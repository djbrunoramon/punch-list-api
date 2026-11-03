package br.com.engbr.examples.punchlistapi;

import com.tngtech.archunit.core.domain.JavaMethod;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;
import jakarta.persistence.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.Repository;
import org.springframework.web.bind.annotation.RestController;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.fields;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

/**
 * Package-by-feature rules (D-03). Features live directly under the root package; {@code shared} is cross-cutting.
 */
@AnalyzeClasses(packages = "br.com.engbr.examples.punchlistapi", importOptions = ImportOption.DoNotIncludeTests.class)
class ArchitectureTest {

    @ArchTest
    static final ArchRule controllersDoNotAccessRepositories = noClasses()
            .that().areAnnotatedWith(RestController.class)
            .should().dependOnClassesThat().areAssignableTo(Repository.class);

    @ArchTest
    static final ArchRule controllersDoNotExposeEntities = methods()
            .that().areDeclaredInClassesThat().areAnnotatedWith(RestController.class)
            .and().arePublic()
            .should(notReturnEntities());

    /** Also inspects generic arguments, so {@code ResponseEntity<Entity>} or {@code Page<Entity>} fail. */
    static ArchCondition<JavaMethod> notReturnEntities() {
        return new ArchCondition<>("not return a JPA @Entity, directly or as a type argument") {
            @Override
            public void check(JavaMethod method, ConditionEvents events) {
                method.getReturnType().getAllInvolvedRawTypes().stream()
                        .filter(type -> type.isAnnotatedWith(Entity.class))
                        .forEach(type -> events.add(SimpleConditionEvent.violated(method,
                                "%s returns entity %s".formatted(method.getFullName(), type.getName()))));
            }
        };
    }

    @ArchTest
    static final ArchRule sharedDoesNotDependOnFeatures = noClasses()
            .that().resideInAPackage("..punchlistapi.shared..")
            .should().dependOnClassesThat().resideInAnyPackage(
                    "..punchlistapi.contract..", "..punchlistapi.responsibleperson..", "..punchlistapi.pendency..");

    @ArchTest
    static final ArchRule featuresAreFreeOfCycles = slices()
            .matching("br.com.engbr.examples.punchlistapi.(*)..")
            .should().beFreeOfCycles();

    @ArchTest
    static final ArchRule noFieldInjection = fields()
            .should().notBeAnnotatedWith(Autowired.class);

    @ArchTest
    static final ArchRule repositoriesAreOnlyUsedInsideTheirFeatureServices = noClasses()
            .that().areNotAssignableTo(Repository.class)
            .and().haveSimpleNameNotEndingWith("Service")
            .should().dependOnClassesThat().areAssignableTo(Repository.class);
}

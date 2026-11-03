package br.com.engbr.examples.punchlistapi;

import br.com.engbr.examples.punchlistapi.contract.Contract;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Proves that {@link ArchitectureTest#controllersDoNotExposeEntities} catches entities wrapped in generics.
 */
class ArchitectureRulesSelfTest {

    @RestController
    static class LeakyController {
        public ResponseEntity<List<Contract>> leak() {
            return ResponseEntity.ok(List.of());
        }
    }

    @Test
    void wrappedEntityInControllerReturnTypeIsAViolation() {
        JavaClasses classes = new ClassFileImporter().importClasses(LeakyController.class, Contract.class);

        assertThatThrownBy(() -> ArchitectureTest.controllersDoNotExposeEntities.check(classes))
                .isInstanceOf(AssertionError.class)
                .hasMessageContaining("returns entity " + Contract.class.getName());
    }
}

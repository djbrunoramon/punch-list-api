package br.com.engbr.examples.punchlistapi.responsibleperson;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/responsible-person")
public class ResponsiblePersonController {

    private final ResponsiblePersonService responsiblePersonService;

    public ResponsiblePersonController(ResponsiblePersonService responsiblePersonService) {
        this.responsiblePersonService = responsiblePersonService;
    }

    @Operation(summary = "Get the responsible people of a contract")
    @GetMapping("/contract/{contractId}")
    public Page<ResponsiblePersonResponse> findAllByContract(
            @PathVariable Long contractId,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.ASC) Pageable pageable) {
        return responsiblePersonService.findAllByContract(contractId, pageable);
    }

    @Operation(summary = "Get a responsible person by id")
    @GetMapping("/{id}")
    public ResponsiblePersonResponse findById(@PathVariable Long id) {
        return responsiblePersonService.findById(id);
    }

    @Operation(summary = "Create a responsible person")
    @PostMapping
    public ResponseEntity<ResponsiblePersonResponse> create(@Valid @RequestBody ResponsiblePersonRequest request) {
        ResponsiblePersonResponse created = responsiblePersonService.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(created.id()).toUri();
        return ResponseEntity.created(location).body(created);
    }

    @Operation(summary = "Update a responsible person by id")
    @PutMapping("/{id}")
    public ResponsiblePersonResponse update(@PathVariable Long id,
                                            @Valid @RequestBody ResponsiblePersonRequest request) {
        return responsiblePersonService.update(id, request);
    }
}

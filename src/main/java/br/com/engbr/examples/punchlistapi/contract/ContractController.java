package br.com.engbr.examples.punchlistapi.contract;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
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
import java.util.List;

@RestController
@RequestMapping("/api/v1/contract")
public class ContractController {

    private final ContractService contractService;

    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @Operation(summary = "Get all contracts")
    @GetMapping
    public List<ContractResponse> findAll() {
        return contractService.findAll();
    }

    @Operation(summary = "Get a contract by id")
    @GetMapping("/{id}")
    public ContractResponse findById(@PathVariable Long id) {
        return contractService.findById(id);
    }

    @Operation(summary = "Create a contract")
    @PostMapping
    public ResponseEntity<ContractResponse> create(@Valid @RequestBody ContractRequest request) {
        ContractResponse created = contractService.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(created.id()).toUri();
        return ResponseEntity.created(location).body(created);
    }

    @Operation(summary = "Update a contract by id")
    @PutMapping("/{id}")
    public ContractResponse update(@PathVariable Long id, @Valid @RequestBody ContractRequest request) {
        return contractService.update(id, request);
    }
}

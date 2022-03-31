package br.com.engbr.examples.punchlistapi.controllers;

import br.com.engbr.examples.punchlistapi.dto.ContractDTO;
import br.com.engbr.examples.punchlistapi.exceptions.IdNotFoundException;
import br.com.engbr.examples.punchlistapi.views.ContractView;
import br.com.engbr.examples.punchlistapi.services.ContractService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/contract")
public class ContractController {

    private final ContractService contractService;

    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @Operation(summary = "Get all Contracts")
    @GetMapping
    public ResponseEntity<List<ContractView>> findAll() {
        List<ContractView> contracts = contractService.findAll();
        return ResponseEntity.ok(contracts);
    }

    @Operation(summary = "Save a Contract")
    @PostMapping
    public ResponseEntity<ContractView> save(@Valid @RequestBody ContractDTO contractDTO) {
        ContractView contractView = contractService.save(contractDTO);
        return new ResponseEntity<>(contractView, HttpStatus.CREATED);
    }

    @Operation(summary = "Update a Contract By Id")
    @PutMapping("/{id}")
    public ResponseEntity<ContractView> update(@PathVariable Long id,
                                               @Valid @RequestBody ContractDTO contractDTO) throws IdNotFoundException {
        ContractView contractView = contractService.update(id,  contractDTO);
        return ResponseEntity.ok(contractView);
    }

    @Operation(summary = "Get contract By Id")
    @GetMapping("/{id}")
    public ResponseEntity<ContractView> update(@PathVariable Long id) throws IdNotFoundException {
        ContractView contractView = contractService.findById(id);
        return ResponseEntity.ok(contractView);
    }
}

package br.com.engbr.examples.punchlistapi.controllers;

import br.com.engbr.examples.punchlistapi.dto.PendencyDTO;
import br.com.engbr.examples.punchlistapi.exceptions.IdNotFoundException;
import br.com.engbr.examples.punchlistapi.views.PendencyView;
import br.com.engbr.examples.punchlistapi.services.PendencyService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
@RequestMapping("/api/v1/pendency")
public class PendencyController {

    private final PendencyService pendencyService;

    public PendencyController(PendencyService pendencyService) {
        this.pendencyService = pendencyService;
    }

    @Operation(summary = "Get all Pendencies by Contract")
    @GetMapping("/contract/{id}")
    public ResponseEntity<Page<PendencyView>> findAllByContract(@PathVariable Long id, Pageable pageable) {
        Page<PendencyView> pendencyViews = pendencyService.findAllByContract(id, pageable);
        return ResponseEntity.ok(pendencyViews);
    }

    @Operation(summary = "Get by id Pendency")
    @GetMapping("/{id}")
    public ResponseEntity<PendencyView> findById(@PathVariable Long id) throws IdNotFoundException {
        PendencyView pendencyView = pendencyService.findById(id);
        return ResponseEntity.ok(pendencyView);
    }

    @Operation(summary = "Save a Pendency")
    @PostMapping
    public ResponseEntity<PendencyView> save(@Valid @RequestBody PendencyDTO pendencyDTO) {
        PendencyView pendencyView = pendencyService.save(pendencyDTO);
        return ResponseEntity.ok(pendencyView);
    }

    @Operation(summary = "Update a Pendency by id")
    @PutMapping("/{id}")
    public ResponseEntity<PendencyView> update(@PathVariable Long id,
                                               @Valid @RequestBody PendencyDTO pendencyDTO) throws IdNotFoundException {
        PendencyView pendencyView = pendencyService.update(id, pendencyDTO);
        return ResponseEntity.ok(pendencyView);
    }

}

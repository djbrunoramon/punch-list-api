package br.com.engbr.examples.punchlistapi.controllers;

import br.com.engbr.examples.punchlistapi.dto.PendencyDTO;
import br.com.engbr.examples.punchlistapi.enums.StatusEnum;
import br.com.engbr.examples.punchlistapi.exceptions.IdNotFoundException;
import br.com.engbr.examples.punchlistapi.exceptions.PendencyStatusInvalidException;
import br.com.engbr.examples.punchlistapi.views.PendencyByPriorityView;
import br.com.engbr.examples.punchlistapi.views.PendencyByStatusView;
import br.com.engbr.examples.punchlistapi.views.PendencyView;
import br.com.engbr.examples.punchlistapi.services.PendencyService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
import java.util.Map;

@RestController
@RequestMapping("/api/v1/pendency")
public class PendencyController {

    private final PendencyService pendencyService;

    public PendencyController(PendencyService pendencyService) {
        this.pendencyService = pendencyService;
    }

    @Operation(summary = "Get all Pendencies by Contract")
    @GetMapping("/contract/{id}")
    public ResponseEntity<Page<PendencyView>> findAllByContract(@PathVariable Long id,
                                                                @PageableDefault(page = 0, size = 20, sort = "createdAt", direction = Sort.Direction.ASC) Pageable pageable) {
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
    public ResponseEntity<PendencyView> save(@Valid @RequestBody PendencyDTO pendencyDTO) throws PendencyStatusInvalidException {
        PendencyView pendencyView = pendencyService.save(pendencyDTO);
        return new ResponseEntity<>(pendencyView, HttpStatus.CREATED);
    }

    @Operation(summary = "Update a Pendency by id")
    @PutMapping("/{id}")
    public ResponseEntity<PendencyView> update(@PathVariable Long id,
                                               @Valid @RequestBody PendencyDTO pendencyDTO) throws IdNotFoundException, PendencyStatusInvalidException {
        PendencyView pendencyView = pendencyService.update(id, pendencyDTO);
        return ResponseEntity.ok(pendencyView);
    }

    @Operation(summary = "Get data for chart pie by contract")
    @GetMapping("/contract/{id}/chart/pie")
    public ResponseEntity<List<PendencyByStatusView>> countPendencyByStatus(@PathVariable Long id) {
        List<PendencyByStatusView> data = pendencyService.getDataCharPie(id);
        return ResponseEntity.ok(data);
    }

    @Operation(summary = "Get data for chart bar by contract")
    @GetMapping("/contract/{id}/chart/bar/{status}")
    public ResponseEntity<List<PendencyByPriorityView>> countPendencyByPriority(@PathVariable Long id, @PathVariable StatusEnum status) {
        List<PendencyByPriorityView> data = pendencyService.getDataCharBar(id, status);
        return ResponseEntity.ok(data);
    }
}

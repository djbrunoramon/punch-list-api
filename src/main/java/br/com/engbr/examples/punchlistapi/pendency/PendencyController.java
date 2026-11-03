package br.com.engbr.examples.punchlistapi.pendency;

import br.com.engbr.examples.punchlistapi.pendency.chart.PendencyByPriority;
import br.com.engbr.examples.punchlistapi.pendency.chart.PendencyByStatus;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import java.util.List;

@RestController
@RequestMapping("/api/v1/pendency")
public class PendencyController {

    private final PendencyService pendencyService;

    public PendencyController(PendencyService pendencyService) {
        this.pendencyService = pendencyService;
    }

    @Operation(summary = "Get the pendencies of a contract")
    @GetMapping("/contract/{contractId}")
    public Page<PendencyResponse> findAllByContract(
            @PathVariable Long contractId,
            @PageableDefault(size = 20, sort = "createdAt") Pageable pageable) {
        return pendencyService.findAllByContract(contractId, pageable);
    }

    @Operation(summary = "Get a pendency by id")
    @GetMapping("/{id}")
    public PendencyResponse findById(@PathVariable Long id) {
        return pendencyService.findById(id);
    }

    @Operation(summary = "Create a pendency")
    @PostMapping
    public ResponseEntity<PendencyResponse> create(@Valid @RequestBody PendencyRequest request) {
        PendencyResponse created = pendencyService.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(created.id()).toUri();
        return ResponseEntity.created(location).body(created);
    }

    @Operation(summary = "Update a pendency by id")
    @PutMapping("/{id}")
    public PendencyResponse update(@PathVariable Long id, @Valid @RequestBody PendencyRequest request) {
        return pendencyService.update(id, request);
    }

    @Operation(summary = "Pendencies of a contract grouped by status")
    @GetMapping("/contract/{contractId}/chart/pie")
    public List<PendencyByStatus> chartByStatus(@PathVariable Long contractId) {
        return pendencyService.chartByStatus(contractId);
    }

    @Operation(summary = "Pendencies of a contract with a given status, grouped by priority")
    @GetMapping("/contract/{contractId}/chart/bar/{status}")
    public List<PendencyByPriority> chartByPriority(@PathVariable Long contractId,
                                                    @PathVariable PendencyStatus status) {
        return pendencyService.chartByPriority(contractId, status);
    }
}

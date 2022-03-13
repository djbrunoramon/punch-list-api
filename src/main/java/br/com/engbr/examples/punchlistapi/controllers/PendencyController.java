package br.com.engbr.examples.punchlistapi.controllers;

import br.com.engbr.examples.punchlistapi.views.PendencyView;
import br.com.engbr.examples.punchlistapi.services.PendencyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pendency")
public class PendencyController {

    private final PendencyService pendencyService;

    public PendencyController(PendencyService pendencyService) {
        this.pendencyService = pendencyService;
    }

    @GetMapping("/contract/{id}")
    public ResponseEntity<Page<PendencyView>> findAllByContract(@PathVariable Long id, Pageable pageable) {
        Page<PendencyView> pendencyViews = pendencyService.findAllByContract(id, pageable);
        return ResponseEntity.ok(pendencyViews);
    }
}

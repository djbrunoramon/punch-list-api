package br.com.engbr.examples.punchlistapi.controllers;

import br.com.engbr.examples.punchlistapi.views.PendencyView;
import br.com.engbr.examples.punchlistapi.services.PendencyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping
    public ResponseEntity<List<PendencyView>> findAll() {
        List<PendencyView> pendencyViews = pendencyService.findAll();
        return ResponseEntity.ok(pendencyViews);
    }
}

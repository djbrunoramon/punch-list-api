package br.com.engbr.examples.punchlistapi.controllers;

import br.com.engbr.examples.punchlistapi.views.ResponsiblePersonView;
import br.com.engbr.examples.punchlistapi.services.ResponsiblePersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/responsible-person")
public class ResponsiblePersonController {

    private final ResponsiblePersonService responsiblePersonService;

    public ResponsiblePersonController(ResponsiblePersonService responsiblePersonService) {
        this.responsiblePersonService = responsiblePersonService;
    }

    @GetMapping
    public ResponseEntity<List<ResponsiblePersonView>> findAll() {
        List<ResponsiblePersonView> responsiblePersonViews = responsiblePersonService.findAll();
        return ResponseEntity.ok(responsiblePersonViews);
    }
}

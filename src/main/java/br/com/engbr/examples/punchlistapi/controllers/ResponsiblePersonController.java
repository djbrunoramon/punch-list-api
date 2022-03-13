package br.com.engbr.examples.punchlistapi.controllers;

import br.com.engbr.examples.punchlistapi.dto.ResponsiblePersonDTO;
import br.com.engbr.examples.punchlistapi.exceptions.IdNotFoundException;
import br.com.engbr.examples.punchlistapi.services.ResponsiblePersonService;
import br.com.engbr.examples.punchlistapi.views.ResponsiblePersonView;
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

@RestController
@RequestMapping("/api/v1/responsible-person")
public class ResponsiblePersonController {

    private final ResponsiblePersonService responsiblePersonService;

    public ResponsiblePersonController(ResponsiblePersonService responsiblePersonService) {
        this.responsiblePersonService = responsiblePersonService;
    }

    @GetMapping("/contract/{id}")
    public ResponseEntity<Page<ResponsiblePersonView>> findAllByContract(@PathVariable Long id, Pageable pageable) {
        Page<ResponsiblePersonView> responsiblePersonViews = responsiblePersonService.findAllByContract(id, pageable);
        return ResponseEntity.ok(responsiblePersonViews);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponsiblePersonView> findById(@PathVariable Long id) throws IdNotFoundException {
        ResponsiblePersonView responsiblePersonView = responsiblePersonService.findById(id);
        return ResponseEntity.ok(responsiblePersonView);
    }

    @PostMapping
    public ResponseEntity<ResponsiblePersonView> save(@Valid @RequestBody ResponsiblePersonDTO responsiblePersonDTO) throws IdNotFoundException {
        ResponsiblePersonView responsiblePersonView = responsiblePersonService.save(responsiblePersonDTO);
        return ResponseEntity.ok(responsiblePersonView);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponsiblePersonView> update(@PathVariable Long id,
                                                        @Valid @RequestBody ResponsiblePersonDTO responsiblePersonDTO) throws IdNotFoundException {
        ResponsiblePersonView responsiblePersonView = responsiblePersonService.update(id, responsiblePersonDTO);
        return ResponseEntity.ok(responsiblePersonView);
    }
}

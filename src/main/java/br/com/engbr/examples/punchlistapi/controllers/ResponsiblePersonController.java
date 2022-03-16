package br.com.engbr.examples.punchlistapi.controllers;

import br.com.engbr.examples.punchlistapi.dto.ResponsiblePersonDTO;
import br.com.engbr.examples.punchlistapi.exceptions.IdNotFoundException;
import br.com.engbr.examples.punchlistapi.services.ResponsiblePersonService;
import br.com.engbr.examples.punchlistapi.views.ResponsiblePersonView;
import io.swagger.v3.oas.annotations.Operation;
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

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/responsible-person")
public class ResponsiblePersonController {

    private final ResponsiblePersonService responsiblePersonService;

    public ResponsiblePersonController(ResponsiblePersonService responsiblePersonService) {
        this.responsiblePersonService = responsiblePersonService;
    }

    @Operation(summary = "Get all Responsible Person by Contract")
    @GetMapping("/contract/{id}")
    public ResponseEntity<Page<ResponsiblePersonView>> findAllByContract(@PathVariable Long id,
                                                                         @PageableDefault(page = 0, size = 20, sort = "namecreatedAt", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<ResponsiblePersonView> responsiblePersonViews = responsiblePersonService.findAllByContract(id, pageable);
        return ResponseEntity.ok(responsiblePersonViews);
    }

    @Operation(summary = "Get by id a Responsible Person")
    @GetMapping("/{id}")
    public ResponseEntity<ResponsiblePersonView> findById(@PathVariable Long id) throws IdNotFoundException {
        ResponsiblePersonView responsiblePersonView = responsiblePersonService.findById(id);
        return ResponseEntity.ok(responsiblePersonView);
    }

    @Operation(summary = "Save a Responsible Person")
    @PostMapping
    public ResponseEntity<ResponsiblePersonView> save(@Valid @RequestBody ResponsiblePersonDTO responsiblePersonDTO) throws IdNotFoundException {
        ResponsiblePersonView responsiblePersonView = responsiblePersonService.save(responsiblePersonDTO);
        return ResponseEntity.ok(responsiblePersonView);
    }

    @Operation(summary = "Update a Responsible Person")
    @PutMapping("/{id}")
    public ResponseEntity<ResponsiblePersonView> update(@PathVariable Long id,
                                                        @Valid @RequestBody ResponsiblePersonDTO responsiblePersonDTO) throws IdNotFoundException {
        ResponsiblePersonView responsiblePersonView = responsiblePersonService.update(id, responsiblePersonDTO);
        return ResponseEntity.ok(responsiblePersonView);
    }
}

package br.com.engbr.examples.punchlistapi.services;

import br.com.engbr.examples.punchlistapi.dto.ResponsiblePersonView;
import br.com.engbr.examples.punchlistapi.repositories.ResponsiblePersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ResponsiblePersonService {

    private final ResponsiblePersonRepository responsiblePersonRepository;

    public ResponsiblePersonService(ResponsiblePersonRepository responsiblePersonRepository) {
        this.responsiblePersonRepository = responsiblePersonRepository;
    }

    @Transactional(readOnly = true)
    public List<ResponsiblePersonView> findAll() {
        return responsiblePersonRepository.findAllByActive(true);
    }
}

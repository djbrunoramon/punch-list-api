package br.com.engbr.examples.punchlistapi.services;

import br.com.engbr.examples.punchlistapi.repositories.ResponsiblePersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ResponsiblePersonService {

    private final ResponsiblePersonRepository responsiblePersonRepository;

    public ResponsiblePersonService(ResponsiblePersonRepository responsiblePersonRepository) {
        this.responsiblePersonRepository = responsiblePersonRepository;
    }
}

package br.com.engbr.examples.punchlistapi.services;

import br.com.engbr.examples.punchlistapi.repositories.PendencyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PendencyService {

    private final PendencyRepository pendencyRepository;

    public PendencyService(PendencyRepository pendencyRepository) {
        this.pendencyRepository = pendencyRepository;
    }
}

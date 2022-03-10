package br.com.engbr.examples.punchlistapi.services;

import br.com.engbr.examples.punchlistapi.dto.PendencyView;
import br.com.engbr.examples.punchlistapi.enums.StatusEnum;
import br.com.engbr.examples.punchlistapi.repositories.PendencyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PendencyService {

    private final PendencyRepository pendencyRepository;

    public PendencyService(PendencyRepository pendencyRepository) {
        this.pendencyRepository = pendencyRepository;
    }

    public List<PendencyView> findAll() {
        return pendencyRepository.findAllByStatus(StatusEnum.OPEN);
    }
}

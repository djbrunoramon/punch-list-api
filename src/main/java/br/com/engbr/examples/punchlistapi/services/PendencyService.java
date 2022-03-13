package br.com.engbr.examples.punchlistapi.services;

import br.com.engbr.examples.punchlistapi.repositories.PendencyRepository;
import br.com.engbr.examples.punchlistapi.views.PendencyView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PendencyService {

    private final PendencyRepository pendencyRepository;

    public PendencyService(PendencyRepository pendencyRepository) {
        this.pendencyRepository = pendencyRepository;
    }

    public Page<PendencyView> findAllByContract(Long id, Pageable pageable) {
        return pendencyRepository.findAllByContractId(id, pageable);
    }


}

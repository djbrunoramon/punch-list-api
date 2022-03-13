package br.com.engbr.examples.punchlistapi.services;

import br.com.engbr.examples.punchlistapi.dto.PendencyDTO;
import br.com.engbr.examples.punchlistapi.exceptions.IdNotFoundException;
import br.com.engbr.examples.punchlistapi.model.Contract;
import br.com.engbr.examples.punchlistapi.model.Pendency;
import br.com.engbr.examples.punchlistapi.model.ResponsiblePerson;
import br.com.engbr.examples.punchlistapi.repositories.PendencyRepository;
import br.com.engbr.examples.punchlistapi.views.PendencyView;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class PendencyService {

    private final PendencyRepository pendencyRepository;

    public PendencyService(PendencyRepository pendencyRepository) {
        this.pendencyRepository = pendencyRepository;
    }

    @Transactional(readOnly = true)
    public Page<PendencyView> findAllByContract(Long id, Pageable pageable) {
        return pendencyRepository.findAllByContractId(id, pageable);
    }

    @Transactional(readOnly = true)
    public PendencyView findById(Long id) throws IdNotFoundException {
        Optional<PendencyView> pendencyView = pendencyRepository.readById(id);
        return pendencyView.orElseThrow(IdNotFoundException::new);
    }

    public PendencyView save(PendencyDTO pendencyDTO) {
        Pendency pendency = new Pendency();

        BeanUtils.copyProperties(pendencyDTO, pendency);

        pendency.setContract(new Contract(pendencyDTO.getIdContract()));

        setFieldsOfResponsiblePersons(pendency, pendencyDTO);

        pendencyRepository.save(pendency);

        return pendencyRepository.readById(pendency.getId()).get();
    }

    public PendencyView update(Long idPendency, PendencyDTO pendencyDTO) throws IdNotFoundException {
        Pendency pendency = pendencyRepository.findById(idPendency)
                .orElseThrow(IdNotFoundException::new);
        BeanUtils.copyProperties(pendencyDTO, pendency);

        setFieldsOfResponsiblePersons(pendency, pendencyDTO);

        pendencyRepository.save(pendency);

        return pendencyRepository.readById(pendency.getId()).get();
    }

    private void setFieldsOfResponsiblePersons(Pendency pendency, PendencyDTO pendencyDTO) {
        pendency.setRegisteredBy(verifyIfResponsiblePersonIsNotNull(pendencyDTO.getRegisteredBy()));
        pendency.setRegisteredTo(verifyIfResponsiblePersonIsNotNull(pendencyDTO.getRegisteredTo()));
        pendency.setDisapprovedBy(verifyIfResponsiblePersonIsNotNull(pendencyDTO.getDisapprovedBy()));
        pendency.setFinishedBy(verifyIfResponsiblePersonIsNotNull(pendencyDTO.getFinishedBy()));
        pendency.setCanceledBy(verifyIfResponsiblePersonIsNotNull(pendencyDTO.getCanceledBy()));
    }

    private ResponsiblePerson verifyIfResponsiblePersonIsNotNull(Long id) {
        if(id != null){
            return new ResponsiblePerson(id);
        }
        return null;
    }
}

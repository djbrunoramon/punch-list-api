package br.com.engbr.examples.punchlistapi.services;

import br.com.engbr.examples.punchlistapi.dto.ResponsiblePersonDTO;
import br.com.engbr.examples.punchlistapi.exceptions.IdNotFoundException;
import br.com.engbr.examples.punchlistapi.model.Contract;
import br.com.engbr.examples.punchlistapi.model.ResponsiblePerson;
import br.com.engbr.examples.punchlistapi.repositories.ResponsiblePersonRepository;
import br.com.engbr.examples.punchlistapi.views.ResponsiblePersonView;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class ResponsiblePersonService {

    private final ResponsiblePersonRepository responsiblePersonRepository;

    public ResponsiblePersonService(ResponsiblePersonRepository responsiblePersonRepository) {
        this.responsiblePersonRepository = responsiblePersonRepository;
    }

    @Transactional(readOnly = true)
    public Page<ResponsiblePersonView> findAllByContract(Long idContract, Pageable pageable) {
        return responsiblePersonRepository.findAllByContractId(idContract, pageable);
    }

    @Transactional(readOnly = true)
    public ResponsiblePersonView findById(Long id) throws IdNotFoundException {
        Optional<ResponsiblePersonView> responsiblePersonView = responsiblePersonRepository
                .readById(id);
        return responsiblePersonView.orElseThrow(IdNotFoundException::new);
    }

    public ResponsiblePersonView save(ResponsiblePersonDTO responsiblePersonDTO) {
        ResponsiblePerson responsiblePerson = new ResponsiblePerson();
        BeanUtils.copyProperties(responsiblePersonDTO, responsiblePerson);
        responsiblePerson.setContract(new Contract(responsiblePersonDTO.getIdContract()));
        responsiblePersonRepository.saveAndFlush(responsiblePerson);
        return responsiblePersonRepository.readById(responsiblePerson.getId()).get();
    }

    public ResponsiblePersonView update(Long id, ResponsiblePersonDTO responsiblePersonDTO) throws IdNotFoundException {
        ResponsiblePerson responsiblePerson = responsiblePersonRepository.findById(id)
                        .orElseThrow(IdNotFoundException::new);
        BeanUtils.copyProperties(responsiblePersonDTO, responsiblePerson);
        responsiblePersonRepository.save(responsiblePerson);
        return responsiblePersonRepository.readById(responsiblePerson.getId()).get();
    }
}

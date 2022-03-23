package br.com.engbr.examples.punchlistapi.controllers;

import br.com.engbr.examples.punchlistapi.dto.ResponsiblePersonDTO;
import br.com.engbr.examples.punchlistapi.repositories.ResponsiblePersonRepository;
import br.com.engbr.examples.punchlistapi.utils.TestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ResponsiblePersonControllerTest {

    @Autowired
    private MockMvc responsiblePersonController;

    @Autowired
    private ResponsiblePersonRepository responsiblePersonRepository;

    private static final String URL_RESPONSIBLE_PERSON = "/api/v1/responsible-person";
    private static final Long CONTRACT_ID = 1L;


    @Test
    void findAllByContract_ExpectOk() throws Exception {
        responsiblePersonController
                .perform(get(URL_RESPONSIBLE_PERSON + "/contract/" + CONTRACT_ID))
                .andExpect(status().isOk());
    }

    @Test
    void findById_ExpertOk() throws Exception {
        responsiblePersonController
                .perform(get(URL_RESPONSIBLE_PERSON + "/" + 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Bruno Machado"));
    }

    @Test
    @Transactional
    void saveResponsiblePerson_ExpectSaved() throws Exception {
        ResponsiblePersonDTO responsiblePersonDTO = createdResponsiblePersonDTO();

        responsiblePersonController
                .perform(post(URL_RESPONSIBLE_PERSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(responsiblePersonDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(responsiblePersonDTO.getName()));
    }

    @Test
    @Transactional
    void updateResponsiblePerson_ExpectUpdated() throws Exception {
        ResponsiblePersonDTO responsiblePersonDTO = createdResponsiblePersonDTO();

        responsiblePersonController
                .perform(put(URL_RESPONSIBLE_PERSON + "/" + 2L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(responsiblePersonDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(responsiblePersonDTO.getName()));
    }

    private ResponsiblePersonDTO createdResponsiblePersonDTO() {
        ResponsiblePersonDTO responsiblePersonDTO = new ResponsiblePersonDTO();
        responsiblePersonDTO.setIdContract(1L);
        responsiblePersonDTO.setName("Name Test");
        responsiblePersonDTO.setDepartment("Department Test");
        responsiblePersonDTO.setOccupation("Occupation Test");
        return responsiblePersonDTO;
    }
}

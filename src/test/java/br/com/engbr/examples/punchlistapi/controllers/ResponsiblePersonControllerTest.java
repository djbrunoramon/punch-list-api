package br.com.engbr.examples.punchlistapi.controllers;

import br.com.engbr.examples.punchlistapi.model.ResponsiblePerson;
import br.com.engbr.examples.punchlistapi.repositories.ResponsiblePersonRepository;
import br.com.engbr.examples.punchlistapi.utils.TestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    void findAllByContract() throws Exception {

        responsiblePersonController
                .perform(get(URL_RESPONSIBLE_PERSON + "/contract/" + CONTRACT_ID))
                .andExpect(status().isOk());

    }

    @Test
    void findById() {
    }

    @Test
    void save() {
    }

    @Test
    void update() {
    }
}

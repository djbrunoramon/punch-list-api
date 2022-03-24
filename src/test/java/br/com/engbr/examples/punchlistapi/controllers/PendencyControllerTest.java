package br.com.engbr.examples.punchlistapi.controllers;

import br.com.engbr.examples.punchlistapi.dto.PendencyDTO;
import br.com.engbr.examples.punchlistapi.enums.StatusEnum;
import br.com.engbr.examples.punchlistapi.model.Pendency;
import br.com.engbr.examples.punchlistapi.utils.TestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PendencyControllerTest {

    public static final String MESSAGE_ID_NOT_FOUND = "ID not found";

    @Autowired
    private MockMvc contractController;

    private static final String URL_PENDENCY = "/api/v1/pendency";

    @Test
    void getAllPendencyByIdContract_ExpectOk() throws Exception {
        contractController
                .perform(get(URL_PENDENCY + "/contract/" + 1L))
                .andExpect(status().isOk());
    }

    @Test
    void getPendencyById_ExpectOk() throws Exception {
        contractController
                .perform(get(URL_PENDENCY + "/" + 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.areaIdentification").value("GAS TURBINE"))
                .andExpect(jsonPath("$.description").value("Touch-up paint on the east side of the enclosure."))
                .andExpect(jsonPath("$.priority").value("B"));
    }

    @Test
    void getPendencyById_WithIdNotExistent_ExpectBadRequest() throws Exception {
        contractController
                .perform(get(URL_PENDENCY + "/" + 100L))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(MESSAGE_ID_NOT_FOUND));
    }

    @Test
    @Transactional
    void savePendency_ExpectCreated() throws Exception {
        PendencyDTO pendencyDTO = this.createPendencyDTO();
        MockHttpServletResponse response = contractController
                .perform(post(URL_PENDENCY)
                        .content(TestUtil.convertObjectToJsonBytes(pendencyDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn().getResponse();

        Pendency pendency = TestUtil.toObject(response.getContentAsByteArray(), Pendency.class);

        assertThat(pendency.getContract().getId()).isEqualTo(pendencyDTO.getIdContract());
        assertThat(pendency.getAreaIdentification()).isEqualTo(pendencyDTO.getAreaIdentification());
        assertThat(pendency.getTag()).isEqualTo(pendencyDTO.getTag());
        assertThat(pendency.getDescription()).isEqualTo(pendencyDTO.getDescription());
        assertThat(pendency.getPriority()).isEqualTo(pendencyDTO.getPriority());
        assertThat(pendency.getStatus()).isEqualTo(pendencyDTO.getStatus());
        assertThat(pendency.getDiscipline()).isEqualTo(pendencyDTO.getDiscipline());
        assertThat(pendency.getRegisteredBy().getId()).isEqualTo(pendencyDTO.getRegisteredBy());
        assertThat(pendency.getRegisteredTo().getId()).isEqualTo(pendencyDTO.getRegisteredTo());
        assertThat(pendency.getExpectedIn()).isEqualTo(pendencyDTO.getExpectedIn());
    }

    @Test
    @Transactional
    void savePendency_WithDescriptionFieldNull_ExpectBadRequest() throws Exception {
        PendencyDTO pendencyDTO = this.createPendencyDTO();
        pendencyDTO.setDescription(null);

        contractController
                .perform(post(URL_PENDENCY)
                        .content(TestUtil.convertObjectToJsonBytes(pendencyDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    @Transactional
    void updatePendency_ExpectCreated() throws Exception {
        PendencyDTO pendencyDTO = this.createPendencyDTO();
        MockHttpServletResponse response = contractController
                .perform(put(URL_PENDENCY + "/" + 1L)
                        .content(TestUtil.convertObjectToJsonBytes(pendencyDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        Pendency pendency = TestUtil.toObject(response.getContentAsByteArray(), Pendency.class);

        assertThat(pendency.getContract().getId()).isEqualTo(pendencyDTO.getIdContract());
        assertThat(pendency.getAreaIdentification()).isEqualTo(pendencyDTO.getAreaIdentification());
        assertThat(pendency.getTag()).isEqualTo(pendencyDTO.getTag());
        assertThat(pendency.getDescription()).isEqualTo(pendencyDTO.getDescription());
        assertThat(pendency.getPriority()).isEqualTo(pendencyDTO.getPriority());
        assertThat(pendency.getStatus()).isEqualTo(pendencyDTO.getStatus());
        assertThat(pendency.getDiscipline()).isEqualTo(pendencyDTO.getDiscipline());
        assertThat(pendency.getRegisteredBy().getId()).isEqualTo(pendencyDTO.getRegisteredBy());
        assertThat(pendency.getRegisteredTo().getId()).isEqualTo(pendencyDTO.getRegisteredTo());
        assertThat(pendency.getExpectedIn()).isEqualTo(pendencyDTO.getExpectedIn());
    }

    @Test
    @Transactional
    void updatePendency_WithDescriptionFieldNull_ExpectBadRequest() throws Exception {
        PendencyDTO pendencyDTO = this.createPendencyDTO();
        pendencyDTO.setDescription(null);

        contractController
                .perform(put(URL_PENDENCY + "/" + 1L)
                        .content(TestUtil.convertObjectToJsonBytes(pendencyDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    void updatePendency_WithIDNotExistent_ExpectBadRequest() throws Exception {
        PendencyDTO pendencyDTO = this.createPendencyDTO();

        contractController
                .perform(put(URL_PENDENCY + "/" + 1000L)
                        .content(TestUtil.convertObjectToJsonBytes(pendencyDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(MESSAGE_ID_NOT_FOUND));
    }

    private PendencyDTO createPendencyDTO () {
        PendencyDTO pendencyDTO = new PendencyDTO();
        pendencyDTO.setIdContract(1L);
        pendencyDTO.setAreaIdentification("Area Identification Test");
        pendencyDTO.setTag("Tag Test");
        pendencyDTO.setDescription("Description test");
        pendencyDTO.setPriority("C");
        pendencyDTO.setStatus(StatusEnum.OPEN);
        pendencyDTO.setDiscipline("Discipline Test");
        pendencyDTO.setRegisteredBy(1L);
        pendencyDTO.setRegisteredTo(2L);
        pendencyDTO.setExpectedIn(LocalDateTime.now().plusDays(7L));
        return pendencyDTO;
    }
}

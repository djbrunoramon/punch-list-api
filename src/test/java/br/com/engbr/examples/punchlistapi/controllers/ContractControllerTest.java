package br.com.engbr.examples.punchlistapi.controllers;

import br.com.engbr.examples.punchlistapi.dto.ContractDTO;
import br.com.engbr.examples.punchlistapi.model.Contract;
import br.com.engbr.examples.punchlistapi.utils.TestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ContractControllerTest {

    @Autowired
    private MockMvc contractController;

    private static final String URL_CONTRACT = "/api/v1/contract";

    @Test
    @Transactional
    void findAllContracts_ExpectOk() throws Exception {
        contractController
                .perform(get(URL_CONTRACT))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    void saveAContract_ExpectCreated() throws Exception {
        ContractDTO contractDTO = this.createContractDTO();

        contractController
                .perform(post(URL_CONTRACT)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(TestUtil.convertObjectToJsonBytes(contractDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.numberContract").value(contractDTO.getNumberContract()))
                .andExpect(jsonPath("$.description").value(contractDTO.getDescription()));
    }

    @Test
    @Transactional
    void saveAContract_WithDescriptionFieldNull_ExpectBadRequest() throws Exception {
        ContractDTO contractDTO = this.createContractDTO();
        contractDTO.setDescription(null);

        contractController
                .perform(post(URL_CONTRACT)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(TestUtil.convertObjectToJsonBytes(contractDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateAContract_ExpectCreated() throws Exception {
        ContractDTO contractDTO = this.createContractDTO();

        contractController
                .perform(put(URL_CONTRACT + "/" + 1L)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(TestUtil.convertObjectToJsonBytes(contractDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.numberContract").value(contractDTO.getNumberContract()))
                .andExpect(jsonPath("$.description").value(contractDTO.getDescription()));
    }

    @Test
    @Transactional
    void updateAContract_WithDescriptionFieldNull_ExpectBadRequest() throws Exception {
        ContractDTO contractDTO = this.createContractDTO();
        contractDTO.setDescription(null);

        contractController
                .perform(put(URL_CONTRACT + "/" + 1L)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(TestUtil.convertObjectToJsonBytes(contractDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    void getContractById_ExpectOk() throws Exception {
        Long idContract = 1L;

        MockHttpServletResponse response = contractController
                .perform(get(URL_CONTRACT + "/" + idContract))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        Contract contract = TestUtil.toObject(response.getContentAsByteArray(), Contract.class);
        assertThat(contract.getId()).isEqualTo(idContract);
        assertThat(contract.getNumberContract()).isEqualTo("BR2022-001");
        assertThat(contract.getDescription()).isEqualTo("Thermoelectric Power Plant Generation Brazil");
        assertThat(contract.getAddress()).isEqualTo("Manaus-AM-BR");
        assertThat(contract.getStartAt().truncatedTo(ChronoUnit.DAYS)).isEqualTo(LocalDateTime.now().plusDays(1L).truncatedTo(ChronoUnit.DAYS));
    }

    @Test
    @Transactional
    void getContractById_WithIdContractNotExistent_ExpectBadRequest() throws Exception {
        Long idContractNotExistent = 999L;

        contractController
                .perform(get(URL_CONTRACT + "/" + idContractNotExistent))
                .andExpect(status().isBadRequest());
    }

    private ContractDTO createContractDTO() {
        ContractDTO contractDTO = new ContractDTO();
        contractDTO.setNumberContract("BR1010-TEST");
        contractDTO.setDescription("Contract For Test");
        contractDTO.setAddress("Curitiba-PR-BR");
        contractDTO.setEstimatedAt(new BigDecimal("10000000000.00"));
        contractDTO.setStartAt(LocalDateTime.now().plusDays(10L));
        contractDTO.setScheduledTo(LocalDateTime.now().plusDays(340L));
        return contractDTO;
    }

}

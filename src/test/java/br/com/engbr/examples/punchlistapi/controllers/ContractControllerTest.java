package br.com.engbr.examples.punchlistapi.controllers;

import br.com.engbr.examples.punchlistapi.dto.ContractDTO;

import br.com.engbr.examples.punchlistapi.utils.TestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

//    @Test
//    @Transactional
//    void saveAContract_ExpectCreated() throws Exception {
//        ContractDTO contractDTO = this.createContractDTO();
//        byte[] contractDTOBytes = TestUtil.convertObjectToJsonBytes(contractDTO);
//        contractController
//                .perform(post(URL_CONTRACT)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(contractDTOBytes))
//                .andExpect(status().isCreated());
//    }

    @Test
    void update() {
    }

    private ContractDTO createContractDTO() {
        ContractDTO contractDTO = new ContractDTO();
        contractDTO.setNumberContract("BR1010-TEST");
        contractDTO.setDescription("Contract For Test");
        contractDTO.setAddress("Curitiba-PR-BR");
        contractDTO.setStartAt(LocalDateTime.parse("2022-03-04T10:30:00"));
//        contractDTO.setScheduledTo(LocalDateTime.now().plusDays(340L));
//        contractDTO.setEstimatedAt(new BigDecimal("10000000000.00"));
        return contractDTO;
    }

}

package br.com.engbr.examples.punchlistapi.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ResponsiblePersonDTO {

    @NotNull
    private Long idContract;

    @NotBlank
    private String name;

    @NotBlank
    private String occupation;

    @NotBlank
    private String department;

}

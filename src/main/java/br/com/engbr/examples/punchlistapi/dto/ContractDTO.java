package br.com.engbr.examples.punchlistapi.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
public class ContractDTO {

    @NotBlank
    private String numberContract;

    @NotBlank
    private String description;

    @NotBlank
    private String address;

    private LocalDateTime startAt;

    private LocalDateTime scheduledTo;

    private BigDecimal estimatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContractDTO that = (ContractDTO) o;
        return numberContract.equals(that.numberContract) && description.equals(that.description) && address.equals(that.address) && startAt.equals(that.startAt) && scheduledTo.equals(that.scheduledTo) && estimatedAt.equals(that.estimatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numberContract, description, address, startAt, scheduledTo, estimatedAt);
    }
}

package br.com.engbr.examples.punchlistapi.views;

import br.com.engbr.examples.punchlistapi.enums.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PendencyByStatusView {
    private StatusEnum label;
    private Long quantity;


}

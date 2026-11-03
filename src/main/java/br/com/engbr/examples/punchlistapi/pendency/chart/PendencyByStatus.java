package br.com.engbr.examples.punchlistapi.pendency.chart;

import br.com.engbr.examples.punchlistapi.pendency.PendencyStatus;

public record PendencyByStatus(PendencyStatus label, long quantity) {
}

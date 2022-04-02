package com.agh.emt.utils.form;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PlannedExchangeDetails {
    private ErasmusDestination erasmusDestination;
    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;
    private Boolean isLongTerm;
}

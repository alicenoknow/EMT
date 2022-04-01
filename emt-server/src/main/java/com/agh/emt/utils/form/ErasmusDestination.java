package com.agh.emt.utils.form;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErasmusDestination {
    private Country country;
    private Boolean isUniversity = true;
    private String institutionName; // internship institution / university
    private String erasmusCode; // Optional
}

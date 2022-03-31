package com.agh.emt.utils.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.Binary;

@Data
@AllArgsConstructor
public class LanguageCertificate {
    private Language language;
    private String name; // TODO: Maybe enum, but we need to find all possible certificates for all popular erasmus languages
    private CertificateLevel level;
    private Float grade; // When grade is a letter -> map to number
    // (i.e. name: Cambridge, level: C1, grade: B -> 4.0)
    private Binary pdf; // Uploaded pdf/image (?) (BSON-BinData: <16MB )
    private Boolean accepted = false; // Coordinator accepted the certificate (he could look into)
}

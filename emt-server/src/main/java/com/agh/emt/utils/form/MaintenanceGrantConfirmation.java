package com.agh.emt.utils.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.Binary;

@Data
@AllArgsConstructor
public class MaintenanceGrantConfirmation {
    private Binary pdf; // Uploaded pdf/image (?) (BSON-BinData: <16MB )
    private Boolean accepted = false; // Coordinator accepted the certificate (he could look into)
}

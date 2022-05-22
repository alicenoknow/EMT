package com.agh.emt.service.one_drive;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class PostFileDTO {
    String oneDriveLink;
    String oneDrivePath;
    String oneDriveId;
}

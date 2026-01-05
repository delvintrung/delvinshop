package com.example.delvin.dto.request;

import com.example.delvin.enums.LicenseKeyPrefix;
import lombok.*;

import java.time.Instant;

@Data
@AllArgsConstructor
@Getter
@Setter
public class LicenseProductCreateRequest {
    private String name;
    private String description;
    private LicenseKeyPrefix useWith;
    @NonNull
    private Long licenseTypeId;
}

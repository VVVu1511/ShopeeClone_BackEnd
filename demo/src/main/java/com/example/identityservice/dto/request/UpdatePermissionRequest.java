package com.example.identityservice.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)

public class UpdatePermissionRequest {
    Long permissionId;
    String name;
    String description;
}

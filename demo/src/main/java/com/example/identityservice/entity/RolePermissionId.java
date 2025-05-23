package com.example.identityservice.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Embeddable
@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Getter
@Builder
public class RolePermissionId implements Serializable{
    @Column(name="role_id")
    Long roleId;
    @Column(name = "permission_id")
    Long permissionId;
}

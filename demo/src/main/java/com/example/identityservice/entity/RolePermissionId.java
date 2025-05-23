package com.example.identityservice.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Embeddable
@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Getter
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolePermissionId implements Serializable{
    @Column(name="role_id", length = 36)
    Long roleId;
    @Column(name="permission_id", length = 36)
    Long permissionId;
}

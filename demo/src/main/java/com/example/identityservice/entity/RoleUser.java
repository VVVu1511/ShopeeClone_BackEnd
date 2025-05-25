package com.example.identityservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter 
@Setter
@Builder
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class RoleUser {
    @EmbeddedId
    RoleUserId id;

    @ManyToOne
    @JsonIgnore
    @MapsId("roleId")
    @JoinColumn(name="role_id")
    Role role;

    @ManyToOne
    @JsonIgnore
    @MapsId("userId")
    @JoinColumn(name="user_id")
    User user;
}

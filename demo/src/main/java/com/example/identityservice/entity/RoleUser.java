package com.example.identityservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
@Builder
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

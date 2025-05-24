package com.example.identityservice.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Builder
@Getter
@Setter
public class RoleUserId implements Serializable{
    @Column(name="role_id")
    Long role;
    @Column(name = "user_id")
    Long user;
}

package com.techvortex.vortex.entity;

import java.io.Serializable;
import java.util.List;

import org.hibernate.annotations.Nationalized;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;

@Data
@Table(name = "Role")
@Entity
public class Role implements Serializable {
    @Id
    @Column(name = "role_id")
    private String RoleId;

    @Nationalized
    @Column(name = "role_name")
    private String RoleName;

    @JsonIgnore
    @OneToMany(mappedBy = "role")
    List<Authority> authorities;
}

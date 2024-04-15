package com.techvortex.vortex.entity;

import java.io.Serializable;
import java.util.List;

import org.hibernate.annotations.Nationalized;

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
    private String RoleId;

    @Nationalized
    private String RoleName;

    @OneToMany(mappedBy = "role")
    List<Authority> authorities;
}

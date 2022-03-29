package com.viktor.skype.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "role")
public class Role implements GrantedAuthority {
    @EqualsAndHashCode.Include
    @Column(name = "role_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    @ManyToMany(mappedBy = "authorities")
    @JsonIgnore
    @ToString.Exclude
    private List<User> users;

    @Override
    public String getAuthority() {
        return name;
    }
}

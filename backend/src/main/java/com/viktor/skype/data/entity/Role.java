package com.viktor.skype.data.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Table;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "role")
public class Role implements GrantedAuthority {
    @EqualsAndHashCode.Include
    private Integer id;
    private String name;

    @Override
    public String getAuthority() {
        return name;
    }
}

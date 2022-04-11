package com.itstore.security.model;

import com.itstore.core.data.model.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "sec_resource")
public class SecurityResource extends AbstractEntity {

    private String name;
    private String key;
    private String description;
    @OneToMany(mappedBy = "resource")
    private Set<SecurityPermission> permissions = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<SecurityPermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<SecurityPermission> permissions) {
        this.permissions = permissions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SecurityResource resource = (SecurityResource) o;
        return Objects.equals(key, resource.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }
}

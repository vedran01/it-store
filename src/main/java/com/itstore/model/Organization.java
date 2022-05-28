package com.itstore.model;

import com.itstore.core.data.model.AbstractEntity;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "organization")
public class Organization extends AbstractEntity {

    private String name;

    @Embedded
    private Contact contact;

    @Embedded
    private Location location;

    private boolean enabled;

    @CreatedDate
    private Date created;

    @LastModifiedDate
    private Date modified;

    @ManyToMany
    @JoinTable(name = "organization_user",
            joinColumns = @JoinColumn(name = "organization_id") ,
            inverseJoinColumns = @JoinColumn(name = "user_id") )
    private Set<User> users = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public static Organization example(String search) {
        Organization organization = new Organization();
        organization.setName(search);

        return organization;
    }
}

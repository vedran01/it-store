package com.itstore.data.dto;

public class PermissionDTO {

    private Long id;
    private String name;
    private String key;
    private String description;
    private boolean enabled;
    private ResourceDTO resource;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public ResourceDTO getResource() {
        return resource;
    }

    public void setResource(ResourceDTO resource) {
        this.resource = resource;
    }
}

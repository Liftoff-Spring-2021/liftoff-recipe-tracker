package org.launchcode.liftoffrecipetracker.models;

import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@MappedSuperclass
public abstract class AbstractEntityName extends AbstractEntityId {

    // Class Variables
    @Size(min = 4, max = 50, message = "Name is required and must be between 4 and 50 characters long")
    @NotBlank(message = "")
    @NotNull
    //@FullTextField annotation creates an index of this property that is full-text searchable
    @FullTextField
    private String name;

    //Getters & Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}

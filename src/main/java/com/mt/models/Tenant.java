package com.mt.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by bharathim on 11/11/2015.
 */
@Entity
@Table(name = "tenant")
public class Tenant {

    // ------------------------
    // PRIVATE FIELDS
    // ------------------------

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull
    private String name;

    @NotNull
    private String domain;

    // ------------------------
    // PUBLIC METHODS
    // ------------------------

    public int getId() { return id; }

    public void setId(int value) {
        this.id = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String value) {
        this.domain = value;
    }

} // class User
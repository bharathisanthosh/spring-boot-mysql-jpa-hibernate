package com.mt.models;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.ParamDef;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Represents a Product for this web application.
 */
@Entity
@Table(name = "product")
@FilterDef(name="tenantFilter", parameters=@ParamDef(name="tenantId",type="int"))
@Filters(
        @Filter(name="tenantFilter",condition="tenant_id = :tenantId")
)
public class Product {

  // ------------------------
  // PRIVATE FIELDS
  // ------------------------
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
  
  @NotNull
  private String code;
  
  @NotNull
  private String name;

  private String description;

  @NotNull
  private String price;

  @Column(name="tenant_id",nullable=false)
  private int tenantId;

  // ------------------------
  // PUBLIC METHODS
  // ------------------------
  
  public Product() { }

  public Product(long id) {
    this.id = id;
  }

  public Product(String code, String name, String description, String price) {
    this.code = code;
    this.name = name;
    this.description = description;
    this.price = price;
  }

  public long getId() {
    return id;
  }

  public void setId(long value) {
    this.id = value;
  }

  public String getCode() {
    return code;
  }
  
  public void setCode(String value) {
    this.code = value;
  }
  
  public String getName() {
    return name;
  }

  public void setName(String value) {
    this.name = value;
  }

  public int getTenantId() {
    return tenantId;
  }

  public void setTenantId(int value) {
    this.tenantId = value;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getPrice() {
    return price;
  }

  public void setPrice(String price) {
    this.price = price;
  }
} // class Product

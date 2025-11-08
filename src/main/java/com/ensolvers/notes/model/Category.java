package com.ensolvers.notes.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "categories", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Category {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)   // <- ignora id en requests
  private Long id;

  @NotBlank
  @Column(nullable = false)
  private String name;

  // Getters / Setters
  public Long getId() { return id; }
  public String getName() { return name; }
  public void setName(String name) { this.name = name; }
}

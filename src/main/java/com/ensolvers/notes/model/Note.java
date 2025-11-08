package com.ensolvers.notes.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "notes")
public class Note {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  private String title;

  @Column(columnDefinition = "text")
  private String content;

  private boolean archived = false;

  @Column(name = "created_at", nullable = false)
  private Instant createdAt;

  @Column(name = "updated_at", nullable = false)
  private Instant updatedAt;

  @ManyToMany
  @JoinTable(
      name = "note_categories",
      joinColumns = @JoinColumn(name = "note_id"),
      inverseJoinColumns = @JoinColumn(name = "category_id")
  )
  private Set<Category> categories = new HashSet<>();

  @PrePersist
  void onCreate() {
    Instant now = Instant.now();
    createdAt = now;
    updatedAt = now;
  }

  @PreUpdate
  void onUpdate() { updatedAt = Instant.now(); }

  // Getters / Setters
  public Long getId() { return id; }
  public String getTitle() { return title; }
  public void setTitle(String title) { this.title = title; }
  public String getContent() { return content; }
  public void setContent(String content) { this.content = content; }
  public boolean isArchived() { return archived; }
  public void setArchived(boolean archived) { this.archived = archived; }
  public Instant getCreatedAt() { return createdAt; }
  public Instant getUpdatedAt() { return updatedAt; }
  public Set<Category> getCategories() { return categories; }
  public void setCategories(Set<Category> categories) { this.categories = categories; }
}

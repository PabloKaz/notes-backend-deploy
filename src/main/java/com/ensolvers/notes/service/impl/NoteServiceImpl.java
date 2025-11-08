package com.ensolvers.notes.service.impl;

import com.ensolvers.notes.dto.NoteRequest;
import com.ensolvers.notes.dto.NoteResponse;
import com.ensolvers.notes.model.Category;
import com.ensolvers.notes.model.Note;
import com.ensolvers.notes.repo.CategoryRepository;
import com.ensolvers.notes.repo.NoteRepository;
import com.ensolvers.notes.service.NoteService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class NoteServiceImpl implements NoteService {
  private final NoteRepository noteRepo;
  private final CategoryRepository catRepo;

  public NoteServiceImpl(NoteRepository noteRepo, CategoryRepository catRepo) {
    this.noteRepo = noteRepo; this.catRepo = catRepo;
  }

  @Override
  public NoteResponse create(NoteRequest req) {
    Note n = new Note();
    n.setTitle(req.title());
    n.setContent(req.content());
    attachCategories(n, req.categoryIds());
    return toDto(noteRepo.save(n));
  }

  @Override
  public NoteResponse update(Long id, NoteRequest req) {
    Note n = noteRepo.findById(id).orElseThrow(() -> notFound("Note", id));
    n.setTitle(req.title());
    n.setContent(req.content());
    n.getCategories().clear();
    attachCategories(n, req.categoryIds());
    return toDto(n);
  }

  @Override
  public void delete(Long id) {
    try { noteRepo.deleteById(id); }
    catch (EmptyResultDataAccessException e) { throw notFound("Note", id); }
  }

  @Override
  public NoteResponse archive(Long id) {
    Note n = noteRepo.findById(id).orElseThrow(() -> notFound("Note", id));
    n.setArchived(true);
    return toDto(n);
  }

  @Override
  public NoteResponse unarchive(Long id) {
    Note n = noteRepo.findById(id).orElseThrow(() -> notFound("Note", id));
    n.setArchived(false);
    return toDto(n);
  }

  @Override
  @Transactional(readOnly = true)
  public java.util.List<NoteResponse> list(boolean archived, String category) {
    var list = (category == null || category.isBlank())
        ? noteRepo.findAllByArchived(archived)
        : noteRepo.findAllByCategories_NameAndArchived(category, archived);
    return list.stream().map(this::toDto).toList();
  }

  @Override
  @Transactional(readOnly = true)
  public NoteResponse get(Long id) {
    return toDto(noteRepo.findById(id).orElseThrow(() -> notFound("Note", id)));
  }

  private void attachCategories(Note n, Set<Long> ids) {
    if (ids == null || ids.isEmpty()) return;
    var cats = ids.stream()
        .map(i -> catRepo.findById(i).orElseThrow(() -> notFound("Category", i)))
        .collect(Collectors.toSet());
    n.getCategories().addAll(cats);
  }

  private NoteResponse toDto(Note n) {
    var names = n.getCategories().stream().map(Category::getName).collect(Collectors.toSet());
    return new NoteResponse(n.getId(), n.getTitle(), n.getContent(), n.isArchived(),
        n.getCreatedAt(), n.getUpdatedAt(), names);
  }

  private NoSuchElementException notFound(String type, Object id) {
    return new NoSuchElementException(type + " not found: " + id);
  }
}

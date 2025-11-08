package com.ensolvers.notes.repo;

import com.ensolvers.notes.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {
  List<Note> findAllByArchived(boolean archived);
  List<Note> findAllByCategories_NameAndArchived(String name, boolean archived);
}

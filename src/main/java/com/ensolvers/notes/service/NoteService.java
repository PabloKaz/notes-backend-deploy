package com.ensolvers.notes.service;

import com.ensolvers.notes.dto.NoteRequest;
import com.ensolvers.notes.dto.NoteResponse;

import java.util.List;

public interface NoteService {
  NoteResponse create(NoteRequest req);
  NoteResponse update(Long id, NoteRequest req);
  void delete(Long id);
  NoteResponse archive(Long id);
  NoteResponse unarchive(Long id);
  List<NoteResponse> list(boolean archived, String category);
  NoteResponse get(Long id);
}

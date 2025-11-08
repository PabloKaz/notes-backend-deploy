package com.ensolvers.notes.api;

import com.ensolvers.notes.dto.NoteRequest;
import com.ensolvers.notes.dto.NoteResponse;
import com.ensolvers.notes.service.NoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Notes")
@RestController
@RequestMapping("/api/notes")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class NoteController {
  private final NoteService svc;
  public NoteController(NoteService svc) { this.svc = svc; }

  @Operation(summary = "Crear nota")
  @PostMapping public NoteResponse create(@RequestBody @Valid NoteRequest req) { return svc.create(req); }

  @Operation(summary = "Obtener una nota por id")
  @GetMapping("/{id}")
  public NoteResponse get(@PathVariable("id") Long id) { return svc.get(id); }
  
  @Operation(summary = "Actualizar nota")
  @PutMapping("/{id}")
  public NoteResponse update(@PathVariable("id") Long id, @RequestBody @jakarta.validation.Valid NoteRequest req) {
    return svc.update(id, req);
  }

  @Operation(summary = "Eliminar nota")
  @DeleteMapping("/{id}")
  public void delete(@PathVariable("id") Long id) { svc.delete(id); }
  
  @Operation(summary = "Archivar nota")
  @PostMapping("/{id}/archive")
  public NoteResponse archive(
      @io.swagger.v3.oas.annotations.Parameter(description = "Note ID", example = "1")
      @PathVariable("id") Long id
  ) {
    return svc.archive(id);
  }
  
  @Operation(summary = "Desarchivar nota")
  @PostMapping("/{id}/unarchive")
  public NoteResponse unarchive(
      @io.swagger.v3.oas.annotations.Parameter(description = "Note ID", example = "1")
      @PathVariable("id") Long id
  ) {
    return svc.unarchive(id);
  }
  
  
  @Operation(summary = "Listar notas", description = "archived=false|true y category opcional")
  @GetMapping
  public List<NoteResponse> list(    @RequestParam(name = "archived", defaultValue = "false") boolean archived,
		    						@RequestParam(name = "category", required = false) String category) {
    return svc.list(archived, category);
  }
}

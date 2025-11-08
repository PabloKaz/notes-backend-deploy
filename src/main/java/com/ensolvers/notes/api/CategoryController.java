package com.ensolvers.notes.api;

import com.ensolvers.notes.dto.CategoryRequest;
import com.ensolvers.notes.model.Category;
import com.ensolvers.notes.repo.CategoryRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Categories")
@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class CategoryController {
  private final CategoryRepository repo;
  public CategoryController(CategoryRepository repo) { this.repo = repo; }

  @Operation(summary = "Listar categorías")
  @GetMapping public List<Category> all() { return repo.findAll(); }

  @Operation(summary = "Crear categoría")
  @PostMapping
  public Category create(@RequestBody @Valid CategoryRequest req) {
    var c = new Category();
    c.setName(req.name());
    return repo.save(c); // id null -> persist
  }

  @Operation(summary = "Eliminar categoría")
  @DeleteMapping("/{id}") public void delete(@PathVariable Long id) { repo.deleteById(id); }
}

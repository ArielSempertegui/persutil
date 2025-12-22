package net.ausiasmarch.persutil.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.ausiasmarch.persutil.entity.FernandezIdeaEntity;
import net.ausiasmarch.persutil.service.FernandezIdeaService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/idea")
public class FernandezIdeaApi {
    // bulk creation de ideas fake con CORS específico para Angular
    @CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
    @PostMapping("/bulk/{amount}")
    public ResponseEntity<Long> bulkCreate(@PathVariable int amount) {
        return ResponseEntity.ok(oIdeaService.bulkCreate((long) amount));
    }

    @Autowired
    FernandezIdeaService oIdeaService;

    // ----------------------------CRUD---------------------------------

    // obtener idea por id
    @GetMapping("/{id}")
    public ResponseEntity<FernandezIdeaEntity> get(@PathVariable Long id) {
        return ResponseEntity.ok(oIdeaService.get(id));
    }

    // crear idea
    @PostMapping("")
    public ResponseEntity<Long> create(@RequestBody FernandezIdeaEntity ideaEntity) {
        return ResponseEntity.ok(oIdeaService.create(ideaEntity));
    }

    // modificar idea
    @PutMapping("")
    public ResponseEntity<Long> update(@RequestBody FernandezIdeaEntity ideaEntity) {
        return ResponseEntity.ok(oIdeaService.update(ideaEntity));
    }

    // borrar idea
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id) {
        return ResponseEntity.ok(oIdeaService.delete(id));
    }

    // listado paginado de ideas (acepta filtro publico)
    @GetMapping("")
    public ResponseEntity<Page<FernandezIdeaEntity>> getPage(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "publico", required = false) Boolean publico,
        @RequestParam(name = "filter", required = false) String filter,
            @RequestParam(name = "sort", defaultValue = "fechaCreacion") String sort,
            @RequestParam(name = "direction", required = false) String direction) {
        // Support both styles:
        // - frontend sending separate params: sort=fechaCreacion&direction=desc
        // - frontend sending combined param as Spring usually does: sort=field,dir
        String sortField = sort;
        String dir = (direction == null) ? "desc" : direction;
        if (sort != null && sort.contains(",")) {
            String[] parts = sort.split(",");
            if (parts.length > 0) {
                sortField = parts[0];
            }
            if (parts.length > 1) {
                dir = parts[1];
            }
        }
        return ResponseEntity.ok(oIdeaService.getPageFiltered(page, size, publico, filter, sortField, dir));
    }

    // contar ideas (opcionalmente filtrado por publico)
    @GetMapping("/count")
    public ResponseEntity<Long> count(
            @RequestParam(name = "publico", required = false) Boolean publico,
            @RequestParam(name = "filter", required = false) String filter) {
        return ResponseEntity.ok(oIdeaService.countFiltered(publico, filter));
    }

    // total sin filtro (pensado para admin)
    @GetMapping("/count/total")
    public ResponseEntity<Long> countTotal() {
        return ResponseEntity.ok(oIdeaService.countTotal());
    }

    // publicar/despublicar (botón de publicación)
    @PutMapping("/publicar/{id}")
    public ResponseEntity<Long> publicar(@PathVariable Long id) {
        return ResponseEntity.ok(oIdeaService.publicar(id));
    }

    @PutMapping("/despublicar/{id}")
    public ResponseEntity<Long> despublicar(@PathVariable Long id) {
        return ResponseEntity.ok(oIdeaService.despublicar(id));
    }

    // vaciar tabla (elimina todos los registros)
    @DeleteMapping("/empty")
    public ResponseEntity<Long> empty() {
        return ResponseEntity.ok(oIdeaService.empty());
    }

}

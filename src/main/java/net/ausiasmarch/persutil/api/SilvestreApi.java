package net.ausiasmarch.persutil.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.ausiasmarch.persutil.entity.SilvestreEntity;
import net.ausiasmarch.persutil.service.SilvestreService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/silvestre")
public class SilvestreApi {

    @Autowired
    SilvestreService oSilvestreService;

    @GetMapping("/saludar")
    public ResponseEntity<String> saludar() {
        return new ResponseEntity<>("\"Hola desde Silvestre\"", HttpStatus.OK);
    }

    @GetMapping("/rellena/{numItems}")
    public ResponseEntity<Long> rellenaSilvestre(@PathVariable Long numItems) {
        return ResponseEntity.ok(oSilvestreService.rellenaSilvestre(numItems));
    }

    // ----------------------------CRUD---------------------------------

    // obtener publicación por id
    @GetMapping("/{id}")
    public ResponseEntity<SilvestreEntity> get(@PathVariable Long id) {
        return ResponseEntity.ok(oSilvestreService.get(id));
    }

    // crear publicaciones
    @PostMapping("")
    public ResponseEntity<Long> create(@RequestBody SilvestreEntity oEntity) {
        return ResponseEntity.ok(oSilvestreService.create(oEntity));
    }

    // modificar publicaciones
    @PutMapping("")
    public ResponseEntity<Long> update(@RequestBody SilvestreEntity oEntity) {
        return ResponseEntity.ok(oSilvestreService.update(oEntity));
    }

    // borrar posts
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id) {
        return ResponseEntity.ok(oSilvestreService.delete(id));
    }

    // listado paginado de posts
    @GetMapping("")
    public ResponseEntity<Page<SilvestreEntity>> getPage(Pageable oPageable,
            @RequestParam(name = "titulo", required = false) String titulo,
            @RequestParam(name = "descripcion", required = false) String descripcion,
            @RequestParam(name = "publicado", required = false) Boolean publicado) {
        if ((titulo == null || titulo.isBlank()) && (descripcion == null || descripcion.isBlank()) && publicado == null) {
            return ResponseEntity.ok(oSilvestreService.getPage(oPageable));
        } else {
            return ResponseEntity.ok(oSilvestreService.getPageFiltered(oPageable, titulo, descripcion, publicado));
        }
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count(@RequestParam(name = "titulo", required = false) String titulo,
            @RequestParam(name = "descripcion", required = false) String descripcion,
            @RequestParam(name = "publicado", required = false) Boolean publicado) {
        if ((titulo == null || titulo.isBlank()) && (descripcion == null || descripcion.isBlank()) && publicado == null) {
            return ResponseEntity.ok(oSilvestreService.count());
        } else {
            return ResponseEntity.ok(oSilvestreService.countFiltered(titulo, descripcion, publicado));
        }
    }

    // publicar post
    @PutMapping("/publicar/{id}")
    public ResponseEntity<Long> publicar(@PathVariable Long id) {
        return ResponseEntity.ok(oSilvestreService.publicar(id));
    }

    // despublicar post
    @PutMapping("/despublicar/{id}")
    public ResponseEntity<Long> despublicar(@PathVariable Long id) {
        return ResponseEntity.ok(oSilvestreService.despublicar(id));
    }
}


package net.ausiasmarch.persutil.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import net.ausiasmarch.persutil.entity.GarciaEntity;
import net.ausiasmarch.persutil.service.GarciaService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/garcia")
public class GarciaApi {

    @Autowired
    GarciaService GarciaService;

    @GetMapping("/rellena/{numPosts}")
    public ResponseEntity<Long> rellenaBlog(
            @PathVariable Long numPosts) {
        return ResponseEntity.ok(GarciaService.createRandom(numPosts));
    }

    @PostMapping("/random/{cantidad}")
    public ResponseEntity<Long> createRandom(@PathVariable Long cantidad) {
        return ResponseEntity.ok(GarciaService.createRandom(cantidad));
    }

    // ----------------------------CRUD---------------------------------

    // obtener post por id
    @GetMapping("/{id}")
    public ResponseEntity<GarciaEntity> get(@PathVariable Long id) {
        return ResponseEntity.ok(GarciaService.get(id));
    }

    // crear posts
    @PostMapping("")
    public ResponseEntity<Long> create(@RequestBody GarciaEntity garciaEntity) {
        return ResponseEntity.ok(GarciaService.create(garciaEntity));
    }

    // modificar posts
    @PutMapping("")
    public ResponseEntity<Long> update(@RequestBody GarciaEntity garciaEntity) {
        return ResponseEntity.ok(GarciaService.update(garciaEntity));
    }

    // borrar posts
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id) {
        return ResponseEntity.ok(GarciaService.delete(id));
    }

    // listado paginado de posts
    @GetMapping("")
    public ResponseEntity<Page<GarciaEntity>> getPage(Pageable oPageable) {
        return ResponseEntity.ok(GarciaService.getPage(oPageable));

    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(GarciaService.count());
    }

    @GetMapping("/publico")
    public ResponseEntity<Page<GarciaEntity>> getPagePublico(Pageable oPageable) {
        return ResponseEntity.ok(GarciaService.getPagePublico(oPageable));
    }

    @PutMapping("/publicar/{id}")
    public ResponseEntity<Long> publicar(@PathVariable Long id) {
        return ResponseEntity.ok(GarciaService.publicar(id));
    }

    @PutMapping("/despublicar/{id}")
    public ResponseEntity<Long> despublicar(@PathVariable Long id) {
        return ResponseEntity.ok(GarciaService.despublicar(id));
    }

    @DeleteMapping("/deleteall")
    public ResponseEntity<Long> deleteAll() {
        return ResponseEntity.ok(GarciaService.deleteAll());
    }

}

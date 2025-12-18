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

import net.ausiasmarch.persutil.entity.SemperteguiEntity;
import net.ausiasmarch.persutil.service.SemperteguiService;

@CrossOrigin(origins="*", allowedHeaders="*")
@RestController
@RequestMapping("/sempertegui")
public class SemperteguiApi {
    
    @Autowired
    SemperteguiService semperteguiService;

    // ---------------------------Rellenar datos fake ---------------------------
    
    @GetMapping("/rellena/{numPosts}")
    public ResponseEntity<Long> rellenaPeliculas(@PathVariable Long numPosts){
        return ResponseEntity.ok(semperteguiService.rellenaPeliculas(numPosts));
    }

    // ----------------------------CRUD---------------------------------

    // Obtener un listado paginado de todas las películas (getAll)
    @GetMapping("")
    public ResponseEntity<Page<SemperteguiEntity>> getPage(Pageable pageable) {
        return ResponseEntity.ok(semperteguiService.getPage(pageable));
    }

    // Obtener película por id
    @GetMapping("/{id}")
    public ResponseEntity<SemperteguiEntity> get(@PathVariable Long id) {
        return ResponseEntity.ok(semperteguiService.get(id));
    }

    // Crear película
    @PostMapping("")
    public ResponseEntity<Long> create(@RequestBody SemperteguiEntity semperteguiEntity) {
        return ResponseEntity.ok(semperteguiService.create(semperteguiEntity));
    }

    // Modificar película
    @PutMapping("")
    public ResponseEntity<Long> update(@RequestBody SemperteguiEntity semperteguiEntity) {
        return ResponseEntity.ok(semperteguiService.update(semperteguiEntity));
    }

    // Borrar película por id
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id) {
        return ResponseEntity.ok(semperteguiService.delete(id));
    }

    // vaciar tabla película (solo administradores)
    @DeleteMapping("/empty")
    public ResponseEntity<Long> empty() {
        return ResponseEntity.ok(semperteguiService.empty());
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(semperteguiService.count()); 
    }


    // ----- Publicar o Despublicar

    // publicar post
    @PutMapping("/publicar/{id}")
    public ResponseEntity<Long> publicar(@PathVariable Long id) {
        return ResponseEntity.ok(semperteguiService.publicar(id));
    }

    // despublicar post
    @PutMapping("/despublicar/{id}")
    public ResponseEntity<Long> despublicar(@PathVariable Long id) {
        return ResponseEntity.ok(semperteguiService.despublicar(id));
    }
}

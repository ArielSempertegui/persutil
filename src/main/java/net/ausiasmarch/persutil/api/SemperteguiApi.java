package net.ausiasmarch.persutil.api;


import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/pelicula")
    public ResponseEntity<String> pelicula(){
        return ResponseEntity.ok("HOLA ESTA TODO OK");
    }

    @GetMapping("/rellena")
    public ResponseEntity<Long> rellenaPeliculas(){
        return ResponseEntity.ok(semperteguiService.rellenaPeliculas());
    }

    // ----------------------------CRUD---------------------------------

    // Obtener una película por id
    @GetMapping("/{id}")
    public ResponseEntity<SemperteguiEntity> get(@PathVariable Long id) {
        return ResponseEntity.ok(semperteguiService.get(id));
    }

    // Crear una película
    @PostMapping("")
    public ResponseEntity<Long> create(@RequestBody SemperteguiEntity semperteguiEntity) {
        return ResponseEntity.ok(semperteguiService.create(semperteguiEntity));
    }

    // Modificar una película
    @PutMapping("")
    public ResponseEntity<Long> update(@RequestBody SemperteguiEntity semperteguiEntity) {
        return ResponseEntity.ok(semperteguiService.update(semperteguiEntity));
    }

    // Borrar una película
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id) {
        return ResponseEntity.ok(semperteguiService.delete(id));
    }
}

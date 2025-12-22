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

import net.ausiasmarch.persutil.entity.PavonEntity;
import net.ausiasmarch.persutil.service.PavonService;

@CrossOrigin(origins = "*", allowedHeaders = "*")    //ESTO ES PARA SOLUCIONAR EL CORS
@RestController
@RequestMapping("/recurso")
public class PavonApi {

    @Autowired
    PavonService oPavonService;

    // ----------------------------CRUD---------------------------------

    // obtener post por id
    @GetMapping("/{id}")
    public ResponseEntity<PavonEntity> get(@PathVariable Long id) {
        return ResponseEntity.ok(oPavonService.get(id));
    }

    // crear posts
    @PostMapping("")
    public ResponseEntity<Long> create(@RequestBody PavonEntity pavonEntity) {
        return ResponseEntity.ok(oPavonService.create(pavonEntity));
    }

    // modificar posts
    @PutMapping("")
    public ResponseEntity<Long> update(@RequestBody PavonEntity pavonEntity) {
        return ResponseEntity.ok(oPavonService.update(pavonEntity));
    }

    // borrar posts
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id) {
        return ResponseEntity.ok(oPavonService.delete(id));
    }

    // listado paginado de posts
    @GetMapping("")
    public ResponseEntity<Page<PavonEntity>> getPage(Pageable oPageable) {
        return ResponseEntity.ok(oPavonService.getPage(oPageable));
        
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(oPavonService.count()); 
    }

    @PostMapping("/generate-fake/{cantidad}")
    public ResponseEntity<Long> generateFakeData(@PathVariable int cantidad) {
        return ResponseEntity.ok(oPavonService.generateFakeData(cantidad));
    }

    // publicar post
    @PutMapping("/publicar/{id}")
    public ResponseEntity<Long> publicar(@PathVariable Long id) {
        return ResponseEntity.ok(oPavonService.publicar(id));
    }

    // despublicar post
    @PutMapping("/despublicar/{id}")
    public ResponseEntity<Long> despublicar(@PathVariable Long id) {
        return ResponseEntity.ok(oPavonService.despublicar(id));
    }
}

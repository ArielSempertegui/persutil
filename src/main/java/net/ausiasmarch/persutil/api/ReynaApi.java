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

import net.ausiasmarch.persutil.entity.ReynaEntity;
import net.ausiasmarch.persutil.service.ReynaService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/frasesmotivacionales")
public class ReynaApi {

    @Autowired
    ReynaService oReynaService;

    @GetMapping("/rellena/{numPosts}")
    public ResponseEntity<Long> rellenaBlog(
            @PathVariable Long numPosts) {
        return ResponseEntity.ok(oReynaService.createRandom(numPosts));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReynaEntity> getById(@PathVariable Long id) {
        return ResponseEntity.ok(oReynaService.get(id));
    }

    @PostMapping("")
    public ResponseEntity<Long> create(@RequestBody ReynaEntity oReynaEntity) {
        return ResponseEntity.ok(oReynaService.create(oReynaEntity));
    }

    @PutMapping("")
    public ResponseEntity<Long> update(@RequestBody ReynaEntity oReynaEntity) {
        return ResponseEntity.ok(oReynaService.update(oReynaEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id) {
        return ResponseEntity.ok(oReynaService.delete(id));
    }

    // vaciar tabla blog (solo administradores)
    @DeleteMapping("/empty")
    public ResponseEntity<Long> empty() {
        return ResponseEntity.ok(oReynaService.empty());
    }

    @GetMapping("")
    public ResponseEntity<Page<ReynaEntity>> getPage(Pageable oPageable) {
        return ResponseEntity.ok(oReynaService.getPage(oPageable));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(oReynaService.count());
    }

    // publicar
    @PutMapping("/publicar/{id}")
    public ResponseEntity<Long> publicar(@PathVariable Long id) {
        return ResponseEntity.ok(oReynaService.publicar(id));
    }

    // despublicar post
    @PutMapping("/despublicar/{id}")
    public ResponseEntity<Long> despublicar(@PathVariable Long id) {
        return ResponseEntity.ok(oReynaService.despublicar(id));
    }
}
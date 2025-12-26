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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.ausiasmarch.persutil.entity.AlfonsoRespuestaEntity;
import net.ausiasmarch.persutil.service.AlfonsoRespuestaService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/alfonsorespuesta")
public class AlfonsoRespuestaApi {

    @Autowired
    private AlfonsoRespuestaService oAlfonsoRespuestaService;

    @GetMapping("/rellena/{cantidad}")
    public ResponseEntity<Long> rellenar(@PathVariable Long cantidad) {
        return ResponseEntity.ok(oAlfonsoRespuestaService.rellenaRespuestas(cantidad));
    }

    // vaciar tabla (solo administradores)
    @DeleteMapping("/empty")
    public ResponseEntity<Long> empty() {
        return ResponseEntity.ok(oAlfonsoRespuestaService.empty());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlfonsoRespuestaEntity> get(@PathVariable Long id) {
        return ResponseEntity.ok(oAlfonsoRespuestaService.get(id));
    }

    @PostMapping("")
    public ResponseEntity<Long> create(@RequestBody AlfonsoRespuestaEntity entity) {
        return ResponseEntity.ok(oAlfonsoRespuestaService.create(entity));
    }

    @PutMapping("")
    public ResponseEntity<Long> update(@RequestBody AlfonsoRespuestaEntity entity) {
        return ResponseEntity.ok(oAlfonsoRespuestaService.update(entity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id) {
        return ResponseEntity.ok(oAlfonsoRespuestaService.delete(id));
    }

    // publicar
    @PutMapping("/publicar/{id}")
    public ResponseEntity<Long> publicar(@PathVariable Long id) {
        return ResponseEntity.ok(oAlfonsoRespuestaService.publicar(id));
    }

    // despublicar
    @PutMapping("/despublicar/{id}")
    public ResponseEntity<Long> despublicar(@PathVariable Long id) {
        return ResponseEntity.ok(oAlfonsoRespuestaService.despublicar(id));
    }

    @GetMapping("")
    public ResponseEntity<Page<AlfonsoRespuestaEntity>> getPage(
            @RequestParam(required = false) String filter,
            Pageable pageable) {
        return ResponseEntity.ok(oAlfonsoRespuestaService.getPage(filter, pageable));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(oAlfonsoRespuestaService.count());
    }

    @GetMapping("/count/visible")
    public ResponseEntity<Long> countVisible() {
        return ResponseEntity.ok(oAlfonsoRespuestaService.countVisible());
    }
}

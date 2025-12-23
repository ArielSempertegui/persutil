package net.ausiasmarch.persutil.api;

import java.util.List;

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

import net.ausiasmarch.persutil.entity.AlcaldeEntity;
import net.ausiasmarch.persutil.service.AlcaldeService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/alcalde")
public class AlcaldeApi {

    @Autowired
    private AlcaldeService oAlcaldeService;

    @GetMapping("")
    public ResponseEntity<Page<AlcaldeEntity>> getPage(Pageable pageable,
            @RequestParam(name = "publicado", required = false) Boolean soloPublicados) {
        return ResponseEntity.ok(oAlcaldeService.getPage(pageable, soloPublicados));
    }

    @GetMapping("/filtered")
    public ResponseEntity<Page<AlcaldeEntity>> getPageFiltered(Pageable pageable,
            @RequestParam(name = "genero", required = false) String genero,
            @RequestParam(name = "minValoracion", defaultValue = "0") int minValoracion) {
        return ResponseEntity.ok(oAlcaldeService.getPageFiltered(pageable, genero, minValoracion));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlcaldeEntity> get(@PathVariable Long id) {
        return ResponseEntity.ok(oAlcaldeService.get(id));
    }

    @PostMapping("")
    public ResponseEntity<Long> create(@RequestBody AlcaldeEntity entidad) {
        return ResponseEntity.ok(oAlcaldeService.create(entidad));
    }

    @PutMapping("")
    public ResponseEntity<Long> update(@RequestBody AlcaldeEntity entidad) {
        return ResponseEntity.ok(oAlcaldeService.update(entidad));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id) {
        return ResponseEntity.ok(oAlcaldeService.delete(id));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(oAlcaldeService.count());
    }

    @GetMapping("/generos")
    public ResponseEntity<List<String>> generos() {
        return ResponseEntity.ok(oAlcaldeService.getGeneros());
    }

    @GetMapping("/selection")
    public ResponseEntity<List<AlcaldeEntity>> selection(
            @RequestParam(defaultValue = "6") int limit) {
        return ResponseEntity.ok(oAlcaldeService.getSeleccion(limit));
    }

    @GetMapping("/rellena/{cantidad}")
    public ResponseEntity<Long> rellena(@PathVariable Long cantidad) {
        return ResponseEntity.ok(oAlcaldeService.rellena(cantidad));
    }

    @DeleteMapping("/empty")
    public ResponseEntity<Long> empty() {
        return ResponseEntity.ok(oAlcaldeService.empty());
    }

    @PutMapping("/publicar/{id}")
    public ResponseEntity<Long> publicar(@PathVariable Long id) {
        return ResponseEntity.ok(oAlcaldeService.publicar(id));
    }

    @PutMapping("/despublicar/{id}")
    public ResponseEntity<Long> despublicar(@PathVariable Long id) {
        return ResponseEntity.ok(oAlcaldeService.despublicar(id));
    }
}

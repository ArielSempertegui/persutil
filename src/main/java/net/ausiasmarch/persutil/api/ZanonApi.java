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
import net.ausiasmarch.persutil.entity.ZanonEntity;
import net.ausiasmarch.persutil.service.ZanonService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/zanon")
public class ZanonApi {

    @Autowired
    ZanonService oZanonService;
    
    @GetMapping("/registro/{numPosts}")
    public ResponseEntity<Long> registroRutinas(
            @PathVariable Long numPosts
    ) {
        return ResponseEntity.ok(oZanonService.registroRutinas(numPosts));
    }

    @GetMapping("/rellena/{numPosts}")
    public ResponseEntity<Integer> rellenar(
            @PathVariable int numPosts
    ) {
        int rutinasCreadas = oZanonService.rellenarRutinas(numPosts);
        return ResponseEntity.ok(rutinasCreadas);
    }

    // ------------------------------ CRUD ------------------------------

    // Obtener POST por ID
    @GetMapping("/{id}")
    public ResponseEntity<ZanonEntity> get(@PathVariable Long id) {
        return ResponseEntity.ok(oZanonService.get(id));
    }

    // Crear POSTS
    @PostMapping("")
    public ResponseEntity<Long> create(@RequestBody ZanonEntity ZanonEntity) {
        return ResponseEntity.ok(oZanonService.create(ZanonEntity));
    }

    // Modificar POSTS
    @PutMapping("")
    public ResponseEntity<Long> update(@RequestBody ZanonEntity ZanonEntity) {
        return ResponseEntity.ok(oZanonService.update(ZanonEntity));
    }

    // Borrar POSTS
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id) {
        return ResponseEntity.ok(oZanonService.delete(id));
    }

    // Listado paginado de POSTS
    @GetMapping("")
    public ResponseEntity<Page<ZanonEntity>> getPage(
        Pageable oPageable,
        @RequestParam(name = "publico", required = false) Boolean publico) {
        return ResponseEntity.ok(oZanonService.getPage(oPageable, publico));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(oZanonService.count()); 
    }

    // Vaciar la tabla (solo administradores)
    @DeleteMapping("/empty")
    public ResponseEntity<Long> empty() {
        return ResponseEntity.ok(oZanonService.empty());
    }

    // Publicar POST
    @PutMapping("/publicar/{id}")
    public ResponseEntity<Long> publicar(@PathVariable Long id) {
        return ResponseEntity.ok(oZanonService.publicar(id));
    }

    // Despublicar POST
    @PutMapping("/despublicar/{id}")
    public ResponseEntity<Long> despublicar(@PathVariable Long id) {
        return ResponseEntity.ok(oZanonService.despublicar(id));
    }
}

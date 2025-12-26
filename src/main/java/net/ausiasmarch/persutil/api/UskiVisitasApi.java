package net.ausiasmarch.persutil.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;

import net.ausiasmarch.persutil.entity.UskiVisitasEntity;
import net.ausiasmarch.persutil.service.UskiVisitasService;

@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", methods = { RequestMethod.GET, RequestMethod.POST,
        RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS })
@RestController
@RequestMapping("/visitas")
public class UskiVisitasApi {

    @Autowired
    UskiVisitasService oUskiVisitasService;

    // listado paginado de registros
    @GetMapping("")
    public ResponseEntity<Page<UskiVisitasEntity>> getPublicPage(@NonNull Pageable oPageable) {
        return ResponseEntity.ok(oUskiVisitasService.getPublicPage(oPageable));
    }

    // listado paginado para dashboard (admin): incluye todos los registros
    @GetMapping("/dashboard")
    public ResponseEntity<Page<UskiVisitasEntity>> getAdminPage(@NonNull Pageable oPageable) {
        return ResponseEntity.ok(oUskiVisitasService.getAdminPage(oPageable));
    }

    // obtener registro por id
    @GetMapping("/{id}")
    public ResponseEntity<UskiVisitasEntity> get(@NonNull @PathVariable Long id) {
        return ResponseEntity.ok(oUskiVisitasService.get(id));
    }

    // crear registro
    @PostMapping("")
    public ResponseEntity<Long> create(@RequestBody UskiVisitasEntity visitasEntity) {
        return ResponseEntity.ok(oUskiVisitasService.create(visitasEntity));
    }

    // publicar u ocultar registro
    @PutMapping("/publish")
    public ResponseEntity<Long> publish(@RequestBody UskiVisitasEntity visitasEntity) {
        return ResponseEntity.ok(oUskiVisitasService.publish(visitasEntity));
    }

    // modificar registro
    @PutMapping("/update")
    public ResponseEntity<Long> update(@RequestBody UskiVisitasEntity visitasEntity) {
        return ResponseEntity.ok(oUskiVisitasService.update(visitasEntity));
    }

    // borrar registro
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@NonNull @PathVariable Long id) {
        return ResponseEntity.ok(oUskiVisitasService.delete(id));
    }

    // borrar todos los registros
    @DeleteMapping("/delete_all")
    public ResponseEntity<Long> deleteAll() {
        return ResponseEntity.ok(oUskiVisitasService.deleteAll());
    }

    // rellenar datos fake visitas
    @GetMapping("/rellena/{numPosts}")
    public ResponseEntity<Long> rellenaBlog(@PathVariable Long numPosts) {
        return ResponseEntity.ok(oUskiVisitasService.rellenaBlog(numPosts));
    }
}

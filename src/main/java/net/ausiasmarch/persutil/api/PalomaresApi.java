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
import org.springframework.web.bind.annotation.RestController;

import net.ausiasmarch.persutil.entity.PalomaresEntity;
import net.ausiasmarch.persutil.service.AleatorioService;
import net.ausiasmarch.persutil.service.PalomaresService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/palomares")
public class PalomaresApi {

    @Autowired
    AleatorioService oAleatorioService;

    @Autowired
    PalomaresService oPalomaresService;

    @GetMapping("/saludar")
    public ResponseEntity<String> saludar() {
        return new ResponseEntity<>("\"Hola desde palomares\"", HttpStatus.OK);
    }

    @GetMapping("/saludar/buenosdias")
    public ResponseEntity<String> saludarPorLaMañana() {
        return new ResponseEntity<>("\"Hola buenos dias desde palomares\"", HttpStatus.OK);
    }

    @GetMapping("/aleatorio")
    public ResponseEntity<Integer> aleatorio() {
        int numeroAleatorio = (int) (Math.random() * 100) + 1;
        return ResponseEntity.ok(numeroAleatorio);
    }

    @GetMapping("/aleatorio/{min}/{max}")
    public ResponseEntity<Integer> aleatorioEnRango(
            @PathVariable int min,
            @PathVariable int max) {
        int numeroAleatorio = (int) (Math.random() * (max - min + 1)) + min;
        return ResponseEntity.ok(numeroAleatorio);
    }

    @GetMapping("/aleatorio/service/{min}/{max}")
    public ResponseEntity<Integer> aleatorioUsandoServiceEnRango(
            @PathVariable int min,
            @PathVariable int max) {
        return ResponseEntity.ok(oAleatorioService.GenerarNumeroAleatorioEnteroEnRango(min, max));
    }

    // ---------------------------Rellenar datos fake---------------------------------

    @GetMapping("/rellena/{numTareas}")
    public ResponseEntity<Long> rellenaTareas(
            @PathVariable Long numTareas) {
        return ResponseEntity.ok(oPalomaresService.rellenaTareas(numTareas));
    }

    // ----------------------------CRUD---------------------------------

    @GetMapping("/{id}")
    public ResponseEntity<PalomaresEntity> get(@PathVariable Long id) {
        return ResponseEntity.ok(oPalomaresService.get(id));
    }

    @PostMapping("")
    public ResponseEntity<Long> create(@RequestBody PalomaresEntity palomaresEntity) {
        return ResponseEntity.ok(oPalomaresService.create(palomaresEntity));
    }

    @PutMapping("")
    public ResponseEntity<Long> update(@RequestBody PalomaresEntity palomaresEntity) {
        return ResponseEntity.ok(oPalomaresService.update(palomaresEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id) {
        return ResponseEntity.ok(oPalomaresService.delete(id));
    }

    @DeleteMapping("/empty")
    public ResponseEntity<Long> empty() {
        return ResponseEntity.ok(oPalomaresService.empty());
    }

    @GetMapping("")
    public ResponseEntity<Page<PalomaresEntity>> getPage(Pageable oPageable) {
        return ResponseEntity.ok(oPalomaresService.getPage(oPageable));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(oPalomaresService.count());
    }

    // -----

    @PutMapping("/publicar/{id}")
    public ResponseEntity<Long> publicar(@PathVariable Long id) {
        return ResponseEntity.ok(oPalomaresService.publicar(id));
    }

    @PutMapping("/despublicar/{id}")
    public ResponseEntity<Long> despublicar(@PathVariable Long id) {
        return ResponseEntity.ok(oPalomaresService.despublicar(id));
    }
}

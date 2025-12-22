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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.ausiasmarch.persutil.entity.CalinescuEntity;
import net.ausiasmarch.persutil.service.CalinescuService;

/**
 * Controlador REST para la gestión de la lista de compra.
 * 
 * Proporciona endpoints para operaciones CRUD sobre items de la lista de compra,
 * incluyendo funcionalidades de publicación, filtrado, y generación de datos de prueba.
 * 
 * Todos los endpoints soportan CORS para permitir peticiones desde el frontend.
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/calinescuListaCompra")

public class CalinescuApi {

@Autowired
CalinescuService oCalinescuService;

 /**
  * Genera una cantidad específica de items de prueba en la lista de compra.
  * Requiere sesión activa de administrador.
  * 
  * @param numProductos Número de productos a generar
  * @return Número total de items en la lista después de la generación
  */
 @GetMapping("/rellena/{numProductos}")
    public ResponseEntity<Long> rellenaListaCompra(
            @PathVariable Long numProductos
    ) {
        return ResponseEntity.ok(oCalinescuService.rellenaListaCompra(numProductos));
    }
    
    /**
     * Endpoint de prueba para verificar conectividad.
     * 
     * @return Mensaje de saludo
     */
    @GetMapping("/saludar")
    public ResponseEntity<String> saludar() {
        return new ResponseEntity<>("\"Hola desde el blog\"", HttpStatus.OK);
    }
    
    /**
     * Obtiene un item por su ID.
     * Si no hay sesión activa, solo retorna items publicados.
     * 
     * @param id Identificador del item
     * @return Item encontrado
     */
    @GetMapping("/{id}")
    public ResponseEntity<CalinescuEntity> get(@PathVariable Long id) {
        return ResponseEntity.ok(oCalinescuService.get(id));
    }

    /**
     * Crea un nuevo item en la lista de compra.
     * Requiere sesión activa de administrador.
     * 
     * @param calinescuEntity Datos del item a crear
     * @return ID del item creado
     */
    @PostMapping("")
    public ResponseEntity<Long> create(@RequestBody CalinescuEntity calinescuEntity) {
        return ResponseEntity.ok(oCalinescuService.create(calinescuEntity));
    }

    /**
     * Actualiza un item existente en la lista de compra.
     * Requiere sesión activa de administrador.
     * 
     * @param calinescuEntity Datos actualizados del item
     * @return ID del item actualizado
     */
    @PutMapping("")
    public ResponseEntity<Long> update(@RequestBody CalinescuEntity calinescuEntity) {
        return ResponseEntity.ok(oCalinescuService.update(calinescuEntity));
    }

    /**
     * Elimina un item de la lista de compra.
     * Requiere sesión activa de administrador.
     * 
     * @param id Identificador del item a eliminar
     * @return ID del item eliminado
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id) {
        return ResponseEntity.ok(oCalinescuService.delete(id));
    }

    /**
     * Obtiene una página de items con opciones de filtrado y paginación.
     * Si no hay sesión activa, solo retorna items publicados.
     * 
     * @param oPageable Información de paginación y ordenamiento
     * @param publicado Filtro opcional por estado de publicación
     * @param filter Filtro opcional por texto en nombre o contenido
     * @return Página de items que cumplen los criterios
     */
    @GetMapping("")
    public ResponseEntity<Page<CalinescuEntity>> getPage(
            Pageable oPageable,
            @RequestParam(required = false) Boolean publicado,
            @RequestParam(required = false) String filter) {
        return ResponseEntity.ok(oCalinescuService.getPage(oPageable, publicado, filter));
        
    }

    /**
     * Cuenta el número de items según criterios de filtrado.
     * 
     * @param publicado Filtro opcional por estado de publicación
     * @param filter Filtro opcional por texto en nombre o contenido
     * @return Número de items que cumplen los criterios
     */
    @GetMapping("/count")
    public ResponseEntity<Long> count(@RequestParam(required = false) Boolean publicado, @RequestParam(required = false) String filter) {
        return ResponseEntity.ok(oCalinescuService.count(publicado, filter)); 
    }

    /**
     * Calcula el precio total de los items según estado de publicación.
     * El total considera precio × cantidad de cada item.
     * 
     * @param publicado Filtro opcional por estado de publicación
     * @return Suma total de precios
     */
    @GetMapping("/total")
    public ResponseEntity<Double> calcularTotalPrecios(@RequestParam(required = false) Boolean publicado) {
        return ResponseEntity.ok(oCalinescuService.calcularTotalPrecios(publicado));
    }

    /**
     * Elimina todos los items de la lista de compra.
     * Requiere sesión activa de administrador.
     * Operación irreversible.
     * 
     * @return Número de items eliminados
     */
    @DeleteMapping("/all")
    public ResponseEntity<Long> deleteAll() {
        return ResponseEntity.ok(oCalinescuService.deleteAll());
    }

    /**
     * Marca un item como publicado.
     * Requiere sesión activa de administrador.
     * 
     * @param id Identificador del item a publicar
     * @return ID del item publicado
     */
    @PostMapping("/publicar/{id}")
    public ResponseEntity<Long> publicar(@PathVariable Long id) {
        return ResponseEntity.ok(oCalinescuService.publicar(id));
    }

    /**
     * Marca un item como no publicado.
     * Requiere sesión activa de administrador.
     * 
     * @param id Identificador del item a despublicar
     * @return ID del item despublicado
     */
    @PostMapping("/despublicar/{id}")
    public ResponseEntity<Long> despublicar(@PathVariable Long id) {
        return ResponseEntity.ok(oCalinescuService.despublicar(id));
    }

}

package net.ausiasmarch.persutil.service;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.ausiasmarch.persutil.entity.CalinescuEntity;
import net.ausiasmarch.persutil.exception.ResourceNotFoundException;
import net.ausiasmarch.persutil.exception.UnauthorizedException;
import net.ausiasmarch.persutil.repository.CalinescuRepository;

/**
 * Servicio de lógica de negocio para la gestión de la lista de compra.
 * 
 * Implementa las operaciones CRUD y funcionalidades adicionales como
 * generación de datos de prueba, filtrado, publicación y cálculos de totales.
 * Incluye verificación de sesión para operaciones sensibles.
 */
@Service
public class CalinescuService {

    @Autowired
    CalinescuRepository oCalinescuRepository;

    @Autowired
    AleatorioService oAleatorioService;

    @Autowired
    SessionService oSessionService;

    /** Lista de nombres de productos para generar datos de prueba */
    ArrayList<String> alNombres = new ArrayList<>();

    /**
     * Constructor que inicializa la lista de nombres de productos base.
     */
    public CalinescuService() {
        alNombres.add("Pan");
        alNombres.add("Leche");
        alNombres.add("Huevos");
        alNombres.add("Azucar");
        alNombres.add("Sal");
        alNombres.add("Arroz");
        alNombres.add("Pasta");
        alNombres.add("Aceite");
        alNombres.add("Tomate");
        alNombres.add("Cafe");
    }

    /**
     * Genera una cantidad específica de items de prueba en la lista de compra.
     * Requiere sesión activa de administrador.
     * 
     * Los items generados tienen:
     * - Nombre aleatorio de la lista base + número secuencial
     * - Contenido con items numerados (1-5 items)
     * - Estado publicado
     * - Fecha de creación actual
     * 
     * @param numProductos Número de productos a generar
     * @return Total de items en la lista después de la generación
     * @throws UnauthorizedException si no hay sesión activa
     */
    public Long rellenaListaCompra(Long numProductos) {

        if (!oSessionService.isSessionActive()) {
            throw new UnauthorizedException("No active session");
        }

        for (long j = 0; j < numProductos; j++) {
            CalinescuEntity oCalinescuEntity = new CalinescuEntity();
            oCalinescuEntity.setNombre(
                    alNombres.get(oAleatorioService.GenerarNumeroAleatorioEnteroEnRango(0, alNombres.size() - 1))
                            + j);
            // Genera contenido con items numerados
            String contenidoGenerado = "";
            int numItems = oAleatorioService.GenerarNumeroAleatorioEnteroEnRango(1, 5);
            for (int i = 1; i <= numItems; i++) {
                contenidoGenerado += "Item" + i + " ";
            }
            oCalinescuEntity.setContenido(contenidoGenerado.trim());
            oCalinescuEntity.setFecha_compra_esperada(null);
            oCalinescuEntity.setFecha_creacion(LocalDateTime.now());
            oCalinescuEntity.setFecha_modificacion(null);
            oCalinescuEntity.setPublicado(true);
            oCalinescuRepository.save(oCalinescuEntity);
        }
        return oCalinescuRepository.count();
    }

    /**
     * Obtiene un item por su ID.
     * Si hay sesión activa, retorna cualquier item.
     * Si no hay sesión, solo retorna items publicados.
     * 
     * @param id Identificador del item
     * @return Item encontrado
     * @throws ResourceNotFoundException si el item no existe o no está publicado (sin sesión)
     */
    public CalinescuEntity get(Long id) {
        if (oSessionService.isSessionActive()) {
            return oCalinescuRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ListaCompra not found"));
        } else {
            CalinescuEntity entity = oCalinescuRepository.findByIdAndPublicadoTrue(id);
            if (entity == null) {
                throw new ResourceNotFoundException("ListaCompra not found or not published");
            }
            return entity;
        }
    }

    /**
     * Crea un nuevo item en la lista de compra.
     * Requiere sesión activa de administrador.
     * Establece automáticamente la fecha de creación.
     * 
     * @param calinescuEntity Datos del item a crear
     * @return ID del item creado
     * @throws UnauthorizedException si no hay sesión activa
     */
    public Long create(CalinescuEntity calinescuEntity) {
        if (!oSessionService.isSessionActive()) {
            throw new UnauthorizedException("No active session");
        }
        calinescuEntity.setFecha_creacion(LocalDateTime.now());
        calinescuEntity.setFecha_modificacion(null);
        oCalinescuRepository.save(calinescuEntity);
        return calinescuEntity.getId();
    }

    /**
     * Actualiza un item existente en la lista de compra.
     * Requiere sesión activa de administrador.
     * Actualiza automáticamente la fecha de modificación.
     * 
     * @param calinescuEntity Datos actualizados del item (debe incluir ID)
     * @return ID del item actualizado
     * @throws UnauthorizedException si no hay sesión activa
     * @throws ResourceNotFoundException si el item no existe
     */
    public Long update(CalinescuEntity calinescuEntity) {
        if (!oSessionService.isSessionActive()) {
            throw new UnauthorizedException("No active session");
        }
        CalinescuEntity existingListaCompra = oCalinescuRepository.findById(calinescuEntity.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Lista de compra no encontrada"));
        existingListaCompra.setNombre(calinescuEntity.getNombre());
        existingListaCompra.setContenido(calinescuEntity.getContenido());
        existingListaCompra.setFecha_compra_esperada(calinescuEntity.getFecha_compra_esperada());
        existingListaCompra.setPublicado(calinescuEntity.isPublicado());
        existingListaCompra.setPrecio(calinescuEntity.getPrecio());
        existingListaCompra.setCantidad(calinescuEntity.getCantidad());
        existingListaCompra.setFecha_modificacion(LocalDateTime.now());
        oCalinescuRepository.save(existingListaCompra);
        return existingListaCompra.getId();
    }

    /**
     * Elimina un item de la lista de compra.
     * Requiere sesión activa de administrador.
     * 
     * @param id Identificador del item a eliminar
     * @return ID del item eliminado
     * @throws UnauthorizedException si no hay sesión activa
     */
    public Long delete(Long id) {
        if (!oSessionService.isSessionActive()) {
            throw new UnauthorizedException("No active session");
        }
        oCalinescuRepository.deleteById(id);
        return id;
    }

    /**
     * Obtiene una página de items con opciones de filtrado.
     * 
     * Si hay filtros (publicado o texto), realiza filtrado en memoria.
     * Si no hay sesión activa, solo retorna items publicados.
     * Si hay sesión y no hay filtros, retorna todos los items.
     * 
     * @param oPageable Información de paginación y ordenamiento
     * @param publicado Filtro opcional por estado de publicación
     * @param filter Filtro opcional por texto en nombre o contenido
     * @return Página de items que cumplen los criterios
     */
    public Page<CalinescuEntity> getPage(Pageable oPageable, Boolean publicado, String filter) {
        // Si se solicita filtrado explícito por publicado y/o texto, aplicar filtro in-memory
        if (publicado != null || (filter != null && !filter.isBlank())) {
            // Filtrar manualmente la lista completa y crear una sublista paginada
            var todasLasEntidades = oCalinescuRepository.findAll();
            var filtradas = todasLasEntidades.stream()
                    .filter(item -> (publicado == null || item.isPublicado() == publicado))
                    .filter(item -> {
                        if (filter == null || filter.isBlank()) return true;
                        String f = filter.toLowerCase();
                        boolean matchNombre = item.getNombre() != null && item.getNombre().toLowerCase().contains(f);
                        boolean matchContenido = item.getContenido() != null && item.getContenido().toLowerCase().contains(f);
                        return matchNombre || matchContenido;
                    })
                    .toList();

            int start = (int) oPageable.getOffset();
            int end = Math.min((start + oPageable.getPageSize()), filtradas.size());

            if (start > filtradas.size()) {
                start = 0;
                end = Math.min(oPageable.getPageSize(), filtradas.size());
            }

            var paginaActual = filtradas.subList(start, end);
            return new org.springframework.data.domain.PageImpl<>(
                    paginaActual,
                    oPageable,
                    filtradas.size()
            );
        }
        // Si no hay sesión activa, devolver solo publicados
        if (!oSessionService.isSessionActive()) {
            return oCalinescuRepository.findByPublicadoTrue(oPageable);
        } else {
            return oCalinescuRepository.findAll(oPageable);
        }
    }

    /**
     * Cuenta el número de items según criterios de filtrado.
     * 
     * @param publicado Filtro opcional por estado de publicación
     * @param filter Filtro opcional por texto en nombre o contenido
     * @return Número de items que cumplen los criterios
     */
    public Long count(Boolean publicado, String filter) {
        if (publicado != null || (filter != null && !filter.isBlank())) {
            return oCalinescuRepository.findAll().stream()
                    .filter(item -> (publicado == null || item.isPublicado() == publicado))
                    .filter(item -> {
                        if (filter == null || filter.isBlank()) return true;
                        String f = filter.toLowerCase();
                        return (item.getNombre() != null && item.getNombre().toLowerCase().contains(f))
                                || (item.getContenido() != null && item.getContenido().toLowerCase().contains(f));
                    })
                    .count();
        }
        return oCalinescuRepository.count();
    }

    /**
     * Calcula el precio total de los items.
     * El total considera precio × cantidad de cada item.
     * 
     * @param publicado Filtro opcional por estado de publicación
     * @return Suma total de (precio × cantidad) de todos los items filtrados
     */
    public Double calcularTotalPrecios(Boolean publicado) {
        return oCalinescuRepository.findAll().stream()
                .filter(item -> publicado == null || item.isPublicado() == publicado)
                .mapToDouble(item -> {
                    Double p = item.getPrecio();
                    Integer c = item.getCantidad();
                    double precio = (p != null) ? p : 0.0d;
                    int cantidad = (c != null) ? c : 1;
                    return precio * cantidad;
                })
                .sum();
    }

    /**
     * Elimina todos los items de la lista de compra.
     * Requiere sesión activa de administrador.
     * Operación irreversible.
     * 
     * @return Número de items eliminados
     * @throws UnauthorizedException si no hay sesión activa
     */
    public Long deleteAll() {
        if (!oSessionService.isSessionActive()) {
            throw new UnauthorizedException("No active session");
        }
        Long count = oCalinescuRepository.count();
        oCalinescuRepository.deleteAll();
        return count;
    }

    /**
     * Marca un item como publicado.
     * Requiere sesión activa de administrador.
     * Actualiza la fecha de modificación.
     * 
     * @param id Identificador del item a publicar
     * @return ID del item publicado
     * @throws UnauthorizedException si no hay sesión activa
     * @throws ResourceNotFoundException si el item no existe
     */
    public Long publicar(Long id) {
        if (!oSessionService.isSessionActive()) {
            throw new UnauthorizedException("No active session");
        }
        CalinescuEntity existing = oCalinescuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ListaCompra not found"));
        existing.setPublicado(true);
        existing.setFecha_modificacion(LocalDateTime.now());
        oCalinescuRepository.save(existing);
        return existing.getId();
    }

    /**
     * Marca un item como no publicado (oculto).
     * Requiere sesión activa de administrador.
     * Actualiza la fecha de modificación.
     * 
     * @param id Identificador del item a despublicar
     * @return ID del item despublicado
     * @throws UnauthorizedException si no hay sesión activa
     * @throws ResourceNotFoundException si el item no existe
     */
    public Long despublicar(Long id) {
        if (!oSessionService.isSessionActive()) {
            throw new UnauthorizedException("No active session");
        }
        CalinescuEntity existing = oCalinescuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ListaCompra not found"));
        existing.setPublicado(false);
        existing.setFecha_modificacion(LocalDateTime.now());
        oCalinescuRepository.save(existing);
        return existing.getId();
    }
}

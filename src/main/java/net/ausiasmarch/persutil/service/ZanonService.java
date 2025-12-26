package net.ausiasmarch.persutil.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.ausiasmarch.persutil.entity.ZanonEntity;
import net.ausiasmarch.persutil.exception.ResourceNotFoundException;
import net.ausiasmarch.persutil.exception.UnauthorizedException;
import net.ausiasmarch.persutil.repository.ZanonRepository;

@Service
public class ZanonService {

    @Autowired
    AleatorioService oAleatorioService;

    @Autowired
    ZanonRepository oZanonRepository;

    @Autowired
    SessionService oSessionService;

    ArrayList<String> rutinas = new ArrayList<>();

    public ZanonService() {
        rutinas.add("Pecho: Press banca 4x8, Aperturas 3x12, Fondos 3x10");
        rutinas.add("Espalda: Dominadas 4x6, Remo con barra 4x8, Jalón 3x12");
        rutinas.add("Piernas: Sentadilla 4x8, Prensa 4x12, Extensiones 3x15");
        rutinas.add("Hombro: Press militar 4x8, Elevaciones laterales 3x12");
        rutinas.add("Brazos: Curl bíceps 4x10, Tríceps polea 4x12");
        rutinas.add("Full Body: Sentadilla 3x10, Press banca 3x10, Remo 3x10");
    }

    private String extraerTitulo(String rutina) {
        String titulo = rutina.substring(0, rutina.indexOf(":"))
                              .trim()
                              .toLowerCase();
        return titulo;
    }

    private String extraerContenido(String rutina) {
        String contenido = rutina.substring(rutina.indexOf(":") + 1)
                                 .trim();
        return contenido;
    }

    private String extraerEtiquetas(String contenido) {
        String[] palabras = contenido
                 .replaceAll("[0-9]", "") // Quitamos los números
                 .replaceAll("[.,;:!?]", "") //Quitamos los signos
                 .toLowerCase()
                 .split(" ");
        
        return Arrays.stream(palabras)
                 .filter(p -> p.length() > 4)
                 .distinct()
                 .limit(5)
                 .collect(Collectors.joining(", "));
    }

    private Boolean esPublico(int num) {
        
        // Si "num" es 1, devuelve "true". Pero si no lo es, devuelve "false"
        return num == 1;
    }

    public Long registroRutinas(Long numPosts) {
        ZanonEntity.Dificultad[] dificultades = ZanonEntity.Dificultad.values();

        for (long j = 0; j < numPosts; j++) {

            // Llamámos al fichero ZanonEntity
            ZanonEntity oZanonEntity = new ZanonEntity();

            String rutina = rutinas.get(oAleatorioService.GenerarNumeroAleatorioEnteroEnRango(0, rutinas.size() -1));

            // Establecemos un título
            oZanonEntity.setTitulo(extraerTitulo(rutina));

            // Generamos contenido
            oZanonEntity.setContenido(extraerContenido(rutina));

            // Generamos etiquetas a partir del contenido
            oZanonEntity.setEtiquetas(extraerEtiquetas(extraerContenido(rutina)));

            // Establecemos la fecha de creación y modificación
            oZanonEntity.setFechaCreacion(LocalDateTime.now());
            oZanonEntity.setFechaModificacion(null);

            // Establecemos una duración aleatoria
            oZanonEntity.setDuracion(oAleatorioService.GenerarNumeroAleatorioEnteroEnRango(5, 60));

            // Establecemos una dificultad aleatoria
            oZanonEntity.setDificultad(dificultades[oAleatorioService.GenerarNumeroAleatorioEnteroEnRango(0, dificultades.length - 1)]);

            // Establecemos si la rutina es pública o no
            oZanonEntity.setPublico(esPublico(oAleatorioService.GenerarNumeroAleatorioEnteroEnRango(0, 1)));
            
            // Lo guardamos todo en la base de datos
            oZanonRepository.save(oZanonEntity);
        }

        // Se devuelve el total de registros
        return oZanonRepository.count();
    }

    // ------------------------------ CRUD ------------------------------

    public ZanonEntity get(Long id) {
        if (oSessionService.isSessionActive()) {
            return oZanonRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        } else{
            ZanonEntity oZanonEntity = oZanonRepository.findByIdAndPublicoTrue(id);

            if (oZanonEntity == null) {
                throw new ResourceNotFoundException("Post not found or not published");
            }

            return oZanonEntity;
        }
    }

    public Long create(ZanonEntity zanonEntity) {
        if (!oSessionService.isSessionActive()) {
            throw new UnauthorizedException("No active session");
        }
        
        zanonEntity.setFechaCreacion(LocalDateTime.now());
        zanonEntity.setFechaModificacion(null);

        // Si la imagen viene vacía o nula, establecemos una por defecto
        if (zanonEntity.getImagen() == null || zanonEntity.getImagen().isEmpty()) {
            zanonEntity.setImagen("https://avena.io/blog/wp-content/uploads/2023/09/Guia-completa-y-efectiva-para-tu-rutina-de-ejercicios-todo-lo-que-necesitas-saber-1.jpg");
        }

        oZanonRepository.save(zanonEntity);
        return zanonEntity.getId();
    }

    public Long update(ZanonEntity zanonEntity) {
        if (!oSessionService.isSessionActive()) {
            throw new UnauthorizedException("No active session");
        }

        ZanonEntity existingBlog = oZanonRepository.findById(zanonEntity.getId())
                .orElseThrow(() -> new RuntimeException("Blog not found"));
        existingBlog.setTitulo(zanonEntity.getTitulo());
        existingBlog.setContenido(zanonEntity.getContenido());
        existingBlog.setEtiquetas(zanonEntity.getEtiquetas());
        existingBlog.setFechaModificacion(LocalDateTime.now());
        existingBlog.setDuracion(zanonEntity.getDuracion());
        existingBlog.setDificultad(zanonEntity.getDificultad());
        existingBlog.setPublico(zanonEntity.getPublico());
        existingBlog.setImagen(zanonEntity.getImagen());
        oZanonRepository.save(existingBlog);
        return existingBlog.getId();
    }

    public Long delete(Long id) {
        if (!oSessionService.isSessionActive()) {
            throw new UnauthorizedException("No active session");
        }

        oZanonRepository.deleteById(id);
        return id;
    }

    public Page<ZanonEntity> getPage(Pageable oPageable, Boolean publico) {
        if (Boolean.TRUE.equals(publico)) {
            return oZanonRepository.findByPublicoTrue(oPageable);
        }
        
        // Si no hay session activa, solo devuelve los publicados
        if (!oSessionService.isSessionActive()) {
            return oZanonRepository.findByPublicoTrue(oPageable);
        } else {
            return oZanonRepository.findAll(oPageable);
        }
    }

    public Long count() {
        return oZanonRepository.count();
    }

    public int rellenarRutinas(int numPosts) {
        ZanonEntity.Dificultad[] dificultades = ZanonEntity.Dificultad.values();

        for (int i = 0; i < numPosts; i++) {
            ZanonEntity oZanonEntity = new ZanonEntity();

            String rutina = rutinas.get(oAleatorioService.GenerarNumeroAleatorioEnteroEnRango(0, rutinas.size() -1));

            oZanonEntity.setTitulo(extraerTitulo(rutina));
            oZanonEntity.setContenido(extraerContenido(rutina));
            oZanonEntity.setEtiquetas(extraerEtiquetas(extraerContenido(rutina)));
            oZanonEntity.setFechaCreacion(LocalDateTime.now());
            oZanonEntity.setFechaModificacion(null);
            oZanonEntity.setDuracion(oAleatorioService.GenerarNumeroAleatorioEnteroEnRango(5, 60));
            oZanonEntity.setDificultad(dificultades[oAleatorioService.GenerarNumeroAleatorioEnteroEnRango(0, dificultades.length - 1)]);
            oZanonEntity.setPublico(esPublico(oAleatorioService.GenerarNumeroAleatorioEnteroEnRango(0, 1)));

            oZanonRepository.save(oZanonEntity);
        }

        return numPosts;
    }

    public Long publicar(Long id) {
        if (!oSessionService.isSessionActive()) {
            throw new UnauthorizedException("No active session");
        }
        ZanonEntity oExistingBlog = oZanonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        oExistingBlog.setPublico(true);
        oExistingBlog.setFechaModificacion(LocalDateTime.now());
        oZanonRepository.save(oExistingBlog);
        return oExistingBlog.getId();
    }

    public Long despublicar(Long id) {
        if (!oSessionService.isSessionActive()) {
            throw new UnauthorizedException("No active session");
        }
        ZanonEntity oExistingBlog = oZanonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        oExistingBlog.setPublico(false);
        oExistingBlog.setFechaModificacion(LocalDateTime.now());
        oZanonRepository.save(oExistingBlog);
        return oExistingBlog.getId();
    }

    public Long empty() {
        if (!oSessionService.isSessionActive()) {
            throw new UnauthorizedException("No active session");
        }

        Long total = oZanonRepository.count();
        oZanonRepository.deleteAll();
        return total;
    }
}

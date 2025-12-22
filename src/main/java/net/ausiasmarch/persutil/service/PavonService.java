package net.ausiasmarch.persutil.service;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.ausiasmarch.persutil.entity.PavonEntity;
import net.ausiasmarch.persutil.exception.ResourceNotFoundException;
import net.ausiasmarch.persutil.exception.UnauthorizedException;
import net.ausiasmarch.persutil.repository.PavonRepository;

@Service    //Es necesatrio poner el @service para que lo detecten
public class PavonService {

    @Autowired
    PavonRepository oPavonRepository;

    @Autowired
    SessionService oSessionService;

     // ----------------------------CRUD---------------------------------
    public PavonEntity get(Long id) {
        return oPavonRepository.findById(id).orElseThrow(() -> new RuntimeException("Recurso not found"));
    }

    public Long create(PavonEntity pavonEntity) {
        pavonEntity.setFechaCreacion(LocalDateTime.now());
        pavonEntity.setFechaModificacion(null);
        oPavonRepository.save(pavonEntity);
        return pavonEntity.getId();
    }

    public Long update(PavonEntity pavonEntity) {
        PavonEntity existingBlog = oPavonRepository.findById(pavonEntity.getId())
                .orElseThrow(() -> new RuntimeException("Recurso not found"));
        existingBlog.setNombre(pavonEntity.getNombre());
        existingBlog.setUrl(pavonEntity.getUrl());
        existingBlog.setFechaModificacion(LocalDateTime.now());
        oPavonRepository.save(existingBlog);
        return existingBlog.getId();
    }

    public Long delete(Long id) {
        oPavonRepository.deleteById(id);
        return id;
    }

    public Page<PavonEntity> getPage(Pageable oPageable) {
        return oPavonRepository.findAll(oPageable);
    }

    public Long count() {
        return oPavonRepository.count();
    }

    // ----------------------------GENERAR DATOS FALSOS---------------------------------
    public Long generateFakeData(int cantidad) {
        Random random = new Random();
        String[] nombres = {
            "Recurso Educativo", "Tutorial Online", "Base de Datos", "Documentación API",
            "Librería JavaScript", "Framework Python", "Herramienta DevOps", "Plataforma Cloud",
            "IDE Profesional", "Sistema de Control", "Blog Técnico", "Repositorio Código",
            "Servidor Web", "Base de Conocimiento", "Marketplace Digital"
        };
        
        String[] dominios = {
            "https://www.ejemplo.com", "https://api.recurso.io", "https://docs.proyecto.dev",
            "https://github.com/usuario/repo", "https://cloud.servicio.com", "https://plataforma.tech",
            "https://recursos.edu.es", "https://blog.desarrollador.net", "https://cdn.contenido.com",
            "https://dashboard.monitor.io"
        };
        
        for (int i = 0; i < cantidad; i++) {
            PavonEntity nuevoRecurso = new PavonEntity();
            nuevoRecurso.setNombre(nombres[random.nextInt(nombres.length)] + " " + (i + 1));
            nuevoRecurso.setUrl(dominios[random.nextInt(dominios.length)] + "/recurso" + (i + 1));
            nuevoRecurso.setFechaCreacion(LocalDateTime.now());
            nuevoRecurso.setFechaModificacion(null);
            nuevoRecurso.setPublico(random.nextBoolean());
            oPavonRepository.save(nuevoRecurso);
        }
        
        return (long) cantidad;
    }

    public Long publicar(Long id) {
        if (!oSessionService.isSessionActive()) {
            throw new UnauthorizedException("No active session");
        }
        PavonEntity existingPavon = oPavonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        existingPavon.setPublico(true);
        existingPavon.setFechaModificacion(LocalDateTime.now());
        oPavonRepository.save(existingPavon);
        return existingPavon.getId();
    }

    public Long despublicar(Long id) {
        if (!oSessionService.isSessionActive()) {
            throw new UnauthorizedException("No active session");
        }
        PavonEntity existingPavon = oPavonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        existingPavon.setPublico(false);
        existingPavon.setFechaModificacion(LocalDateTime.now());
        oPavonRepository.save(existingPavon);
        return existingPavon.getId();
    }

    public Long empty() {
        if (!oSessionService.isSessionActive()) {
            throw new UnauthorizedException("No active session");
        }
        Long total = oPavonRepository.count();
        oPavonRepository.deleteAll();
        return total;
    }

}

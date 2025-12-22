package net.ausiasmarch.persutil.service;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.ausiasmarch.persutil.entity.PalomaresEntity;
import net.ausiasmarch.persutil.exception.ResourceNotFoundException;
import net.ausiasmarch.persutil.exception.UnauthorizedException;
import net.ausiasmarch.persutil.repository.PalomaresRepository;

@Service
public class PalomaresService {

    @Autowired
    PalomaresRepository oPalomaresRepository;

    @Autowired
    AleatorioService oAleatorioService;

    @Autowired
    SessionService oSessionService;

    ArrayList<String> alTitulos = new ArrayList<>();
    ArrayList<String> alDescripciones = new ArrayList<>();
    ArrayList<String> alCategorias = new ArrayList<>();

    public PalomaresService() {
        alTitulos.add("Terminar proyecto de presentación");
        alTitulos.add("Comprar artículos necesarios");
        alTitulos.add("Organizar reunión de equipo");
        alTitulos.add("Planificar entrenamiento mensual");
        alTitulos.add("Revisar documentación técnica");
        alTitulos.add("Estudiar para el examen");
        alTitulos.add("Leer sobre nuevas tecnologías");
        alTitulos.add("Hacer ejercicio en el gimnasio");
        alTitulos.add("Llamar a soporte técnico");
        alTitulos.add("Limpiar el área de trabajo");
        alTitulos.add("Preparar informe mensual");
        alTitulos.add("Actualizar el sistema");
        alTitulos.add("Configurar servidor de producción");
        alTitulos.add("Aprender nuevo framework");
        alTitulos.add("Practicar algoritmos");

        alDescripciones.add("Esta es una tarea importante que requiere atención inmediata.");
        alDescripciones.add("Tarea programada para completar durante esta semana.");
        alDescripciones.add("Actividad de seguimiento que debe realizarse periódicamente.");
        alDescripciones.add("Proyecto a largo plazo que necesita planificación detallada.");
        alDescripciones.add("Tarea urgente con alta prioridad de ejecución.");
        alDescripciones.add("Actividad opcional pero recomendable para el desarrollo personal.");
        alDescripciones.add("Tarea colaborativa que involucra a varios miembros del equipo.");
        alDescripciones.add("Proyecto individual que requiere concentración y dedicación.");

        alCategorias.add("Trabajo");
        alCategorias.add("Casa");
        alCategorias.add("Estudios");
        alCategorias.add("Deporte");
        alCategorias.add("Compras");
        alCategorias.add("Salud");
        alCategorias.add("Familia");
        alCategorias.add("Viajes");
        alCategorias.add("Tecnología");
        alCategorias.add("Hobby");
    }

    public Long rellenaTareas(Long numTareas) {

        if (!oSessionService.isSessionActive()) {
            throw new UnauthorizedException("No active session");
        }

        for (long j = 0; j < numTareas; j++) {
            PalomaresEntity oPalomaresEntity = new PalomaresEntity();
            oPalomaresEntity.setTitulo(
                    alTitulos.get(oAleatorioService.GenerarNumeroAleatorioEnteroEnRango(0, alTitulos.size() - 1)));
            
            String descripcionGenerada = "";
            int numFrases = oAleatorioService.GenerarNumeroAleatorioEnteroEnRango(1, 3);
            for (int i = 1; i <= numFrases; i++) {
                descripcionGenerada += alDescripciones
                        .get(oAleatorioService.GenerarNumeroAleatorioEnteroEnRango(0, alDescripciones.size() - 1)) + " ";
            }
            oPalomaresEntity.setDescripcion(descripcionGenerada.trim());
            
            oPalomaresEntity.setCategoria(
                    alCategorias.get(oAleatorioService.GenerarNumeroAleatorioEnteroEnRango(0, alCategorias.size() - 1)));
            
            oPalomaresEntity.setFechaCreacion(LocalDateTime.now());
            oPalomaresEntity.setFechaModificacion(null);
            
            oPalomaresEntity.setCompletada(oAleatorioService.GenerarNumeroAleatorioEnteroEnRango(0, 1) == 1);
            oPalomaresEntity.setPublicado(oAleatorioService.GenerarNumeroAleatorioEnteroEnRango(0, 1) == 1);
            
            oPalomaresRepository.save(oPalomaresEntity);
        }
        return oPalomaresRepository.count();
    }

    // ----------------------------CRUD---------------------------------
    public PalomaresEntity get(Long id) {
        if (oSessionService.isSessionActive()) {
            return oPalomaresRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tarea not found"));
        } else {
            PalomaresEntity palomaresEntity = oPalomaresRepository.findByIdAndPublicadoTrue(id);
            if (palomaresEntity == null) {
                throw new ResourceNotFoundException("Tarea not found or not published");
            }
            return palomaresEntity;
        }
    }

    public Long create(PalomaresEntity palomaresEntity) {
        if (!oSessionService.isSessionActive()) {
            throw new UnauthorizedException("No active session");
        }
        palomaresEntity.setFechaCreacion(LocalDateTime.now());
        palomaresEntity.setFechaModificacion(null);
        oPalomaresRepository.save(palomaresEntity);
        return palomaresEntity.getId();
    }

    public Long update(PalomaresEntity palomaresEntity) {
        if (!oSessionService.isSessionActive()) {
            throw new UnauthorizedException("No active session");
        }
        PalomaresEntity existingTarea = oPalomaresRepository.findById(palomaresEntity.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Tarea not found"));
        existingTarea.setTitulo(palomaresEntity.getTitulo());
        existingTarea.setDescripcion(palomaresEntity.getDescripcion());
        existingTarea.setCategoria(palomaresEntity.getCategoria());
        existingTarea.setCompletada(palomaresEntity.getCompletada());
        existingTarea.setPublicado(palomaresEntity.getPublicado());
        existingTarea.setFechaModificacion(LocalDateTime.now());
        oPalomaresRepository.save(existingTarea);
        return existingTarea.getId();
    }

    public Long delete(Long id) {
        if (!oSessionService.isSessionActive()) {
            throw new UnauthorizedException("No active session");
        }
        oPalomaresRepository.deleteById(id);
        return id;
    }

    public Page<PalomaresEntity> getPage(Pageable oPageable) {
        if (!oSessionService.isSessionActive()) {
            return oPalomaresRepository.findByPublicadoTrue(oPageable);
        } else {
            return oPalomaresRepository.findAll(oPageable);
        }
    }

    public Long count() {
        return oPalomaresRepository.count();
    }

    // ---
    public Long publicar(Long id) {
        if (!oSessionService.isSessionActive()) {
            throw new UnauthorizedException("No active session");
        }
        PalomaresEntity existingTarea = oPalomaresRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarea not found"));
        existingTarea.setPublicado(true);
        existingTarea.setFechaModificacion(LocalDateTime.now());
        oPalomaresRepository.save(existingTarea);
        return existingTarea.getId();
    }

    public Long despublicar(Long id) {
        if (!oSessionService.isSessionActive()) {
            throw new UnauthorizedException("No active session");
        }
        PalomaresEntity existingTarea = oPalomaresRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarea not found"));
        existingTarea.setPublicado(false);
        existingTarea.setFechaModificacion(LocalDateTime.now());
        oPalomaresRepository.save(existingTarea);
        return existingTarea.getId();
    }

    public Long empty() {
        if (!oSessionService.isSessionActive()) {
            throw new UnauthorizedException("No active session");
        }
        Long total = oPalomaresRepository.count();
        oPalomaresRepository.deleteAll();
        return total;
    }

}


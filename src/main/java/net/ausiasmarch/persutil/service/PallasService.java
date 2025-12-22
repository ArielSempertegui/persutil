package net.ausiasmarch.persutil.service;


import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.ausiasmarch.persutil.entity.PallasEntity;
import net.ausiasmarch.persutil.repository.PallasRepository;

@Service
public class PallasService {

    @Autowired
    AleatorioService oAleatorioService;

    @Autowired
    PallasRepository oPallasRepository;

    private final String[] arrTitulos = {
        "Comprar pan", "Estudiar Angular", "Revisar Java", "Ir al gimnasio", 
        "Llamar a mamá", "Cita dentista", "Proyecto final", "Ver serie"
    };
    
    private final String[] arrContenidos = {
        "Tengo que hacerlo antes del viernes.", 
        "Es muy importante para la nota final.", 
        "No olvidar llevar la documentación.", 
        "Recordar comprar leche también.", 
        "Repasar el código del servicio."
    };

    
    public Long rellenaBlog(Long numPosts) {
        for(int i=0; i<numPosts;i++){
            PallasEntity oPallasEntity = new PallasEntity();
            oPallasEntity.setTitulo(arrTitulos[oAleatorioService.GenerarNumeroAleatorioEnteroEnRango(0, arrTitulos.length - 1)]);
            oPallasEntity.setContenido(arrContenidos[oAleatorioService.GenerarNumeroAleatorioEnteroEnRango(0, arrContenidos.length - 1)]);
            oPallasEntity.setFechaCreacion(LocalDateTime.now());
            oPallasRepository.save(oPallasEntity);
        }
        return numPosts;
    }

    //Crud
    public PallasEntity get(Long id) {
        return oPallasRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Nota no encontrada con id: " + id));
    }

    public Long create(PallasEntity pallasEntity) {
        pallasEntity.setFechaCreacion(LocalDateTime.now());
        pallasEntity.setFechaModificacion(null);
        PallasEntity savedEntity = oPallasRepository.save(pallasEntity);
        return savedEntity.getId();
    }

    public Long delete(Long id) {
        oPallasRepository.deleteById(id);
        return id;
    }
    
    public Page<PallasEntity> getPage(Pageable oPageable) {
        return oPallasRepository.findAll(oPageable);
    }
     

    public Long update(PallasEntity pallasEntity) {
        PallasEntity existingEntity = oPallasRepository.findById(pallasEntity.getId())
                .orElseThrow(() -> new RuntimeException("Nota no encontrada con id: " + pallasEntity.getId()));
        
        existingEntity.setTitulo(pallasEntity.getTitulo());
        existingEntity.setContenido(pallasEntity.getContenido());
        existingEntity.setFechaCreacion(pallasEntity.getFechaCreacion());
        existingEntity.setFechaModificacion(LocalDateTime.now());
        existingEntity.setPublicado(pallasEntity.isPublicado());
        PallasEntity updatedEntity = oPallasRepository.save(existingEntity);
        return updatedEntity.getId();
    }

public Page<PallasEntity> getPage(Pageable pageable, String filter) {
    if (filter == null || filter.isBlank()) {
        return oPallasRepository.findAll(pageable);
    } else {
        return oPallasRepository.findByTituloContainingIgnoreCaseOrContenidoContainingIgnoreCase(
                filter, filter, pageable
            );
    }
}




} 
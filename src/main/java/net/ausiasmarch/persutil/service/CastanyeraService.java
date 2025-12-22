package net.ausiasmarch.persutil.service;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.ausiasmarch.persutil.entity.CastanyeraEntity;
import net.ausiasmarch.persutil.exception.ResourceNotFoundException;
import net.ausiasmarch.persutil.exception.UnauthorizedException;
import net.ausiasmarch.persutil.repository.CastanyeraRepository;

@Service
public class CastanyeraService {

    @Autowired
    CastanyeraRepository oCastanyeraRepository;

    @Autowired
    AleatorioService oAleatorioService;

    @Autowired
    SessionService oSessionService;

    ArrayList<String> alFrases = new ArrayList<>();

// Función que proporciona títulos
private ArrayList<String> obtenerTitulos() {
    ArrayList<String> titulos = new ArrayList<>();
    titulos.add("Viaje a paris");
    titulos.add("Escapada a Madrid");
    titulos.add("Concierto Taylor");
    titulos.add("Fallas");
    titulos.add("Festival Mad Cool");
    titulos.add("Viaje Italia");
    titulos.add("Concierto Morat");
    titulos.add("Concierto Blackppink");
    titulos.add("Visita Museo Reina Sofia");
    titulos.add("Viaje a Asturias");
    titulos.add("Escapada a Murcia");
    return titulos;
}

// Función que proporciona contenidos
private ArrayList<String> obtenerContenidos() {
    ArrayList<String> contenidos = new ArrayList<>();
    contenidos.add("Hoy caminé por las orillas del Sena sintiéndome parte de una postal; la Torre Eiffel brilló justo cuando levanté la mirada, como si hubiese estado esperándome.");
    contenidos.add("Me perdí entre las calles de Malasaña, tomando café sin prisa y disfrutando del bullicio que solo Madrid sabe regalar un sábado cualquiera.");
    contenidos.add("Anoche canté hasta quedarme sin voz; Taylor hizo que cada canción se sintiera como una confesión compartida entre miles de desconocidos.");
    contenidos.add("El olor a pólvora lo llenaba todo y, entre mascletás y monumentos ardiendo, sentí que Valencia latía con un ritmo propio e imposible de olvidar.");
    contenidos.add("Salté entre luces, guitarras y polvo; el Mad Cool fue un torbellino de música que parecía no querer terminar nunca.");
    contenidos.add("Hoy probé un gelato frente a un callejón empedrado y entendí que Italia no se visita, se saborea, Aunque también visite sitios emblemáticos.");
    contenidos.add("Morat consiguió que cantaramos cada letra como si contaran nuestras propias historias, y por un momento todos fuimos parte del mismo recuerdo.");
    contenidos.add("El escenario explotó en rosa y energía; Blackpink hizo temblar el estadio con cada coreografía perfecta. Best GG");
    contenidos.add("Me quedé un rato largo frente al Guernica, pero como no estudio artes, tampoco entendia demasiado.");
    contenidos.add("El verde aquí parece más verde que en ningún otro sitio; entre montañas y mar, sentí que el tiempo se hacía amable.");
    contenidos.add("Caminé por sus plazas soleadas con un paparajote aún caliente en la mano, disfrutando la calma inesperada de la tarde.");
    return contenidos;
}

public Long rellenaCastanyera(Long numPosts) {
    // Obtener listas de títulos y contenidos
    ArrayList<String> titulos = obtenerTitulos();
    ArrayList<String> contenidos = obtenerContenidos();
    
    for (long j = 0; j < numPosts; j++) {
        // crea entity Castanyera y la rellena con datos aleatorios
        CastanyeraEntity oCastanyeraEntity = new CastanyeraEntity();
        
        // Generar UN SOLO índice aleatorio para título y contenido
        int indiceAleatorio = oAleatorioService.GenerarNumeroAleatorioEnteroEnRango(0, Math.min(titulos.size(), contenidos.size()) - 1);
        
        // Usar el mismo índice para título y contenido
        oCastanyeraEntity.setTitulo(titulos.get(indiceAleatorio));
        oCastanyeraEntity.setContenido(contenidos.get(indiceAleatorio));
        
        // extraer 5 palabras aleatorias del contenido para las etiquetas
        String contenidoParaEtiquetas = contenidos.get(indiceAleatorio);
        String[] palabras = contenidoParaEtiquetas.split(" ");
        
        // eliminar signos de puntuación de las palabras
        for (int i = 0; i < palabras.length; i++) {
            palabras[i] = palabras[i].replace(".", "").replace(",", "").replace(";", "").replace(":", "").replace("!", "").replace("?", "");
        }
        
        // convertir todas las palabras a minúsculas
        for (int i = 0; i < palabras.length; i++) {
            palabras[i] = palabras[i].toLowerCase();
        }
        
        // seleccionar palabras de más de 4 letras
        ArrayList<String> alPalabrasFiltradas = new ArrayList<>();
        for (String palabra : palabras) {
            if (palabra.length() > 4 && !alPalabrasFiltradas.contains(palabra)) {
                alPalabrasFiltradas.add(palabra);
            }
        }
        
        palabras = alPalabrasFiltradas.toArray(new String[0]);
        String etiquetas = "";
        
        // Generar hasta 5 etiquetas únicas
        int numEtiquetas = Math.min(5, palabras.length);
        for (int i = 0; i < numEtiquetas; i++) {
            String palabra = palabras[oAleatorioService.GenerarNumeroAleatorioEnteroEnRango(0, palabras.length - 1)];
            if (!etiquetas.contains(palabra)) {
                etiquetas += palabra + ", ";
            }
        }
        
        // eliminar la última coma y espacio
        if (etiquetas.endsWith(", ")) {
            etiquetas = etiquetas.substring(0, etiquetas.length() - 2);
        }
        oCastanyeraEntity.setEtiquetas(etiquetas);
        
        // establecer fecha de creación y modificación
        oCastanyeraEntity.setFechaCreacion(LocalDateTime.now());
        // evitar insertar NULL en la columna fecha_modificacion (DB la marca NOT NULL)
        oCastanyeraEntity.setFechaModificacion(oCastanyeraEntity.getFechaCreacion());
        
        // establecer visibilidad ALEATORIA (público o privado)
        boolean esPublico = oAleatorioService.GenerarNumeroAleatorioEnteroEnRango(0, 1) == 1;
        oCastanyeraEntity.setPublico(esPublico);
        
        // guardar entity en base de datos
        oCastanyeraRepository.save(oCastanyeraEntity);
    }
    return oCastanyeraRepository.count();
}

    // ----------------------------CRUD---------------------------------
    public CastanyeraEntity get(Long id) {
        if (oSessionService.isSessionActive()) {
                 return oCastanyeraRepository.findById(id).orElseThrow(() -> new RuntimeException("Journal not found"));
            } else{
                CastanyeraEntity castanyeraEntity = oCastanyeraRepository.findByIdAndPublicoTrue(id);
                if (castanyeraEntity == null) {
                    throw new ResourceNotFoundException("Post not found or not published");
                }
                return castanyeraEntity;
            }
    }

    public Long create(CastanyeraEntity castanyeraEntity) {
    castanyeraEntity.setFechaCreacion(LocalDateTime.now());
    // mantener fecha_modificacion igual a la de creación para evitar NULL en BD
    castanyeraEntity.setFechaModificacion(castanyeraEntity.getFechaCreacion());
        // si no se especifica, por defecto público
        if (castanyeraEntity.getPublico() == null) {
            castanyeraEntity.setPublico(true);
        }
        oCastanyeraRepository.save(castanyeraEntity);
        return castanyeraEntity.getId();
    }

    public Long update(CastanyeraEntity castanyeraEntity) {
        CastanyeraEntity existingCastanyera = oCastanyeraRepository.findById(castanyeraEntity.getId())
                .orElseThrow(() -> new RuntimeException("Journal not found"));
        existingCastanyera.setTitulo(castanyeraEntity.getTitulo());
        existingCastanyera.setContenido(castanyeraEntity.getContenido());
        existingCastanyera.setEtiquetas(castanyeraEntity.getEtiquetas());
        // actualizar visibilidad si se proporciona
        if (castanyeraEntity.getPublico() != null) {
            existingCastanyera.setPublico(castanyeraEntity.getPublico());
        }
        existingCastanyera.setFechaModificacion(LocalDateTime.now());
        oCastanyeraRepository.save(existingCastanyera);
        return existingCastanyera.getId();
    }

    public Long delete(Long id) {
        oCastanyeraRepository.deleteById(id);
        return id;
    }

    public Page<CastanyeraEntity> getPage(Pageable oPageable) {
       // si no hay session activa, solo devolver los publicados
        if (!oSessionService.isSessionActive()) {
            return oCastanyeraRepository.findByPublicoTrue(oPageable);
        } else {
            return oCastanyeraRepository.findAll(oPageable);
        }
    }

    public Long count() {
        return oCastanyeraRepository.count();
    }

    // ---
    public Long publicar(Long id) {
        if (!oSessionService.isSessionActive()) {
            throw new UnauthorizedException("No active session");
        }
        CastanyeraEntity existingCastanyera = oCastanyeraRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        existingCastanyera.setPublico(true);
        existingCastanyera.setFechaModificacion(LocalDateTime.now());
        oCastanyeraRepository.save(existingCastanyera);
        return existingCastanyera.getId();
    }

    public Long despublicar(Long id) {
        if (!oSessionService.isSessionActive()) {
            throw new UnauthorizedException("No active session");
        }
        CastanyeraEntity existingCastanyera = oCastanyeraRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        existingCastanyera.setPublico(false);
        existingCastanyera.setFechaModificacion(LocalDateTime.now());
        oCastanyeraRepository.save(existingCastanyera);
        return existingCastanyera.getId();
    }



}

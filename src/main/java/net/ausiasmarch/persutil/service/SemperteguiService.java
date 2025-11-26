package net.ausiasmarch.persutil.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.ausiasmarch.persutil.entity.SemperteguiEntity;
import net.ausiasmarch.persutil.repository.SemperteguiRepository;

@Service
public class SemperteguiService {
    
    @Autowired
    SemperteguiRepository semperteguiRepository;


    Object[][] peliculasSimuladas = {
        {"La Naranja Mecánica", "Ciencia Ficción, Sátira, Drama", "Stanley Kubrick", 8, 1971},
        {"Pulp Fiction", "Crimen, Comedia negra, Suspense", "Quentin Tarantino", 9, 1994},
        {"The Matrix", "Ciencia Ficción, Acción", "Lana y Lilly Wachowski", 8, 1999},
        {"The Dark Knight", "Acción, Crimen, Drama", "Christopher Nolan", 9, 2008},
        {"Inception", "Ciencia Ficción, Acción", "Christopher Nolan", 8, 2010},
        {"Interstellar", "Ciencia Ficción, Aventura, Drama", "Christopher Nolan", 7, 2014},
        {"Parasite", "Drama, Suspense", "Bong Joon-ho", 9, 2019},
        {"Oppenheimer", "Biografía, Drama", "Christopher Nolan", 9, 2023},
        {"Barbie", "Comedia Fantástica", "Greta Gerwing", 8, 2023},
        {"Dune: Part Two", "Ciencia Ficción, Aventura", "Denis Villeneuve", 9, 2024}
    };

    public Long rellenaPeliculas(){

        for (Object[] pelicula : peliculasSimuladas) {
            SemperteguiEntity semperteguiEntity = new SemperteguiEntity();
            semperteguiEntity.setNombre((String) pelicula[0]);
            semperteguiEntity.setGenero((String) pelicula[1]);
            semperteguiEntity.setDirector((String) pelicula[2]);
            semperteguiEntity.setPuntuacion((int) pelicula[3]);
            semperteguiEntity.setAnyo((int) pelicula[4]);
            semperteguiRepository.save(semperteguiEntity);
        }
        return semperteguiRepository.count();
    }

    // ----------------------------CRUD---------------------------------

    public SemperteguiEntity get(Long id){
        return semperteguiRepository.findById(id).orElseThrow(() -> new RuntimeException("Movie not found"));
    }

    public Long create(SemperteguiEntity semperteguiEntity){
        semperteguiEntity.setFechaCreacion(LocalDateTime.now());
        semperteguiEntity.setFechaModificacion(null);
        semperteguiRepository.save(semperteguiEntity);
        return semperteguiEntity.getId();
    }

    public Long update(SemperteguiEntity semperteguiEntity){
        SemperteguiEntity existingPelicula = semperteguiRepository.findById(semperteguiEntity.getId())                .orElseThrow(() -> new RuntimeException("Blog not found"));
        existingPelicula.setNombre(semperteguiEntity.getNombre());
        existingPelicula.setGenero(semperteguiEntity.getGenero());
        existingPelicula.setDirector(semperteguiEntity.getDirector());
        existingPelicula.setPuntuacion(semperteguiEntity.getPuntuacion());
        existingPelicula.setAnyo(semperteguiEntity.getAnyo());
        existingPelicula.setFechaModificacion(LocalDateTime.now());
        semperteguiRepository.save(existingPelicula);
        return existingPelicula.getId();
    }

    public Long delete(Long id){
        semperteguiRepository.deleteById(id);
        return id;
    }
}

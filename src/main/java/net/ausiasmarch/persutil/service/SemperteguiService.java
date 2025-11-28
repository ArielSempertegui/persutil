package net.ausiasmarch.persutil.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.ausiasmarch.persutil.entity.SemperteguiEntity;
import net.ausiasmarch.persutil.repository.SemperteguiRepository;

@Service
public class SemperteguiService {
    
    @Autowired
    SemperteguiRepository semperteguiRepository;


    Object[][] peliculasSimuladas = {
        {"La Naranja Mecánica", "Ciencia Ficción, Sátira, Drama", "Stanley Kubrick", 87, 1971},
        {"Pulp Fiction", "Crimen, Comedia negra, Suspense", "Quentin Tarantino", 92, 1994},
        {"The Matrix", "Ciencia Ficción, Acción", "Lana y Lilly Wachowski", 88, 1999},
        {"The Dark Knight", "Acción, Crimen, Drama", "Christopher Nolan", 94, 2008},
        {"Inception", "Ciencia Ficción, Acción", "Christopher Nolan", 87, 2010},
        {"Interstellar", "Ciencia Ficción, Aventura, Drama", "Christopher Nolan", 73, 2014},
        {"Parasite", "Drama, Suspense", "Bong Joon-ho", 99, 2019},
        {"Oppenheimer", "Biografía, Drama", "Christopher Nolan", 93, 2023},
        {"Barbie", "Comedia Fantástica", "Greta Gerwing", 88, 2023},
        {"Dune: Part Two", "Ciencia Ficción, Aventura", "Denis Villeneuve", 93, 2024}
    };

    public Long rellenaPeliculas(){

        for (Object[] pelicula : peliculasSimuladas) {
            SemperteguiEntity semperteguiEntity = new SemperteguiEntity();
            semperteguiEntity.setNombre((String) pelicula[0]);
            semperteguiEntity.setGenero((String) pelicula[1]);
            semperteguiEntity.setDirector((String) pelicula[2]);
            semperteguiEntity.setPuntuacion((int) pelicula[3]);
            semperteguiEntity.setAnyo((int) pelicula[4]);
            semperteguiEntity.setFechaCreacion(LocalDateTime.now());
            semperteguiEntity.setFechaModificacion(null);
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

    public Page<SemperteguiEntity> getPage(Pageable pageable){
        return semperteguiRepository.findAll(pageable);
    }

    public Long count(){
        return semperteguiRepository.count();
    }
}

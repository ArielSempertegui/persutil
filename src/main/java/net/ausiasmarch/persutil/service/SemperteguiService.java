package net.ausiasmarch.persutil.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.ausiasmarch.persutil.entity.SemperteguiEntity;
import net.ausiasmarch.persutil.exception.ResourceNotFoundException;
import net.ausiasmarch.persutil.exception.UnauthorizedException;
import net.ausiasmarch.persutil.repository.SemperteguiRepository;

@Service
public class SemperteguiService {
    
    @Autowired
    SemperteguiRepository semperteguiRepository;

    @Autowired
    AleatorioService aleatorioService;

    @Autowired
    SessionService sessionService;

    // Object[][] peliculasSimuladas = {
    //     {"La Naranja Mecánica", "Ciencia Ficción, Sátira, Drama", "Stanley Kubrick", 87, 1971},
    //     {"Pulp Fiction", "Crimen, Comedia negra, Suspense", "Quentin Tarantino", 92, 1994},
    //     {"The Matrix", "Ciencia Ficción, Acción", "Larry y Andy Wachowski", 88, 1999},
    //     {"The Dark Knight", "Acción, Crimen, Drama", "Christopher Nolan", 94, 2008},
    //     {"Inception", "Ciencia Ficción, Acción", "Christopher Nolan", 87, 2010},
    //     {"Interstellar", "Ciencia Ficción, Aventura, Drama", "Christopher Nolan", 73, 2014},
    //     {"Parasite", "Drama, Suspense", "Bong Joon-ho", 99, 2019},
    //     {"Oppenheimer", "Biografía, Drama", "Christopher Nolan", 93, 2023},
    //     {"Barbie", "Comedia Fantástica", "Greta Gerwing", 88, 2023},
    //     {"Dune: Part Two", "Ciencia Ficción, Aventura", "Denis Villeneuve", 93, 2024}
    // };

    private static final String[] LOREM = {"Lorem", "Ipsum", "Dolor", "Sit", "Amet", "Consectetur", "Adipiscing", "Elit", "Sed"};
    private static final String[] GENEROS = {"Acción", "Drama", "Comedia", "Terror", "Ciencia Ficción", "Documental", "Crimen", "Suspense", "Thriller"};
    private static final String[] NOMBRES = {"Stanley", "Quentin", "Christopher", "Greta", "Denis", "Steven", "Pedro", "Antonio", "Elena", "Sara"};
    private static final String[] APELLIDOS = {"Kubrick", "Tarantino", "Nolan", "Gerwing", "Villeneuve", "Spielberg", "Almodóvar", "Bayona", "Smith"}; 

    public Long rellenaPeliculas(Long numPosts){

        if (!sessionService.isSessionActive()) {
            throw new UnauthorizedException("No active session");
        }

        for (long j = 0; j < numPosts; j++) {
            SemperteguiEntity semperteguiEntity = new SemperteguiEntity();
            semperteguiEntity.setTitulo(LOREM[aleatorioService.GenerarNumeroAleatorioEnteroEnRango(0, LOREM.length - 1)] + " " + LOREM[aleatorioService.GenerarNumeroAleatorioEnteroEnRango(0, LOREM.length - 1)]);
            String generos = "";
            for (int i = 0; i < 3; i++) {
                String genero = GENEROS[aleatorioService.GenerarNumeroAleatorioEnteroEnRango(0, GENEROS.length - 1)];
                if (!generos.contains(genero)) {
                    generos += genero + ", ";
                }
            }
            // eliminar la última coma y espacio
            if (generos.endsWith(", ")) {
                generos = generos.substring(0, generos.length() - 2);
            }
            semperteguiEntity.setGenero(generos);
            semperteguiEntity.setDirector(NOMBRES[aleatorioService.GenerarNumeroAleatorioEnteroEnRango(0, NOMBRES.length - 1)] + " " + APELLIDOS[aleatorioService.GenerarNumeroAleatorioEnteroEnRango(0, APELLIDOS.length - 1)]);
            semperteguiEntity.setPuntuacion(aleatorioService.GenerarNumeroAleatorioEnteroEnRango(0, 100));
            semperteguiEntity.setAnyo(aleatorioService.GenerarNumeroAleatorioEnteroEnRango(1901, 2155));
            semperteguiEntity.setFechaCreacion(LocalDateTime.now());
            semperteguiEntity.setFechaModificacion(null);
            // poner la flag de publicado aleatoriamente
            // semperteguiEntity.setPublicado(aleatorioService.GenerarNumeroAleatorioEnteroEnRango(0, 1) == 1);
            semperteguiRepository.save(semperteguiEntity);
        }

        return semperteguiRepository.count();
    }

    // ----------------------------CRUD---------------------------------

    public SemperteguiEntity get(Long id){
        if(!sessionService.isSessionActive()) {
            return semperteguiRepository.findByIdAndPublicadoTrue(id).orElseThrow(() -> new ResourceNotFoundException("Movie not found or not published"));
        } else {
            return semperteguiRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Movie not found"));
        }
    }

    public Long create(SemperteguiEntity semperteguiEntity){
        
        if (!sessionService.isSessionActive()) {
            throw new UnauthorizedException("No active session");
        }

        semperteguiEntity.setFechaCreacion(LocalDateTime.now());
        semperteguiEntity.setFechaModificacion(null);
        semperteguiRepository.save(semperteguiEntity);
        return semperteguiEntity.getId();
    }

    public Long update(SemperteguiEntity semperteguiEntity){

        if (!sessionService.isSessionActive()) {
            throw new UnauthorizedException("No active session");
        }

        SemperteguiEntity existingPelicula = semperteguiRepository.findById(semperteguiEntity.getId()).orElseThrow(() -> new ResourceNotFoundException("Movie not found"));
        existingPelicula.setTitulo(semperteguiEntity.getTitulo());
        existingPelicula.setGenero(semperteguiEntity.getGenero());
        existingPelicula.setDirector(semperteguiEntity.getDirector());
        existingPelicula.setPuntuacion(semperteguiEntity.getPuntuacion());
        existingPelicula.setAnyo(semperteguiEntity.getAnyo());
        existingPelicula.setFechaModificacion(LocalDateTime.now());
        semperteguiRepository.save(existingPelicula);
        return existingPelicula.getId();
    }

    public Long delete(Long id){

        if (!sessionService.isSessionActive()) {
            throw new UnauthorizedException("No active session");
        }

        semperteguiRepository.deleteById(id);
        return id;
    }

    public Page<SemperteguiEntity> getPage(Pageable pageable){
        // si no hay session activa, solo devolver los publicados
        if(!sessionService.isSessionActive()) {
            return semperteguiRepository.findByPublicadoTrue(pageable);
        } else {
            return semperteguiRepository.findAll(pageable);
        }
    }

    public Long count(){
        return semperteguiRepository.count();
    }

    // --- Publicar y Despublicar
    public Long publicar(Long id) {
        if (!sessionService.isSessionActive()) {
            throw new UnauthorizedException("No active session");
        }
        SemperteguiEntity existingPelicula = semperteguiRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Movie not found"));
        existingPelicula.setPublicado(true);
        existingPelicula.setFechaModificacion(LocalDateTime.now());
        semperteguiRepository.save(existingPelicula);
        return existingPelicula.getId();
    }

    public Long despublicar(Long id) {
        if (!sessionService.isSessionActive()) {
            throw new UnauthorizedException("No active session");
        }
        SemperteguiEntity existingPelicula = semperteguiRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Movie not found"));
        existingPelicula.setPublicado(false);
        existingPelicula.setFechaModificacion(LocalDateTime.now());
        semperteguiRepository.save(existingPelicula);
        return existingPelicula.getId();
    }
}

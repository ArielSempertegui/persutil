package net.ausiasmarch.persutil.service;

import java.time.LocalDateTime;
import java.util.Random;

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

    // Lista de palabras estándar de Lorem Ipsum
    private static final String[] LOREM = {
        "lorem", "ipsum", "dolor", "sit", "amet", "consectetur", "adipiscing", "elit", 
        "sed", "do", "eiusmod", "tempor", "incididunt", "ut", "labore", "et", 
        "dolore", "magna", "aliqua", "enim", "ad", "minim", "veniam"
    };
    private static final String[] GENEROS = {"Acción", "Drama", "Comedia", "Terror", "Ciencia ficción", "Documental", "Crimen", "Suspense", "Thriller"};
    private static final String[] NOMBRES = {"Stanley", "Quentin", "Christopher", "Greta", "Denis", "Steven", "Pedro", "Antonio", "Elena", "Sara"};
    private static final String[] APELLIDOS = {"Kubrick", "Tarantino", "Nolan", "Gerwing", "Villeneuve", "Spielberg", "Almodóvar", "Bayona", "Smith"}; 

    // Generadora de frases Aleatorias
    public static String generarPalabras(int min, int max, boolean esTitulo) {
        Random random = new Random();
        // Determinar la longitud aleatoria entre min y max
        int longitud = random.nextInt((max - min) + 1) + min;
        StringBuilder frase = new StringBuilder();
        
        for (int i = 0; i < longitud; i++) {
            // Seleccionar una palabra aleatoria del arreglo
            String palabra = LOREM[random.nextInt(LOREM.length)];
            // Capitalizar la primera letra de las palabras segun si es Titulo o no
            if(esTitulo) {
                palabra = palabra.substring(0, 1).toUpperCase() + palabra.substring(1);
            } else if (i == 0) {
                palabra = palabra.substring(0, 1).toUpperCase() + palabra.substring(1);
            }
            frase.append(palabra);
            // Añadir espacio si no es la última palabra, o punto final si lo es
            if (i < longitud - 1) {
                frase.append(" ");
            } else if(!esTitulo){
                frase.append(".");
            }
        }
        return frase.toString();
    }

    public Long rellenaPeliculas(Long numPosts){

        if (!sessionService.isSessionActive()) {
            throw new UnauthorizedException("No active session");
        }

        for (long j = 0; j < numPosts; j++) {
            SemperteguiEntity semperteguiEntity = new SemperteguiEntity();
            semperteguiEntity.setTitulo(generarPalabras(1, 4, true));
            String generos = "";
            int numGeneros = aleatorioService.GenerarNumeroAleatorioEnteroEnRango(1, 3);
            for (int i = 0; i < numGeneros; i++) {
                String genero = GENEROS[aleatorioService.GenerarNumeroAleatorioEnteroEnRango(0, GENEROS.length - 1)];
                if (!generos.contains(genero)) {
                    generos += genero + ", ";
                }
            }
            // eliminar la última coma y espacio
            if (generos.endsWith(", ")) {
                generos = generos.substring(0, generos.length() - 2);
            }
            semperteguiEntity.setGeneros(generos);
            semperteguiEntity.setSinopsis(generarPalabras(5, 30, false));
            semperteguiEntity.setDirector(NOMBRES[aleatorioService.GenerarNumeroAleatorioEnteroEnRango(0, NOMBRES.length - 1)] + " " + APELLIDOS[aleatorioService.GenerarNumeroAleatorioEnteroEnRango(0, APELLIDOS.length - 1)]);
            semperteguiEntity.setPuntuacion(aleatorioService.GenerarNumeroAleatorioEnteroEnRango(0, 100));
            semperteguiEntity.setAnyo(aleatorioService.GenerarNumeroAleatorioEnteroEnRango(1901, LocalDateTime.now().getYear()));
            semperteguiEntity.setPublicado(aleatorioService.GenerarNumeroAleatorioEnteroEnRango(0, 1) == 1);
            semperteguiEntity.setFechaCreacion(LocalDateTime.now());
            semperteguiEntity.setFechaModificacion(null);
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
        existingPelicula.setGeneros(semperteguiEntity.getGeneros());
        existingPelicula.setSinopsis(semperteguiEntity.getSinopsis());
        existingPelicula.setDirector(semperteguiEntity.getDirector());
        existingPelicula.setPuntuacion(semperteguiEntity.getPuntuacion());
        existingPelicula.setAnyo(semperteguiEntity.getAnyo());
        existingPelicula.setPublicado(semperteguiEntity.getPublicado());
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

    // vaciar tabla película (solo administrador)
    public Long empty() {
        if (!sessionService.isSessionActive()) {
            throw new UnauthorizedException("No active session");
        }
        Long total = semperteguiRepository.count();
        semperteguiRepository.deleteAll();
        return total;
    }
}

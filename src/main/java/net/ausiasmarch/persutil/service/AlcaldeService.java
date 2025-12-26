package net.ausiasmarch.persutil.service;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import net.ausiasmarch.persutil.entity.AlcaldeEntity;
import net.ausiasmarch.persutil.repository.AlcaldeRepository;

@Service
public class AlcaldeService {

    @Autowired
    private AlcaldeRepository oAlcaldeRepository;

    private final Random random = new Random();

    private final List<String> titulos = List.of(
            "Sombras en la biblioteca",
            "Cartas desde el norte",
            "El eco de las montanas",
            "Invierno en la ciudad",
            "La ultima constelacion",
            "Historias junto al fuego",
            "Cronicas de medianoche",
            "El jardin de tinta",
            "Mapas para volver",
            "Diario de un lector persistente");

    private final List<String> autores = List.of(
            "Lucia Serrano",
            "Martin Hidalgo",
            "Celia Navarro",
            "Irene Sousa",
            "Alex Pardo",
            "Miguel Campillo",
            "Lola Esteban",
            "Sara Pellicer",
            "Nora Vidal",
            "Pablo Arcas");

    private final List<String> generos = List.of(
            "Fantasia",
            "Ciencia ficcion",
            "No ficcion",
            "Misterio",
            "Novela historica",
            "Ensayo",
            "Biografia",
            "Desarrollo personal");

    private final List<String> sensaciones = List.of(
            "Una lectura que me acompanara durante anos.",
            "Repleto de imagenes potentes y personajes memorables.",
            "Perfecto para las noches de lluvia y manta.",
            "La estructura sorprende y mantiene la tension hasta el final.",
            "Ideal para recomendar a quien busque historias que inspiran.",
            "Su ritmo pausado invita a leer con calma y subrayador.",
            "Me reconcilio con mi rutina lectora.",
            "Didactico, amable y tremendamente honesto.");

    public Page<AlcaldeEntity> getPage(Pageable pageable, Boolean soloPublicados) {
        if (soloPublicados != null && soloPublicados) {
            return oAlcaldeRepository.findByPublicadoTrue(pageable);
        }
        return oAlcaldeRepository.findAll(pageable);
    }

    public Page<AlcaldeEntity> getPageFiltered(Pageable pageable, String genero, int minValoracion) {
        return oAlcaldeRepository.findByPublicadoTrueFiltered(genero, minValoracion, pageable);
    }

    public AlcaldeEntity get(Long id) {
        return oAlcaldeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));
    }

    public Long create(AlcaldeEntity oEntidad) {
        prepararParaCrear(oEntidad);
        oAlcaldeRepository.save(oEntidad);
        return oEntidad.getId();
    }

    public Long update(AlcaldeEntity oEntidad) {
        AlcaldeEntity existente = get(oEntidad.getId());
        existente.setTitulo(oEntidad.getTitulo());
        existente.setAutor(oEntidad.getAutor());
        existente.setGenero(oEntidad.getGenero());
        existente.setReseña(oEntidad.getReseña());
        existente.setValoracion(oEntidad.getValoracion());
        existente.setPublicado(oEntidad.getPublicado());
        existente.setDestacado(oEntidad.getDestacado());
        oAlcaldeRepository.save(existente);
        return existente.getId();
    }

    public Long delete(Long id) {
        oAlcaldeRepository.deleteById(id);
        return id;
    }

    public Long count() {
        return oAlcaldeRepository.count();
    }

    public List<String> getGeneros() {
        return oAlcaldeRepository.findDistinctGeneros();
    }

    public List<AlcaldeEntity> getSeleccion(int limit) {
        Page<AlcaldeEntity> destacados = oAlcaldeRepository
                .findByPublicadoTrueAndDestacadoTrue(
                    PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "valoracion")));
        if (!destacados.isEmpty()) {
            return destacados.getContent();
        }
        return oAlcaldeRepository.findByPublicadoTrue(
                PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "valoracion"))).getContent();
    }

    public Long rellena(Long cantidad) {
        for (long i = 0; i < cantidad; i++) {
            AlcaldeEntity libro = new AlcaldeEntity();
            libro.setTitulo(obtenerAleatorio(titulos) + " #" + oAlcaldeRepository.count());
            libro.setAutor(obtenerAleatorio(autores));
            libro.setGenero(obtenerAleatorio(generos));
            libro.setReseña(generarResena());
            libro.setValoracion(random.nextInt(5) + 1);
            libro.setPublicado(random.nextBoolean());
            libro.setDestacado(random.nextInt(100) < 35);
            prepararParaCrear(libro);
            oAlcaldeRepository.save(libro);
        }
        return oAlcaldeRepository.count();
    }

    public Long empty() {
        Long count = oAlcaldeRepository.count();
        oAlcaldeRepository.deleteAll();
        return count;
    }

    public Long publicar(Long id) {
        AlcaldeEntity entidad = get(id);
        entidad.setPublicado(true);
        oAlcaldeRepository.save(entidad);
        return id;
    }

    public Long despublicar(Long id) {
        AlcaldeEntity entidad = get(id);
        entidad.setPublicado(false);
        oAlcaldeRepository.save(entidad);
        return id;
    }

    private void prepararParaCrear(AlcaldeEntity entidad) {
        entidad.setPublicado(entidad.getPublicado() != null ? entidad.getPublicado() : Boolean.TRUE);
        entidad.setDestacado(entidad.getDestacado() != null ? entidad.getDestacado() : Boolean.FALSE);
        entidad.setValoracion(entidad.getValoracion() != null ? entidad.getValoracion() : 3);
    }

    private String generarResena() {
        StringBuilder resena = new StringBuilder();
        int frases = random.nextInt(3) + 2;
        for (int i = 0; i < frases; i++) {
            resena.append(obtenerAleatorio(sensaciones)).append(' ');
        }
        return resena.toString().trim();
    }

    private String obtenerAleatorio(List<String> datos) {
        return datos.get(random.nextInt(datos.size()));
    }
}

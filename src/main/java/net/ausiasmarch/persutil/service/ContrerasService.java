package net.ausiasmarch.persutil.service;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.ausiasmarch.persutil.entity.ContrerasEntity;
import net.ausiasmarch.persutil.repository.ContrerasRepository;

@Service    //Es necesatrio poner el @service para que lo detecten

public class ContrerasService {

    //---------------------------GENERACION DE DATOS---------------------------

    // Generar datos
    public Long rellenaContreras(Long numPosts) {

        //Listas titulos y Contenidos
        ArrayList<String> titulos = obtenerTitulos();
        ArrayList<String> contenidos = obtenerContenidos();

        java.util.Random random = new java.util.Random();

        for (long j = 0; j < numPosts; j++) {
            ContrerasEntity fake = new ContrerasEntity();

            int indiceAleatorio = random.nextInt(Math.min(titulos.size(), contenidos.size()));
            fake.setTitulo(titulos.get(indiceAleatorio));
            fake.setContenido(contenidos.get(indiceAleatorio));
            // Generar etiquetas a partir del contenido
            String contenidoParaEtiquetas = contenidos.get(indiceAleatorio);
            String[] palabras = contenidoParaEtiquetas.split(" ");
            // limpiar signos de puntuación y pasar a minúsculas
            for (int i = 0; i < palabras.length; i++) {
                palabras[i] = palabras[i].replace(".", "").replace(",", "").replace(";", "").replace(":", "").replace("!", "").replace("?", "");
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
            // Genenrar hasta 5 etiquetas aleatorias
            int numEtiquetas = Math.min(5, palabras.length);
            for (int i = 0; i < numEtiquetas; i++) {
                String palabra = palabras[random.nextInt(palabras.length)];
                if (!etiquetas.contains(palabra)) {
                    etiquetas += palabra + ", ";
                }
            }
            // eliminar la última coma y espacio
            if (etiquetas.endsWith(", ")) {
                etiquetas = etiquetas.substring(0, etiquetas.length() - 2);
            }
            // Asignar etiquetas generadas al post fake
            fake.setEtiquetas(etiquetas);
            fake.setFechaCreacion(LocalDateTime.now());
            fake.setFechaModificacion(fake.getFechaCreacion());
            fake.setPublico(random.nextBoolean());
            oContrerasRepository.save(fake);
        }
        return oContrerasRepository.count();
    }

    private ArrayList<String> obtenerTitulos() {
        ArrayList<String> titulos = new ArrayList<>();
        titulos.add("los melocotones");
        titulos.add("smash or pass");
        titulos.add("La revolución digital");
        titulos.add("El origen del universo");
        titulos.add("La historia de la imprenta");
        titulos.add("Grandes civilizaciones antiguas");
        titulos.add("El arte del ajedrez");
        titulos.add("La música clásica europea");
        titulos.add("El impacto de internet");
        titulos.add("Cultura femboy en la sociedad");
        return titulos;
    }

    private ArrayList<String> obtenerContenidos() {
        ArrayList<String> contenidos = new ArrayList<>();
        contenidos.add("¿Por qué los melocotones son tan populares en los memes? Descúbrelo aquí.");
        contenidos.add("¿Smash or pass? El debate definitivo sobre personajes de videojuegos.");
        contenidos.add("La revolución digital ha transformado la comunicación, la economía y la vida cotidiana en todo el mundo.");
        contenidos.add("El origen del universo es estudiado por la cosmología, destacando teorías como el Big Bang.");
        contenidos.add("La invención de la imprenta por Gutenberg permitió la difusión masiva del conocimiento en Europa.");
        contenidos.add("Civilizaciones como Egipto, Mesopotamia y Roma sentaron las bases de la cultura y la tecnología modernas.");
        contenidos.add("El ajedrez es considerado un arte y una ciencia, practicado por millones de personas en todo el mundo.");
        contenidos.add("Compositores como Mozart, Beethoven y Bach marcaron la historia de la música clásica europea.");
        contenidos.add("Internet ha cambiado la forma en que accedemos a la información y nos conectamos globalmente.");
        contenidos.add("La cultura femboy ha ganado visibilidad en la sociedad contemporánea, promoviendo la expresión de género diversa y la aceptación.");
        return contenidos;
    }

    @Autowired
    ContrerasRepository oContrerasRepository;

    // ----------------------------CRUD---------------------------------
    public ContrerasEntity get(Long id) {
        return oContrerasRepository.findById(id).orElseThrow(() -> new RuntimeException("Contreras not found"));
    }

    public Long create(ContrerasEntity contrerasEntity) {
        contrerasEntity.setFechaCreacion(LocalDateTime.now());
        contrerasEntity.setFechaModificacion(null);
        oContrerasRepository.save(contrerasEntity);
        return contrerasEntity.getId();
    }

    public Long update(ContrerasEntity contrerasEntity) {
        ContrerasEntity existingContreras = oContrerasRepository.findById(contrerasEntity.getId())
                .orElseThrow(() -> new RuntimeException("Contreras not found"));
        existingContreras.setTitulo(contrerasEntity.getTitulo());
        existingContreras.setContenido(contrerasEntity.getContenido());
        existingContreras.setEtiquetas(contrerasEntity.getEtiquetas());
        existingContreras.setPublico(contrerasEntity.isPublico());
        existingContreras.setFechaModificacion(LocalDateTime.now());
        oContrerasRepository.save(existingContreras);
        return existingContreras.getId();
    }

    public Long delete(Long id) {
        oContrerasRepository.deleteById(id);
        return id;
    }

    // Borrar todos los posts
    public void deleteAll() {
        oContrerasRepository.deleteAll();
    }

    public Page<ContrerasEntity> getPage(Pageable oPageable) {
        return oContrerasRepository.findAll(oPageable);
    }

    // Filtrar por publico
    public Page<ContrerasEntity> getPageByPublico(boolean publico, Pageable oPageable) {
        return oContrerasRepository.findByPublico(publico, oPageable);
    }

    public Long count() {
        return oContrerasRepository.count();
    }

    public Long publicar(Long id) {
        ContrerasEntity entity = oContrerasRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contreras not found"));
        entity.setPublico(true);
        entity.setFechaModificacion(LocalDateTime.now());
        oContrerasRepository.save(entity);
        return entity.getId();
    }

    public Long despublicar(Long id) {
        ContrerasEntity entity = oContrerasRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contreras not found"));
        entity.setPublico(false);
        entity.setFechaModificacion(LocalDateTime.now());
        oContrerasRepository.save(entity);
        return entity.getId();
    }

}
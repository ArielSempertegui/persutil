package net.ausiasmarch.persutil.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.ausiasmarch.persutil.entity.GarciaEntity;
import net.ausiasmarch.persutil.exception.ResourceNotFoundException;
import net.ausiasmarch.persutil.exception.UnauthorizedException;
import net.ausiasmarch.persutil.repository.GarciaRepository;

@Service
public class GarciaService {

    @Autowired
    GarciaRepository GarciaRepository;

    private static final String[] TITULOS = {
            "Aprender Java",
            "Proyecto de IA",
            "Mejorar la salud",
            "Dominar Angular",
            "Escribir un libro",
            "Emprender un negocio",
            "Aprender un nuevo idioma",
            "Viajar por el mundo",
            "Optimizar finanzas",
            "Crear una app móvil"
    };

    private static final String[] OBJETIVOS = {
            "Conseguir bases sólidas en programación",
            "Desarrollar un sistema inteligente capaz de reconocer patrones",
            "Alcanzar hábitos saludables y consistentes",
            "Construir aplicaciones web modernas y eficientes",
            "Completar y publicar un libro propio",
            "Lanzar un proyecto que genere ingresos sostenibles",
            "Ser capaz de comunicarme fluidamente en otro idioma",
            "Visitar al menos 5 continentes en 3 años",
            "Ahorrar e invertir para lograr independencia financiera",
            "Diseñar y publicar una app funcional en Google Play/App Store"
    };

    private static final String[] PROGRESOS = {
            "Ya escribiste tus primeras líneas de código, sigue así",
            "Has empezado a recopilar datos para tu IA, buen inicio",
            "Comenzaste a cambiar hábitos diarios, sigue con constancia",
            "Ya configuraste tu primer proyecto en Angular, excelente",
            "Tienes el esbozo del primer capítulo, sigue creando",
            "Hiciste tu primer estudio de mercado, muy bien",
            "Aprendiste vocabulario básico, continúa practicando",
            "Planeaste tu primer viaje, la aventura comienza",
            "Optimizar tus gastos ya está en marcha, sigue adelante",
            "Tu prototipo inicial ya funciona, gran progreso"
    };

    private final Random random = new Random();

    // ----------------------------CRUD---------------------------------
    public GarciaEntity get(Long id) {
        return GarciaRepository.findById(id).orElseThrow(() -> new RuntimeException("Blog not found"));
    }

    public Long create(GarciaEntity garciaEntity) {
        garciaEntity.setFechaInicio(LocalDateTime.now());

        if (garciaEntity.getProgreso() == null || garciaEntity.getProgreso().length() < 3) {
            garciaEntity.setProgreso("Sin progreso");
        }

        if (garciaEntity.getPublico() == null) {
            garciaEntity.setPublico(false);
        }

        GarciaRepository.save(garciaEntity);
        return garciaEntity.getId();
    }

    public Long update(GarciaEntity garciaEntity) {
        GarciaEntity existingGarcia = GarciaRepository.findById(garciaEntity.getId())
                .orElseThrow(() -> new RuntimeException("Blog not found"));

        existingGarcia.setTitulo(garciaEntity.getTitulo());
        existingGarcia.setObjetivo(garciaEntity.getObjetivo());
        existingGarcia.setFechaModificacion(LocalDateTime.now());

        if (garciaEntity.getProgreso() != null && garciaEntity.getProgreso().length() >= 3) {
            existingGarcia.setProgreso(garciaEntity.getProgreso());
        } else {
            existingGarcia.setProgreso("Sin progreso");
        }

        existingGarcia.setFechaInicio(garciaEntity.getFechaInicio() != null
                ? garciaEntity.getFechaInicio()
                : existingGarcia.getFechaInicio());

        existingGarcia.setFechaFinal(garciaEntity.getFechaFinal() != null
                ? garciaEntity.getFechaFinal()
                : existingGarcia.getFechaFinal());

        if (garciaEntity.getPublico() != null) {
            existingGarcia.setPublico(garciaEntity.getPublico());
        }

        GarciaRepository.save(existingGarcia);
        return existingGarcia.getId();
    }

    public Long delete(Long id) {
        GarciaRepository.deleteById(id);
        return id;
    }

    public Page<GarciaEntity> getPage(Pageable oPageable) {
        return GarciaRepository.findAll(oPageable);
    }

    public Long count() {
        return GarciaRepository.count();
    }

    public Long createRandom(Long cantidad) {
        for (int i = 0; i < cantidad; i++) {
            int indiceAleatorio = random.nextInt(TITULOS.length);

            GarciaEntity garciaEntity = new GarciaEntity();
            garciaEntity.setTitulo(TITULOS[indiceAleatorio]);
            garciaEntity.setObjetivo(OBJETIVOS[indiceAleatorio]);
            garciaEntity.setProgreso(PROGRESOS[indiceAleatorio]);
            garciaEntity.setFechaInicio(LocalDateTime.now());
            garciaEntity.setFechaFinal(LocalDate.now().plusDays(random.nextInt(90)));
            garciaEntity.setPublico(random.nextBoolean());

            GarciaRepository.save(garciaEntity);
        }
        return cantidad;
    }

    public Page<GarciaEntity> getPagePublico(Pageable oPageable) {
        return GarciaRepository.findByPublico(true, oPageable);
    }

    public Long publicar(Long id) {
        GarciaEntity existingGarcia = GarciaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        existingGarcia.setPublico(true);
        existingGarcia.setFechaModificacion(LocalDateTime.now());
        GarciaRepository.save(existingGarcia);
        return existingGarcia.getId();
    }

    public Long despublicar(Long id) {
        GarciaEntity existingGarcia = GarciaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        existingGarcia.setPublico(false);
        existingGarcia.setFechaModificacion(LocalDateTime.now());
        GarciaRepository.save(existingGarcia);
        return existingGarcia.getId();
    }

    public Long deleteAll() {
        long count = GarciaRepository.count();
        GarciaRepository.deleteAll();
        return count;
    }
}
package net.ausiasmarch.persutil.service;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import net.ausiasmarch.persutil.entity.UskiVisitasEntity;
import net.ausiasmarch.persutil.exception.ResourceNotFoundException;
import net.ausiasmarch.persutil.exception.UnauthorizedException;
import net.ausiasmarch.persutil.repository.UskiVisitasRepository;

@Service
public class UskiVisitasService {

    @Autowired
    UskiVisitasRepository oUskiVisitasRepository;

    @Autowired
    AleatorioService oAleatorioService;

    @Autowired
    SessionService oSessionService;

    ArrayList<String> names = new ArrayList<>();
    ArrayList<String> comments = new ArrayList<>();

    // constructor para rellenar la tabla con datos aleatorios
    public UskiVisitasService() {
        names.add("Ana García");
        names.add("Luis Pérez");
        names.add("María López");
        names.add("Javier Ruiz");
        names.add("Elena Soto");
        names.add("Carlos Castro");
        names.add("Andrea Mora");
        names.add("Ricardo Vidal");
        names.add("Sofía Torres");
        names.add("Pablo Herrera");
        names.add("Laura Gil");
        names.add("Diego Núñez");
        names.add("Isabel Ramos");
        names.add("Sergio Martín");
        names.add("Marta Navarro");
        names.add("Jorge Jiménez");

        comments.add("Me encantó el sitio, muy fácil de usar.");
        comments.add(
                "La calidad del contenido es innegable y se nota la dedicación. Me gustó especialmente la forma en que se abordó el tema complejo de la optimización de bases de datos, con ejemplos claros y directos. Me gustaría ver más temas en esta sección, quizás cubriendo las últimas tendencias del sector. Todo funcionó sin errores y la experiencia de usuario fue fluida, tanto en la computadora como en el móvil. Gracias por actualizar la información tan rápido después de la última versión. Es importante mantener la relevancia. Sugiero considerar un modo oscuro para los usuarios que prefieren navegar por la noche, sería un añadido excelente a la ya buena experiencia de diseño que tienen.");
        comments.add("Volveré a revisar esta página más adelante, me ayudó mucho.");
        comments.add("Funciona rápido y no tuve problemas en navegar.");
        comments.add(
                "Buen trabajo con la estructura general del sitio, resulta muy intuitiva y no tuve que buscar mucho para encontrar la sección de ayuda. Me ayudó a resolver una duda técnica específica que tenía. Solo un pequeño detalle: la tipografía podría ser un poco más grande en los párrafos extensos.");
        comments.add("Encontré justo lo que necesitaba, gracias.");
        comments.add("Sería genial añadir más ejemplos prácticos.");
        comments.add("La carga es un poco lenta, pero el contenido lo compensa.");
        comments.add("Muy útil para mis tareas, lo recomiendo.");
        comments.add("Buen trabajo con la estructura, resulta intuitiva.");
        comments.add("Me ayudó a resolver una duda que tenía desde hace días.");
        comments.add("Faltan algunos detalles, pero en general está bien.");
        comments.add("Gracias por actualizar la información tan rápido.");
        comments.add(
                "Volveré a revisar esta página más adelante porque el contenido me pareció de gran valor. Me ayudó a resolver una duda importante que tenía desde hace varios días. ¡Recomiendo totalmente este sitio!");
        comments.add(
                "Funciona rápido y no tuve problemas en navegar por las diferentes secciones. La estructura del sitio es muy intuitiva, lo que facilita encontrar la información necesaria sin perder tiempo. Buen trabajo con la categorización de los temas. Sugiero, si es posible, añadir una sección de preguntas frecuentes para las dudas más comunes, aunque el contenido actual ya es muy completo. ¡Gracias por el esfuerzo!");
        comments.add(
                "El sitio en general es fenomenal. La velocidad de carga es excelente en la mayoría de las páginas, lo que mejora considerablemente la experiencia del usuario. Encontré justo lo que necesitaba y la claridad con la que se presenta la información es admirable. Me gustaría destacar el esfuerzo en mantener el contenido actualizado; esto es crucial para la relevancia del tema. Sería genial añadir más ejemplos prácticos y estudios de caso reales que ilustren los conceptos teóricos, esto fortalecería la comprensión para usuarios menos experimentados. Además, si pudieran incorporar un foro de discusión donde los usuarios puedan interactuar y compartir sus propias experiencias, creo que la comunidad crecería. La usabilidad en dispositivos móviles es otro punto a favor; pude navegar sin problemas desde mi teléfono. Aunque mencionaron que la carga de una sección específica era lenta, noté que ese problema se solucionó rápidamente, lo cual demuestra un compromiso con la calidad.");
    }

    public UskiVisitasEntity get(@NonNull Long id) {
        return oUskiVisitasRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("The record not found."));
    }

    public Long create(UskiVisitasEntity visitasEntity) {
        visitasEntity.setFechaCreacion(LocalDateTime.now());
        visitasEntity.setFechaModificacion(null);
        visitasEntity.setEstaPublicado(false);
        oUskiVisitasRepository.save(visitasEntity);
        return visitasEntity.getId();
    }

    public Long update(UskiVisitasEntity visitasEntity) {
        if (!oSessionService.isSessionActive())
            throw new UnauthorizedException("No active session");

        UskiVisitasEntity existingRegistro = oUskiVisitasRepository.findById(visitasEntity.getId())
                .orElseThrow(() -> new ResourceNotFoundException("The record not found."));
        existingRegistro.setNombre(visitasEntity.getNombre());
        existingRegistro.setComentario(visitasEntity.getComentario());
        existingRegistro.setEstaPublicado(visitasEntity.isEstaPublicado());    
        existingRegistro.setFechaModificacion(LocalDateTime.now());
        oUskiVisitasRepository.save(existingRegistro);
        return existingRegistro.getId();
    }

    public Long publish(UskiVisitasEntity visitasEntity) {
        if (!oSessionService.isSessionActive())
            throw new UnauthorizedException("No active session");

        UskiVisitasEntity existingRegistro = oUskiVisitasRepository.findById(visitasEntity.getId())
                .orElseThrow(() -> new ResourceNotFoundException("The record not found."));

        boolean nuevoEstado = !existingRegistro.isEstaPublicado();
        existingRegistro.setEstaPublicado(nuevoEstado);
        existingRegistro.setFechaModificacion(LocalDateTime.now());
        oUskiVisitasRepository.save(existingRegistro);
        return existingRegistro.getId();
    }

    public Long delete(@NonNull Long id) {
        if (!oSessionService.isSessionActive())
            throw new UnauthorizedException("No active session");

        oUskiVisitasRepository.deleteById(id);
        return id;
    }

    public Long deleteAll() {
        if (!oSessionService.isSessionActive())
            throw new UnauthorizedException("No active session");
        Long total = oUskiVisitasRepository.count();
        oUskiVisitasRepository.deleteAll();
        return total;
    }

    public Page<UskiVisitasEntity> getPublicPage(Pageable pageable) {
        return oUskiVisitasRepository.findByEstaPublicadoTrue(pageable);
    }

    public Page<UskiVisitasEntity> getAdminPage(@NonNull Pageable pageable) {
        if (!oSessionService.isSessionActive())
            throw new UnauthorizedException("No active session");
        return oUskiVisitasRepository.findAll(pageable);
    }

    public Long rellenaBlog(Long numPosts) {
        if (!oSessionService.isSessionActive())
            throw new UnauthorizedException("No active session");

        for (long j = 0; j < numPosts; j++) {

            UskiVisitasEntity visitaEntity = new UskiVisitasEntity();

            visitaEntity.setNombre(
                    names.get(oAleatorioService.GenerarNumeroAleatorioEnteroEnRango(0, comments.size() - 1)));
            visitaEntity.setComentario(
                    comments.get(oAleatorioService.GenerarNumeroAleatorioEnteroEnRango(0, comments.size() - 1)));
            visitaEntity.setFechaCreacion(LocalDateTime.now());
            visitaEntity.setFechaModificacion(null);

            oUskiVisitasRepository.save(visitaEntity);
        }

        return oUskiVisitasRepository.count();
    }
}

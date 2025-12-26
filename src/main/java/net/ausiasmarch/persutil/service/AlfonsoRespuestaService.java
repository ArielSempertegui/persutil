package net.ausiasmarch.persutil.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.ausiasmarch.persutil.entity.AlfonsoRespuestaEntity;
import net.ausiasmarch.persutil.exception.ResourceNotFoundException;
import net.ausiasmarch.persutil.exception.UnauthorizedException;
import net.ausiasmarch.persutil.repository.AlfonsoRespuestaRepository;

@Service
public class AlfonsoRespuestaService {

    private static final List<String> AUTOR_FAKE = List.of(
            "Ana", "Bea", "Carlos", "Diego", "Elena", "Fatima", "Gonzalo", "Hugo", "Irene", "Julia",
            "Lucia", "Marcos", "Nora", "Oscar", "Patricia", "Quim", "Raul", "Sara", "Teresa", "Ulises");

    private static final List<String> RESPUESTA_FAKE = List.of(
            "El formulario es sencillo de usar y la pregunta es clara.",
            "Me gustaria ver un resumen de respuestas al final.",
            "Todo correcto, gracias por dejar opinar de forma rapida.",
            "Seria util poder editar la respuesta despues de enviarla.",
            "Me ha resultado facil responder desde el movil.",
            "La encuesta deberia indicar para que se usaran los datos.",
            "Me gusta que solo sea una pregunta, no quita tiempo.",
            "Echo en falta ejemplos de respuestas para inspirar.",
            "El diseno es limpio y sin distracciones.",
            "Gracias por escuchar a la comunidad.",
            "Agregaria opcion para adjuntar una captura.",
            "Prefiero encuestas cortas como esta, sin registros previos.",
            "La pregunta es demasiado abierta, quiza algun contexto.",
            "Todo funciona rapido, no he tenido problemas.",
            "Ojala se pueda ver el resultado total mas adelante.",
            "Buen trabajo, seguiria respondiendo mas preguntas asi.",
            "El boton de enviar deberia destacar mas.",
            "Seria genial confirmar que la respuesta se guardo.",
            "Me preocupa la privacidad, falta un aviso breve.",
            "Me ha gustado poder escribir con libertad.");

    @Autowired
    private AlfonsoRespuestaRepository oAlfonsoRespuestaRepository;

    @Autowired
    private AleatorioService oAleatorioService;

    @Autowired
    private SessionService oSessionService;

    public Long rellenaRespuestas(Long cantidad) {
        if (!oSessionService.isSessionActive()) {
            throw new UnauthorizedException("No active session");
        }
        for (long i = 0; i < cantidad; i++) {
            AlfonsoRespuestaEntity entity = new AlfonsoRespuestaEntity();
            entity.setAutor(AUTOR_FAKE.get(oAleatorioService.GenerarNumeroAleatorioEnteroEnRango(0, AUTOR_FAKE.size() - 1)));
            entity.setContenido(buildRespuestaFake());
            entity.setPublicado(oAleatorioService.GenerarNumeroAleatorioEnteroEnRango(0, 1) == 1);
            LocalDateTime creacion = LocalDateTime.now().minusDays(oAleatorioService.GenerarNumeroAleatorioEnteroEnRango(0, 30));
            entity.setFechaCreacion(creacion);
            entity.setFechaModificacion(null);
            oAlfonsoRespuestaRepository.save(entity);
        }
        return oAlfonsoRespuestaRepository.count();
    }

    private String buildRespuestaFake() {
        int frases = oAleatorioService.GenerarNumeroAleatorioEnteroEnRango(1, 3);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < frases; i++) {
            if (builder.length() > 0) {
                builder.append(" ");
            }
            builder.append(RESPUESTA_FAKE.get(oAleatorioService.GenerarNumeroAleatorioEnteroEnRango(0, RESPUESTA_FAKE.size() - 1)));
        }
        return builder.toString();
    }

    public AlfonsoRespuestaEntity get(Long id) {
        if (oSessionService.isSessionActive()) {
            return oAlfonsoRespuestaRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Respuesta no encontrada"));
        } else {
            AlfonsoRespuestaEntity entity = oAlfonsoRespuestaRepository.findByIdAndPublicadoTrue(id);
            if (entity == null) {
                throw new ResourceNotFoundException("Respuesta no encontrada o no publicada");
            }
            return entity;
        }
    }

    public Long create(AlfonsoRespuestaEntity entity) {
        if (!oSessionService.isSessionActive()) {
            throw new UnauthorizedException("No active session");
        }
        if (entity.getPublicado() == null) {
            entity.setPublicado(Boolean.TRUE);
        }
        entity.setFechaCreacion(LocalDateTime.now());
        entity.setFechaModificacion(null);
        oAlfonsoRespuestaRepository.save(entity);
        return entity.getId();
    }

    public Long update(AlfonsoRespuestaEntity entity) {
        if (!oSessionService.isSessionActive()) {
            throw new UnauthorizedException("No active session");
        }
        AlfonsoRespuestaEntity existing = oAlfonsoRespuestaRepository.findById(entity.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Respuesta no encontrada"));
        existing.setAutor(entity.getAutor());
        existing.setContenido(entity.getContenido());
        existing.setPublicado(entity.getPublicado());
        existing.setFechaModificacion(LocalDateTime.now());
        oAlfonsoRespuestaRepository.save(existing);
        return existing.getId();
    }

    public Long delete(Long id) {
        if (!oSessionService.isSessionActive()) {
            throw new UnauthorizedException("No active session");
        }
        oAlfonsoRespuestaRepository.deleteById(id);
        return id;
    }

    public Page<AlfonsoRespuestaEntity> getPage(String filter, Pageable pageable) {
        boolean publishedOnly = !oSessionService.isSessionActive();
        return oAlfonsoRespuestaRepository.search(filter, publishedOnly, pageable);
    }

    public Long count() {
        return oAlfonsoRespuestaRepository.count();
    }

    public Long countVisible() {
        if (oSessionService.isSessionActive()) {
            return oAlfonsoRespuestaRepository.count();
        } else {
            return oAlfonsoRespuestaRepository.countByPublicadoTrue();
        }
    }

    public Long publicar(Long id) {
        if (!oSessionService.isSessionActive()) {
            throw new UnauthorizedException("No active session");
        }
        AlfonsoRespuestaEntity existing = oAlfonsoRespuestaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Respuesta no encontrada"));
        existing.setPublicado(true);
        existing.setFechaModificacion(LocalDateTime.now());
        oAlfonsoRespuestaRepository.save(existing);
        return existing.getId();
    }

    public Long despublicar(Long id) {
        if (!oSessionService.isSessionActive()) {
            throw new UnauthorizedException("No active session");
        }
        AlfonsoRespuestaEntity existing = oAlfonsoRespuestaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Respuesta no encontrada"));
        existing.setPublicado(false);
        existing.setFechaModificacion(LocalDateTime.now());
        oAlfonsoRespuestaRepository.save(existing);
        return existing.getId();
    }

    public Long empty() {
        if (!oSessionService.isSessionActive()) {
            throw new UnauthorizedException("No active session");
        }
        Long total = oAlfonsoRespuestaRepository.count();
        oAlfonsoRespuestaRepository.deleteAll();
        return total;
    }
}

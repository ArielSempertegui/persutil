package net.ausiasmarch.persutil.service;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.ausiasmarch.persutil.entity.ReynaEntity;
import net.ausiasmarch.persutil.exception.ResourceNotFoundException;
import net.ausiasmarch.persutil.exception.UnauthorizedException;
import net.ausiasmarch.persutil.repository.ReynaRepository;

@Service
public class ReynaService {
    @Autowired
    ReynaRepository oReynaRepository;

    @Autowired
    SessionService oSessionService;

    private static final String[] FRASES = {
            "La vida es aquello que te va sucediendo mientras te empeñas en hacer otros planes",
            "El único modo de hacer un gran trabajo es amar lo que haces",
            "La imaginación es más importante que el conocimiento",
            "No cuentes los días, haz que los días cuenten",
            "La mejor venganza es un éxito masivo",
            "El éxito es la suma de pequeños esfuerzos repetidos día tras día",
            "La única forma de hacer un gran trabajo es amar lo que haces",
            "No es la montaña lo que conquistamos, sino a nosotros mismos",
            "El fracaso es la oportunidad de empezar de nuevo de forma más inteligente",
            "La creatividad es la inteligencia divirtiéndose"
    };

    private static final String[] AUTORES = {
            "John Lennon",
            "Steve Jobs",
            "Albert Einstein",
            "Muhammad Ali",
            "Frank Sinatra",
            "Robert Collier",
            "Steve Jobs",
            "Edmund Hillary",
            "Henry Ford",
            "Albert Einstein"
    };

    private final Random random = new Random();

    public ReynaEntity get(Long id) {
        if (oSessionService.isSessionActive()) {
            return oReynaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        } else {
            ReynaEntity oReynaEntity = oReynaRepository.findByIdAndEsPublicaTrue(id);
            if (oReynaEntity == null) {
                throw new ResourceNotFoundException("Post not found or not published");
            }
            return oReynaEntity;
        }

    }

    public Long create(ReynaEntity oReynaEntity) {
        if (!oSessionService.isSessionActive()) {
            throw new UnauthorizedException("No active session");
        }
        oReynaEntity.setFechaCreacion(LocalDateTime.now());
        oReynaEntity.setFechaModificacion(null);
        oReynaRepository.save(oReynaEntity);
        return oReynaEntity.getId();
    }

    public Long update(ReynaEntity oReynaEntity) {
        if (!oSessionService.isSessionActive()) {
            throw new UnauthorizedException("No active session");
        }
        ReynaEntity existingReyna = oReynaRepository.findById(oReynaEntity.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        existingReyna.setFrase(oReynaEntity.getFrase());
        existingReyna.setAutor(oReynaEntity.getAutor());
        existingReyna.setEsPublica(oReynaEntity.isEsPublica());
        existingReyna.setFechaModificacion(LocalDateTime.now());
        oReynaRepository.save(existingReyna);
        return existingReyna.getId();
    }

    public Long delete(Long id) {
        if (!oSessionService.isSessionActive()) {
            throw new UnauthorizedException("No active session");
        }
        oReynaRepository.deleteById(id);
        return id;
    }

    public Page<ReynaEntity> getPage(Pageable oPageable) {
        if (!oSessionService.isSessionActive()) {
            return oReynaRepository.findByEsPublicaTrue(oPageable);
        } else {
            return oReynaRepository.findAll(oPageable);
        }
    }

    public Long count() {
        return oReynaRepository.count();
    }

    public Long createRandom(Long cantidad) {
        // Solo se permite si hay sesión activa
        if (!oSessionService.isSessionActive()) {
            throw new UnauthorizedException("No active session");
        }

        for (int i = 0; i < cantidad; i++) {
            int indiceAleatorio = random.nextInt(FRASES.length);

            ReynaEntity nuevaReyna = new ReynaEntity();
            nuevaReyna.setFrase(FRASES[indiceAleatorio]);
            nuevaReyna.setAutor(AUTORES[indiceAleatorio]);
            nuevaReyna.setEsPublica(true);
            nuevaReyna.setFechaCreacion(LocalDateTime.now());
            nuevaReyna.setFechaModificacion(null);

            oReynaRepository.save(nuevaReyna);
        }
        return cantidad;
    }

    // publicar
    public Long publicar(Long id) {
        if (!oSessionService.isSessionActive()) {
            throw new UnauthorizedException("No active session");
        }
        ReynaEntity existingReyna = oReynaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        existingReyna.setEsPublica(true);
        existingReyna.setFechaModificacion(LocalDateTime.now());
        oReynaRepository.save(existingReyna);
        return existingReyna.getId();
    }

     public Long despublicar(Long id) {
        if (!oSessionService.isSessionActive()) {
            throw new UnauthorizedException("No active session");
        }
        ReynaEntity existingReyna = oReynaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        existingReyna.setEsPublica(false);
        existingReyna.setFechaModificacion(LocalDateTime.now());
        oReynaRepository.save(existingReyna);
        return existingReyna.getId();
    }

    // vaciar tabla blog (solo administrador)
    public Long empty() {
        if (!oSessionService.isSessionActive()) {
            throw new UnauthorizedException("No active session");
        }
        Long total = oReynaRepository.count();
        oReynaRepository.deleteAll();
        return total;
    }
}
    
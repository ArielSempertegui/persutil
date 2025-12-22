package net.ausiasmarch.persutil.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.ausiasmarch.persutil.entity.FernandezIdeaEntity;
import net.ausiasmarch.persutil.exception.ResourceNotFoundException;
import net.ausiasmarch.persutil.exception.UnauthorizedException;
import net.ausiasmarch.persutil.repository.FernandezIdeaRepository;

@Service
public class FernandezIdeaService {
    // bulk creation de ideas fake
    public Long bulkCreate(Long amount) {
        if (!oSessionService.isSessionActive()) {
            throw new UnauthorizedException("No active session");
        }
        String[] titulos = {"Idea brillante", "Mejora urgente", "Bug misterioso", "Nueva funcionalidad", "Optimización"};
        String[] comentarios = {"Comentario de prueba", "Descripción extensa", "Texto aleatorio", "Pendiente de revisión", "Importante"};
        net.ausiasmarch.persutil.entity.CategoriaEnum[] categorias = net.ausiasmarch.persutil.entity.CategoriaEnum.values();
        for (long i = 0; i < amount; i++) {
            FernandezIdeaEntity idea = new FernandezIdeaEntity();
            idea.setTitulo(titulos[(int)(Math.random()*titulos.length)] + " " + i);
            idea.setComentario(comentarios[(int)(Math.random()*comentarios.length)] + " " + i);
            idea.setCategoria(categorias[(int)(Math.random()*categorias.length)]);
            idea.setPublico(Math.random() > 0.5);
            // fechaCreacion se asigna automáticamente con @PrePersist
            oIdeaRepository.save(idea);
        }
        return oIdeaRepository.count();
    }

    @Autowired
    FernandezIdeaRepository oIdeaRepository;

    @Autowired
    SessionService oSessionService;

    // ----------------------------CRUD---------------------------------
    
    public FernandezIdeaEntity get(Long id) {
        // Si no hay sesión, solo devolver elementos públicos
        if (!oSessionService.isSessionActive()) {
            FernandezIdeaEntity idea = oIdeaRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Idea not found"));
            if (Boolean.TRUE.equals(idea.getPublico())) {
                return idea;
            }
            throw new ResourceNotFoundException("Idea not found");
        }
        return oIdeaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Idea not found"));
    }

    public Long create(FernandezIdeaEntity ideaEntity) {
        if (!oSessionService.isSessionActive()) {
            throw new UnauthorizedException("No active session");
        }
        // fechaCreacion se asigna automáticamente con @PrePersist
        if (ideaEntity.getPublico() == null) {
            ideaEntity.setPublico(true);
        }
        oIdeaRepository.save(ideaEntity);
        return ideaEntity.getId();
    }

    public Long update(FernandezIdeaEntity ideaEntity) {
                if (!oSessionService.isSessionActive()) {
                        throw new UnauthorizedException("No active session");
                }
                FernandezIdeaEntity existingIdea = oIdeaRepository.findById(ideaEntity.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Idea not found"));
        existingIdea.setTitulo(ideaEntity.getTitulo());
        existingIdea.setComentario(ideaEntity.getComentario());
        existingIdea.setCategoria(ideaEntity.getCategoria());
        existingIdea.setPublico(ideaEntity.getPublico());
        // fechaModificacion se asigna automáticamente con @PreUpdate
        oIdeaRepository.save(existingIdea);
        return existingIdea.getId();
    }

    public Long delete(Long id) {
        if (!oSessionService.isSessionActive()) {
            throw new UnauthorizedException("No active session");
        }
        oIdeaRepository.deleteById(id);
        return id;
    }

    public Page<FernandezIdeaEntity> getPage(Pageable oPageable) {
        // Mantener compatibilidad: si no hay sesión, solo públicos
        if (!oSessionService.isSessionActive()) {
            return oIdeaRepository.findByPublico(true, oPageable);
        }
        return oIdeaRepository.findAll(oPageable);
    }

    // paginacion filtrada por 'publico' (filtra en BD antes de paginar)
    public org.springframework.data.domain.Page<FernandezIdeaEntity> getPageFiltered(int page, int size, Boolean publico,
            String filter, String sort, String direction) {
        org.springframework.data.domain.Pageable pageable = org.springframework.data.domain.PageRequest.of(
                page,
                size,
                org.springframework.data.domain.Sort.by(org.springframework.data.domain.Sort.Direction.fromString(direction), sort));

        // Si no hay sesión, forzar público=true y permitir filtro de texto
        if (!oSessionService.isSessionActive()) {
            return oIdeaRepository.findFiltered(true, filter, pageable);
        }

        return oIdeaRepository.findFiltered(publico, filter, pageable);
    }

    // contar según filtro publico (si publico == null devuelve count total)
    public long countFiltered(Boolean publico, String filter) {
        // Si no hay sesión, contar solo públicos (aunque pidan otra cosa)
        if (!oSessionService.isSessionActive()) {
            return oIdeaRepository.countFiltered(true, filter);
        }
        return oIdeaRepository.countFiltered(publico, filter);
    }

    public long countTotal() {
        // total real (sin filtro) solo para admin
        if (!oSessionService.isSessionActive()) {
            throw new UnauthorizedException("No active session");
        }
        return oIdeaRepository.count();
    }

    public Long publicar(Long id) {
        if (!oSessionService.isSessionActive()) {
            throw new UnauthorizedException("No active session");
        }
        FernandezIdeaEntity existing = oIdeaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Idea not found"));
        existing.setPublico(true);
        // fechaModificacion se asigna automáticamente con @PreUpdate
        oIdeaRepository.save(existing);
        return existing.getId();
    }

    public Long despublicar(Long id) {
        if (!oSessionService.isSessionActive()) {
            throw new UnauthorizedException("No active session");
        }
        FernandezIdeaEntity existing = oIdeaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Idea not found"));
        existing.setPublico(false);
        // fechaModificacion se asigna automáticamente con @PreUpdate
        oIdeaRepository.save(existing);
        return existing.getId();
    }

    public Long count() {
        return oIdeaRepository.count();
    }

    public Long empty() {
        if (!oSessionService.isSessionActive()) {
            throw new UnauthorizedException("No active session");
        }
        long count = oIdeaRepository.count();
        oIdeaRepository.deleteAll();
        return count;
    }

}

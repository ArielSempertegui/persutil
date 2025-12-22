package net.ausiasmarch.persutil.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import net.ausiasmarch.persutil.entity.PallasEntity;

public interface PallasRepository extends JpaRepository<PallasEntity, Long> {
  Page<PallasEntity> findByTituloContainingIgnoreCaseOrContenidoContainingIgnoreCase(
        String titulo,
        String contenido,
        Pageable pageable
    );
    
}
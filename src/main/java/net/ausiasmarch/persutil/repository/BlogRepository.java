package net.ausiasmarch.persutil.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import net.ausiasmarch.persutil.entity.BlogEntity;

public interface BlogRepository extends JpaRepository<BlogEntity, Long> {

    
    Page<BlogEntity> findByPublicadoTrue(Pageable oPageable);

    Page<BlogEntity> findByPublicadoFalse(Pageable oPageable);

}

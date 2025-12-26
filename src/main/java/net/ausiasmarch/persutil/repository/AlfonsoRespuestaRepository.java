package net.ausiasmarch.persutil.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.ausiasmarch.persutil.entity.AlfonsoRespuestaEntity;

public interface AlfonsoRespuestaRepository extends JpaRepository<AlfonsoRespuestaEntity, Long> {

    Page<AlfonsoRespuestaEntity> findByPublicadoTrue(Pageable oPageable);

    Page<AlfonsoRespuestaEntity> findByPublicadoFalse(Pageable oPageable);

    AlfonsoRespuestaEntity findByIdAndPublicadoTrue(Long id);

    Long countByPublicadoTrue();

    @Query("SELECT r FROM AlfonsoRespuestaEntity r WHERE " +
            "(:publishedOnly = false OR r.publicado = true) AND " +
            "(:filter IS NULL OR lower(r.autor) LIKE lower(concat('%', :filter, '%')) " +
            " OR lower(r.contenido) LIKE lower(concat('%', :filter, '%'))) ")
    Page<AlfonsoRespuestaEntity> search(@Param("filter") String filter, @Param("publishedOnly") boolean publishedOnly,
            Pageable pageable);
}

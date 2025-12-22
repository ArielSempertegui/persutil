package net.ausiasmarch.persutil.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.ausiasmarch.persutil.entity.FernandezIdeaEntity;

public interface FernandezIdeaRepository extends JpaRepository<FernandezIdeaEntity, Long> {
	org.springframework.data.domain.Page<FernandezIdeaEntity> findByPublico(boolean publico,
			org.springframework.data.domain.Pageable pageable);

	long countByPublico(boolean publico);

	@Query("""
			SELECT i
			FROM FernandezIdeaEntity i
			WHERE (:publico IS NULL OR i.publico = :publico)
			  AND (
				:filter IS NULL OR :filter = '' OR
				LOWER(i.titulo) LIKE LOWER(CONCAT('%', :filter, '%')) OR
				LOWER(i.comentario) LIKE LOWER(CONCAT('%', :filter, '%'))
			  )
			""")
	org.springframework.data.domain.Page<FernandezIdeaEntity> findFiltered(
			@Param("publico") Boolean publico,
			@Param("filter") String filter,
			org.springframework.data.domain.Pageable pageable);

	@Query("""
			SELECT COUNT(i)
			FROM FernandezIdeaEntity i
			WHERE (:publico IS NULL OR i.publico = :publico)
			  AND (
				:filter IS NULL OR :filter = '' OR
				LOWER(i.titulo) LIKE LOWER(CONCAT('%', :filter, '%')) OR
				LOWER(i.comentario) LIKE LOWER(CONCAT('%', :filter, '%'))
			  )
			""")
	long countFiltered(
			@Param("publico") Boolean publico,
			@Param("filter") String filter);
}

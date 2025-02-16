package com.inacal.system.management.repository;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.time.LocalDateTime;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import com.inacal.management.model.Pagination;
import com.inacal.management.db.BaseRepository;
import com.inacal.management.model.PageResponse;
import org.springframework.stereotype.Repository;
import com.inacal.system.management.entity.FormatVersion;

@Repository
public class FormatVersionRepository implements BaseRepository<FormatVersion, String> {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public FormatVersion save(FormatVersion body) {
        return entityManager.merge(body);
    }

    @Override
    public boolean delete(List<String> ids) {
        int result = entityManager.createQuery("UPDATE FROM FormatVersion SET deletedAt = :now WHERE id IN (:id)")
                .setParameter("id", ids)
                .setParameter("now", LocalDateTime.now())
                .executeUpdate();
        return result > 0;
    }

    @Override
    public Optional<FormatVersion> findById(String id) {
        try {
            FormatVersion result = entityManager.createQuery("FROM FormatVersion WHERE id = :id AND deletedAt IS NULL", FormatVersion.class)
                    .setParameter("id", id)
                    .getSingleResult();
            return Optional.ofNullable(result);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public Optional<FormatVersion> findByTitle(String title) {
        try {
            FormatVersion result = entityManager.createQuery("FROM FormatVersion WHERE title = :title AND deletedAt IS NULL", FormatVersion.class)
                    .setParameter("title", title)
                    .getSingleResult();
            return Optional.ofNullable(result);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public List<FormatVersion> saveAll(List<FormatVersion> body) {
        List<FormatVersion> result = new ArrayList<>();
        for ( int index = 0; index < body.size(); index++ ) {
            FormatVersion saved = entityManager.merge(body.get(index));
            result.add(saved);
            if (index % 5 == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }
        return result;
    }

    @Override
    public PageResponse<FormatVersion> findAll(Pagination pagination) {
        List<FormatVersion> result = entityManager.createQuery("FROM FormatVersion WHERE deletedAt IS NULL ORDER BY createdAt ASC", FormatVersion.class)
                .setFirstResult(pagination.offset())
                .setMaxResults(pagination.getSize())
                .getResultList();
        long count = entityManager.createQuery("SELECT COUNT (id) FROM FormatVersion WHERE deletedAt IS NULL ORDER BY createdAt ASC", Long.class)
                .getSingleResult();
        return new PageResponse<>(count, result, pagination);
    }
}

package com.inacal.system.management.repository;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.time.LocalDateTime;
import jakarta.transaction.Transactional;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import com.inacal.management.model.Pagination;
import com.inacal.management.db.BaseRepository;
import com.inacal.management.model.PageResponse;
import org.springframework.stereotype.Repository;
import com.inacal.system.management.entity.InternalVersion;

@Repository
public class InternalVersionRepository implements BaseRepository<InternalVersion, String> {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public InternalVersion save(InternalVersion body) {
        return entityManager.merge(body);
    }

    @Override
    public boolean delete(List<String> ids) {
        int result = entityManager.createQuery("UPDATE FROM InternalVersion SET deletedAt = :now WHERE id IN (:id)")
                .setParameter("id", ids)
                .setParameter("now", LocalDateTime.now())
                .executeUpdate();
        return result > 0;
    }

    @Override
    public Optional<InternalVersion> findById(String id) {
        try {
            InternalVersion result = entityManager.createQuery("FROM InternalVersion WHERE id = :id AND deletedAt IS NULL", InternalVersion.class)
                    .setParameter("id", id)
                    .getSingleResult();
            return Optional.ofNullable(result);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public List<InternalVersion> saveAll(List<InternalVersion> body) {
        List<InternalVersion> result = new ArrayList<>();
        for ( int index = 0; index < body.size(); index++ ) {
            InternalVersion saved = entityManager.merge(body.get(index));
            result.add(saved);
            if (index % 5 == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }
        return result;
    }

    @Override
    public PageResponse<InternalVersion> findAll(Pagination pagination) {
        List<InternalVersion> result = entityManager.createQuery("FROM InternalVersion WHERE deletedAt IS NULL ORDER BY createdAt ASC", InternalVersion.class)
                .setFirstResult(pagination.offset())
                .setMaxResults(pagination.getSize())
                .getResultList();
        long count = entityManager.createQuery("SELECT COUNT (id) FROM InternalVersion WHERE deletedAt IS NULL ORDER BY createdAt ASC", Long.class)
                .getSingleResult();
        return new PageResponse<>(count, result, pagination);
    }
}

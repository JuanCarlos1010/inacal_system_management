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
import com.inacal.system.management.entity.RevisionRecord;

@Repository
public class RevisionRecordRepository implements BaseRepository<RevisionRecord, String> {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public RevisionRecord save(RevisionRecord body) {
        return entityManager.merge(body);
    }

    @Override
    public boolean delete(List<String> ids) {
        int result = entityManager.createQuery("UPDATE FROM RevisionRecord SET deletedAt = :now WHERE id IN (:id)")
                .setParameter("id", ids)
                .setParameter("now", LocalDateTime.now())
                .executeUpdate();
        return result > 0;
    }

    @Override
    public Optional<RevisionRecord> findById(String id) {
        try {
            RevisionRecord result = entityManager.createQuery("FROM RevisionRecord WHERE id = :id AND deletedAt IS NULL", RevisionRecord.class)
                    .setParameter("id", id)
                    .getSingleResult();
            return Optional.ofNullable(result);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public List<RevisionRecord> saveAll(List<RevisionRecord> body) {
        List<RevisionRecord> result = new ArrayList<>();
        for ( int index = 0; index < body.size(); index++ ) {
            RevisionRecord saved = entityManager.merge(body.get(index));
            result.add(saved);
            if (index % 5 == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }
        return result;
    }

    @Override
    public PageResponse<RevisionRecord> findAll(Pagination pagination) {
        List<RevisionRecord> result = entityManager.createQuery("FROM RevisionRecord WHERE deletedAt IS NULL ORDER BY createdAt ASC", RevisionRecord.class)
                .setFirstResult(pagination.offset())
                .setMaxResults(pagination.getSize())
                .getResultList();
        long count = entityManager.createQuery("SELECT COUNT (id) FROM RevisionRecord WHERE deletedAt IS NULL ORDER BY createdAt ASC", Long.class)
                .getSingleResult();
        return new PageResponse<>(count, result, pagination);
    }
}

package com.inacal.system.management.repository;

import com.inacal.management.db.BaseRepository;
import com.inacal.management.model.PageResponse;
import com.inacal.management.model.Pagination;
import com.inacal.management.time.DateTimeHelper;
import com.inacal.system.management.entity.Laboratory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class LaboratoryRepository implements BaseRepository<Laboratory, String> {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Laboratory save(Laboratory body) {
        return entityManager.merge(body);
    }

    @Override
    public boolean delete(List<String> ids) {
        int result = entityManager.createQuery("UPDATE FROM Laboratory SET deletedAt = :now WHERE id IN (:id)")
                .setParameter("id", ids)
                .setParameter("now", DateTimeHelper.now())
                .executeUpdate();
        return result > 0;
    }

    @Override
    public Optional<Laboratory> findById(String id) {
        try {
            Laboratory result = entityManager.createQuery("FROM Laboratory WHERE id = :id AND deletedAt IS NOT NULL", Laboratory.class)
                    .setParameter("id", id)
                    .getSingleResult();
            return Optional.ofNullable(result);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public List<Laboratory> saveAll(List<Laboratory> body) {
        List<Laboratory> result = new ArrayList<>();
        for ( int index = 0; index < body.size(); index++ ) {
            Laboratory saved = entityManager.merge(body.get(index));
            result.add(saved);
            if (index % 5 == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }
        return result;
    }

    @Override
    public PageResponse<Laboratory> findAll(Pagination pagination) {
        List<Laboratory> result = entityManager.createQuery("FROM Laboratory WHERE deletedAt IS NULL", Laboratory.class)
                .setFirstResult(pagination.offset())
                .setMaxResults(pagination.getSize())
                .getResultList();
        long count = entityManager.createQuery("FROM Laboratory WHERE deletedAt IS NULL", Long.class)
                .getSingleResult();
        return new PageResponse<>(count, result, pagination);
    }
}


package com.inacal.system.management.repository;

import com.inacal.management.db.BaseRepository;
import com.inacal.management.model.PageResponse;
import com.inacal.management.model.Pagination;
import com.inacal.management.time.DateTimeHelper;
import com.inacal.system.management.entity.Field;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class FieldRepository implements BaseRepository<Field, String> {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Field save(Field body) {
        return entityManager.merge(body);
    }

    @Override
    public boolean delete(List<String> ids) {
        int result = entityManager.createQuery("UPDATE FROM Field SET deletedAt = :now WHERE id IN (:id)")
                .setParameter("id", ids)
                .setParameter("now", DateTimeHelper.now())
                .executeUpdate();
        return result > 0;
    }

    @Override
    public Optional<Field> findById(String id) {
        try {
            Field result = entityManager.createQuery("FROM Field WHERE id = :id AND deletedAt IS NOT NULL", Field.class)
                    .setParameter("id", id)
                    .getSingleResult();
            return Optional.ofNullable(result);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public List<Field> saveAll(List<Field> body) {
        List<Field> result = new ArrayList<>();
        for ( int index = 0; index < body.size(); index++ ) {
            Field saved = entityManager.merge(body.get(index));
            result.add(saved);
            if (index % 5 == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }
        return result;
    }

    @Override
    public PageResponse<Field> findAll(Pagination pagination) {
        List<Field> result = entityManager.createQuery("FROM Field WHERE deletedAt IS NULL", Field.class)
                .setFirstResult(pagination.offset())
                .setMaxResults(pagination.getSize())
                .getResultList();
        long count = entityManager.createQuery("FROM Field WHERE deletedAt IS NULL", Long.class)
                .getSingleResult();
        return new PageResponse<>(count, result, pagination);
    }
}

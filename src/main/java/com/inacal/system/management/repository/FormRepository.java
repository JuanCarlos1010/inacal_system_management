package com.inacal.system.management.repository;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.time.LocalDateTime;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.persistence.NoResultException;
import com.inacal.management.model.Pagination;
import jakarta.persistence.PersistenceContext;
import com.inacal.management.db.BaseRepository;
import com.inacal.system.management.entity.Form;
import com.inacal.management.model.PageResponse;
import org.springframework.stereotype.Repository;

@Repository
public class FormRepository implements BaseRepository<Form, String> {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Form save(Form body) {
        return entityManager.merge(body);
    }

    @Override
    public boolean delete(List<String> ids) {
        int result = entityManager.createQuery("UPDATE FROM Form SET deletedAt = :now WHERE id IN (:id)")
                .setParameter("id", ids)
                .setParameter("now", LocalDateTime.now())
                .executeUpdate();
        return result > 0;
    }

    @Override
    public Optional<Form> findById(String id) {
        try {
            Form result = entityManager.createQuery("FROM Form WHERE id = :id AND deletedAt IS NULL", Form.class)
                    .setParameter("id", id)
                    .getSingleResult();
            return Optional.ofNullable(result);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public Optional<Form> findByName(String name) {
        try {
            Form result = entityManager.createQuery("FROM Form WHERE name = :name AND deletedAt IS NULL", Form.class)
                    .setParameter("name", name)
                    .getSingleResult();
            return Optional.ofNullable(result);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public List<Form> saveAll(List<Form> body) {
        List<Form> result = new ArrayList<>();
        for ( int index = 0; index < body.size(); index++ ) {
            Form saved = entityManager.merge(body.get(index));
            result.add(saved);
            if (index % 5 == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }
        return result;
    }

    @Override
    public PageResponse<Form> findAll(Pagination pagination) {
        List<Form> result = entityManager.createQuery("FROM Form WHERE deletedAt IS NULL ORDER BY createdAt ASC", Form.class)
                .setFirstResult(pagination.offset())
                .setMaxResults(pagination.getSize())
                .getResultList();
        long count = entityManager.createQuery("SELECT COUNT (id) FROM Form WHERE deletedAt IS NULL ORDER BY createdAt ASC", Long.class)
                .getSingleResult();
        return new PageResponse<>(count, result, pagination);
    }
}

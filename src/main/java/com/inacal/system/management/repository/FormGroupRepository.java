package com.inacal.system.management.repository;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import com.inacal.management.model.Pagination;
import com.inacal.management.db.BaseRepository;
import com.inacal.management.model.PageResponse;
import org.springframework.stereotype.Repository;
import com.inacal.management.time.DateTimeHelper;
import com.inacal.system.management.entity.FormGroup;

@Repository
public class FormGroupRepository implements BaseRepository<FormGroup, String> {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public FormGroup save(FormGroup body) {
        return entityManager.merge(body);
    }

    @Override
    public boolean delete(List<String> ids) {
        int result = entityManager.createQuery("UPDATE FROM FormGroup SET deletedAt = :now WHERE id IN (:id)")
                .setParameter("id", ids)
                .setParameter("now", DateTimeHelper.now())
                .executeUpdate();
        return result > 0;
    }

    @Override
    public Optional<FormGroup> findById(String id) {
        try {
            FormGroup result = entityManager.createQuery("FROM FormGroup WHERE id = :id AND deletedAt IS NOT NULL", FormGroup.class)
                    .setParameter("id", id)
                    .getSingleResult();
            return Optional.ofNullable(result);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public Optional<FormGroup> findByName(String name) {
        try {
            FormGroup result = entityManager.createQuery("FROM FormGroup WHERE name = :name AND deletedAt IS NOT NULL", FormGroup.class)
                    .setParameter("name", name)
                    .getSingleResult();
            return Optional.ofNullable(result);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public List<FormGroup> saveAll(List<FormGroup> body) {
        List<FormGroup> result = new ArrayList<>();
        for ( int index = 0; index < body.size(); index++ ) {
            FormGroup saved = entityManager.merge(body.get(index));
            result.add(saved);
            if (index % 5 == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }
        return result;
    }

    @Override
    public PageResponse<FormGroup> findAll(Pagination pagination) {
        List<FormGroup> result = entityManager.createQuery("FROM FormGroup WHERE deletedAt IS NULL", FormGroup.class)
                .setFirstResult(pagination.offset())
                .setMaxResults(pagination.getSize())
                .getResultList();
        long count = entityManager.createQuery("FROM FormGroup WHERE deletedAt IS NULL", Long.class)
                .getSingleResult();
        return new PageResponse<>(count, result, pagination);
    }
}

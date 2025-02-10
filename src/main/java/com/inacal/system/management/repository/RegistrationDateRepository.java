package com.inacal.system.management.repository;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import jakarta.transaction.Transactional;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import com.inacal.management.model.Pagination;
import com.inacal.management.db.BaseRepository;
import com.inacal.management.model.PageResponse;
import com.inacal.management.time.DateTimeHelper;
import org.springframework.stereotype.Repository;
import com.inacal.system.management.entity.RegistrationDate;

@Repository
public class RegistrationDateRepository implements BaseRepository<RegistrationDate, String> {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public RegistrationDate save(RegistrationDate body) {
        return entityManager.merge(body);
    }

    @Override
    public boolean delete(List<String> ids) {
        int result = entityManager.createQuery("UPDATE FROM RegistrationDate SET deletedAt = :now WHERE id IN (:id)")
                .setParameter("id", ids)
                .setParameter("now", DateTimeHelper.now())
                .executeUpdate();
        return result > 0;
    }

    @Override
    public Optional<RegistrationDate> findById(String id) {
        try {
            RegistrationDate result = entityManager.createQuery("FROM RegistrationDate WHERE id = :id AND deletedAt IS NOT NULL", RegistrationDate.class)
                    .setParameter("id", id)
                    .getSingleResult();
            return Optional.ofNullable(result);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public List<RegistrationDate> saveAll(List<RegistrationDate> body) {
        List<RegistrationDate> result = new ArrayList<>();
        for ( int index = 0; index < body.size(); index++ ) {
            RegistrationDate saved = entityManager.merge(body.get(index));
            result.add(saved);
            if (index % 5 == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }
        return result;
    }

    @Override
    public PageResponse<RegistrationDate> findAll(Pagination pagination) {
        List<RegistrationDate> result = entityManager.createQuery("FROM RegistrationDate WHERE deletedAt IS NULL", RegistrationDate.class)
                .setFirstResult(pagination.offset())
                .setMaxResults(pagination.getSize())
                .getResultList();
        long count = entityManager.createQuery("FROM RegistrationDate WHERE deletedAt IS NULL", Long.class)
                .getSingleResult();
        return new PageResponse<>(count, result, pagination);
    }
}

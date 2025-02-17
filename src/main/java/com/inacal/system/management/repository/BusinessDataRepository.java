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
import com.inacal.system.management.entity.BusinessData;

@Repository
public class BusinessDataRepository implements BaseRepository<BusinessData, String> {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public BusinessData save(BusinessData body) {
        return entityManager.merge(body);
    }

    @Override
    public boolean delete(List<String> ids) {
        int result = entityManager.createQuery("UPDATE FROM BusinessData SET deletedAt = :now WHERE id IN (:id)")
                .setParameter("id", ids)
                .setParameter("now", LocalDateTime.now())
                .executeUpdate();
        return result > 0;
    }

    @Override
    public Optional<BusinessData> findById(String id) {
        try {
            BusinessData result = entityManager.createQuery("FROM BusinessData WHERE id = :id AND deletedAt IS NULL", BusinessData.class)
                    .setParameter("id", id)
                    .getSingleResult();
            return Optional.ofNullable(result);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public Optional<BusinessData> findByName(String name) {
        try {
            BusinessData result = entityManager.createQuery("FROM BusinessData WHERE name = :name AND deletedAt IS NULL", BusinessData.class)
                    .setParameter("name", name)
                    .getSingleResult();
            return Optional.ofNullable(result);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public List<BusinessData> saveAll(List<BusinessData> body) {
        List<BusinessData> result = new ArrayList<>();
        for ( int index = 0; index < body.size(); index++ ) {
            BusinessData saved = entityManager.merge(body.get(index)); 
            result.add(saved);
            if (index % 5 == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }
        return result;
    }

    @Override
    public PageResponse<BusinessData> findAll(Pagination pagination) {
        List<BusinessData> result = entityManager.createQuery("FROM BusinessData WHERE deletedAt IS NULL ORDER BY createdAt ASC", BusinessData.class)
                .setFirstResult(pagination.offset())
                .setMaxResults(pagination.getSize())
                .getResultList();
        long count = entityManager.createQuery("SELECT COUNT (id) FROM BusinessData WHERE deletedAt IS NULL ORDER BY createdAt ASC", Long.class)
                .getSingleResult();
        return new PageResponse<>(count, result, pagination);
    }
}

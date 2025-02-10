package com.inacal.system.management.repository;

import com.inacal.management.db.BaseRepository;
import com.inacal.management.model.PageResponse;
import com.inacal.management.model.Pagination;
import com.inacal.management.time.DateTimeHelper;
import com.inacal.system.management.entity.ProductState;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductStateRepository implements BaseRepository<ProductState, String> {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public ProductState save(ProductState body) {
        return entityManager.merge(body);
    }

    @Override
    public boolean delete(List<String> ids) {
        int result = entityManager.createQuery("UPDATE FROM ProductState SET deletedAt = :now WHERE id IN (:id)")
                .setParameter("id", ids)
                .setParameter("now", DateTimeHelper.now())
                .executeUpdate();
        return result > 0;
    }

    @Override
    public Optional<ProductState> findById(String id) {
        try {
            ProductState result = entityManager.createQuery("FROM ProductState WHERE id = :id AND deletedAt IS NOT NULL", ProductState.class)
                    .setParameter("id", id)
                    .getSingleResult();
            return Optional.ofNullable(result);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public Optional<ProductState> findByName(String name) {
        try {
            ProductState result = entityManager.createQuery("FROM ProductState WHERE name = :name AND deletedAt IS NOT NULL", ProductState.class)
                    .setParameter("name", name)
                    .getSingleResult();
            return Optional.ofNullable(result);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public List<ProductState> saveAll(List<ProductState> body) {
        List<ProductState> result = new ArrayList<>();
        for ( int index = 0; index < body.size(); index++ ) {
            ProductState saved = entityManager.merge(body.get(index));
            result.add(saved);
            if (index % 5 == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }
        return result;
    }

    @Override
    public PageResponse<ProductState> findAll(Pagination pagination) {
        List<ProductState> result = entityManager.createQuery("FROM ProductState WHERE deletedAt IS NULL", ProductState.class)
                .setFirstResult(pagination.offset())
                .setMaxResults(pagination.getSize())
                .getResultList();
        long count = entityManager.createQuery("FROM ProductState WHERE deletedAt IS NULL", Long.class)
                .getSingleResult();
        return new PageResponse<>(count, result, pagination);
    }
}

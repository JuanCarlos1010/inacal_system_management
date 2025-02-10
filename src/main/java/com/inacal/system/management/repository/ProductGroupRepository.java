package com.inacal.system.management.repository;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import jakarta.transaction.Transactional;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import com.inacal.management.model.Pagination;
import jakarta.persistence.PersistenceContext;
import com.inacal.management.db.BaseRepository;
import com.inacal.management.model.PageResponse;
import com.inacal.management.time.DateTimeHelper;
import org.springframework.stereotype.Repository;
import com.inacal.system.management.entity.ProductGroup;

@Repository
public class ProductGroupRepository implements BaseRepository<ProductGroup, String> {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public ProductGroup save(ProductGroup body) {
        return entityManager.merge(body);
    }

    @Override
    public boolean delete(List<String> ids) {
        int result = entityManager.createQuery("UPDATE FROM ProductGroup SET deletedAt = :now WHERE id IN (:id)")
                .setParameter("id", ids)
                .setParameter("now", DateTimeHelper.now())
                .executeUpdate();
        return result > 0;
    }

    @Override
    public Optional<ProductGroup> findById(String id) {
        try {
            ProductGroup result = entityManager.createQuery("FROM ProductGroup WHERE id = :id AND deletedAt IS NOT NULL", ProductGroup.class)
                    .setParameter("id", id)
                    .getSingleResult();
            return Optional.ofNullable(result);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public Optional<ProductGroup> findByName(String name) {
        try {
            ProductGroup result = entityManager.createQuery("FROM ProductGroup WHERE name = :name AND deletedAt IS NOT NULL", ProductGroup.class)
                    .setParameter("name", name)
                    .getSingleResult();
            return Optional.ofNullable(result);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public List<ProductGroup> saveAll(List<ProductGroup> body) {
        List<ProductGroup> result = new ArrayList<>();
        for ( int index = 0; index < body.size(); index++ ) {
            ProductGroup saved = entityManager.merge(body.get(index));
            result.add(saved);
            if (index % 5 == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }
        return result;
    }

    @Override
    public PageResponse<ProductGroup> findAll(Pagination pagination) {
        List<ProductGroup> result = entityManager.createQuery("FROM ProductGroup WHERE deletedAt IS NULL", ProductGroup.class)
                .setFirstResult(pagination.offset())
                .setMaxResults(pagination.getSize())
                .getResultList();
        long count = entityManager.createQuery("FROM ProductGroup WHERE deletedAt IS NULL", Long.class)
                .getSingleResult();
        return new PageResponse<>(count, result, pagination);
    }
}

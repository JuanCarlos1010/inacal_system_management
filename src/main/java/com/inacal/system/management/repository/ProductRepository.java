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
import com.inacal.management.model.PageResponse;
import org.springframework.stereotype.Repository;
import com.inacal.system.management.entity.Product;

@Repository
public class ProductRepository implements BaseRepository<Product, String> {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Product save(Product body) {
        return entityManager.merge(body);
    }

    @Override
    public boolean delete(List<String> ids) {
        int result = entityManager.createQuery("UPDATE FROM Product SET deletedAt = :now WHERE id IN (:id)")
                .setParameter("id", ids)
                .setParameter("now", LocalDateTime.now())
                .executeUpdate();
        return result > 0;
    }

    @Override
    public Optional<Product> findById(String id) {
        try {
            Product result = entityManager.createQuery("FROM Product WHERE id = :id AND deletedAt IS NULL", Product.class)
                    .setParameter("id", id)
                    .getSingleResult();
            return Optional.ofNullable(result);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public Optional<Product> findByName(String name) {
        try {
            Product result = entityManager.createQuery("FROM Product WHERE name = :name AND deletedAt IS NULL", Product.class)
                    .setParameter("name", name)
                    .getSingleResult();
            return Optional.ofNullable(result);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public List<Product> saveAll(List<Product> body) {
        List<Product> result = new ArrayList<>();
        for ( int index = 0; index < body.size(); index++ ) {
            Product saved = entityManager.merge(body.get(index));
            result.add(saved);
            if (index % 5 == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }
        return result;
    }

    @Override
    public PageResponse<Product> findAll(Pagination pagination) {
        List<Product> result = entityManager.createQuery("FROM Product WHERE deletedAt IS NULL ORDER BY createdAt ASC", Product.class)
                .setFirstResult(pagination.offset())
                .setMaxResults(pagination.getSize())
                .getResultList();
        long count = entityManager.createQuery("SELECT COUNT (id) FROM Product WHERE deletedAt IS NULL ORDER BY createdAt ASC", Long.class)
                .getSingleResult();
        return new PageResponse<>(count, result, pagination);
    }
}

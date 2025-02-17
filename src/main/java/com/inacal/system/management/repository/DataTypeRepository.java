package com.inacal.system.management.repository;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.persistence.NoResultException;
import com.inacal.management.model.Pagination;
import jakarta.persistence.PersistenceContext;
import com.inacal.management.db.BaseRepository;
import com.inacal.management.model.PageResponse;
import org.springframework.stereotype.Repository;
import com.inacal.system.management.entity.DataType;

@Repository
public class DataTypeRepository implements BaseRepository<DataType, String> {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public DataType save(DataType body) {
        return entityManager.merge(body);
    }

    @Override
    public boolean delete(List<String> ids) {
        return false;
    }

    @Override
    public Optional<DataType> findById(String id) {
        try {
            DataType result = entityManager.createQuery("FROM DataType WHERE id = :id", DataType.class)
                    .setParameter("id", id)
                    .getSingleResult();
            return Optional.ofNullable(result);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public Optional<DataType> findByName(String name) {
        try {
            DataType result = entityManager.createQuery("FROM DataType WHERE name = :name", DataType.class)
                    .setParameter("name", name)
                    .getSingleResult();
            return Optional.ofNullable(result);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public List<DataType> saveAll(List<DataType> body) {
        List<DataType> result = new ArrayList<>();
        for ( int index = 0; index < body.size(); index++ ) {
            DataType saved = entityManager.merge(body.get(index));
            result.add(saved);
            if (index % 5 == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }
        return result;
    }

    @Override
    public PageResponse<DataType> findAll(Pagination pagination) {
        List<DataType> result = entityManager.createQuery("FROM DataType ORDER BY name ASC", DataType.class)
                .setFirstResult(pagination.offset())
                .setMaxResults(pagination.getSize())
                .getResultList();
        long count = entityManager.createQuery("SELECT COUNT (id) FROM DataType ORDER BY name ASC", Long.class)
                .getSingleResult();
        return new PageResponse<>(count, result, pagination);
    }
}

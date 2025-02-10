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
import com.inacal.system.management.entity.Area;
import com.inacal.management.time.DateTimeHelper;
import org.springframework.stereotype.Repository;

@Repository
public class AreaRepository implements BaseRepository<Area, String> {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Area save(Area body) {
        return entityManager.merge(body);
    }

    @Override
    public boolean delete(List<String> ids) {
        int result = entityManager.createQuery("UPDATE FROM Area SET deletedAt = :now WHERE id IN (:id)")
                .setParameter("id", ids)
                .setParameter("now", DateTimeHelper.now())
                .executeUpdate();
        return result > 0;
    }

    @Override
    public Optional<Area> findById(String id) {
        try {
            Area result = entityManager.createQuery("FROM Area WHERE id = :id AND deletedAt IS NOT NULL", Area.class)
                    .setParameter("id", id)
                    .getSingleResult();
            return Optional.ofNullable(result);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public Optional<Area> findByName(String name) {
        try {
            Area result = entityManager.createQuery("FROM Area WHERE name = :name AND deletedAt IS NOT NULL", Area.class)
                    .setParameter("name", name)
                    .getSingleResult();
            return Optional.ofNullable(result);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public List<Area> saveAll(List<Area> body) {
        List<Area> result = new ArrayList<>();
        for ( int index = 0; index < body.size(); index++ ) {
            Area saved = entityManager.merge(body.get(index));
            result.add(saved);
            if (index % 5 == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }
        return result;
    }

    @Override
    public PageResponse<Area> findAll(Pagination pagination) {
        List<Area> result = entityManager.createQuery("FROM Area WHERE deletedAt IS NULL", Area.class)
                .setFirstResult(pagination.offset())
                .setMaxResults(pagination.getSize())
                .getResultList();
        long count = entityManager.createQuery("FROM Area WHERE deletedAt IS NULL", Long.class)
                .getSingleResult();
        return new PageResponse<>(count, result, pagination);
    }
}

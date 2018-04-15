package com.library.app.category.repository;

import com.library.app.category.model.Category;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class CategoryRepository {

    public EntityManager entityManager;

    public Category add(final Category category) {
        entityManager.persist(category);
        return category;
    }

    public Category findById(Long id) {
        if(id == null) return null;
        return entityManager.find(Category.class, id);
    }

    public void update(Category category) {
        entityManager.merge(category);
    }

    public List<Category> findAll(String kenttä) {
        String query = "select e from Category e order by e."+kenttä;

        return entityManager.createQuery(query).getResultList();
    }

    public boolean isExist(Category category) {
        StringBuilder jcdb = new StringBuilder();
        jcdb.append("select 1 from Category e where e.name=:x");
        if( category.getId() != null) jcdb.append(" and e.id != :y");

        Query query = entityManager.createQuery(jcdb.toString());
        query.setParameter("x", category.getName());
        if( category.getId() != null) query.setParameter("y", category.getId());

        return query.setMaxResults(1).getResultList().size() > 0;
    }

    public int categoryienLukumäärä() {
        return findAll("name").size();
    }

    public boolean isExistById(Long categoryId) {
        return entityManager.createQuery("select e from Category e where id= :x")
                .setParameter("x",categoryId)
                .setMaxResults(1).getResultList().size()> 0;
    }
}

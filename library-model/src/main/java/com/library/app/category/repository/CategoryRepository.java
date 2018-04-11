package com.library.app.category.repository;

import com.library.app.category.model.Category;

import javax.persistence.EntityManager;

public class CategoryRepository {

    public EntityManager entityManager;

    public Category add(final Category category) {
        entityManager.persist(category);
        return category;
    }

    public Category findById(Long id) {
        return entityManager.find(Category.class, id);
    }
}

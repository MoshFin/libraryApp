package com.library.app.category.model.repository;

import com.library.app.category.model.Category;
import com.library.app.category.repository.CategoryRepository;
import com.library.app.commontests.utils.DBCommand;
import com.library.app.commontests.utils.DBCommandTransactionalExecutor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.List;

import static com.library.app.commontests.category.CategoryForTests.*;
import static org.junit.Assert.*;


public class CategoryRepositoryUTest {
    private EntityManagerFactory emf;
    private EntityManager em;
    CategoryRepository categoryRepository;
    private DBCommandTransactionalExecutor executor;

    @Before
    public void initTestCases() {
        emf = Persistence.createEntityManagerFactory("libraryPU");
        em = emf.createEntityManager();
        categoryRepository = new CategoryRepository();
        categoryRepository.entityManager = em;
        executor = new DBCommandTransactionalExecutor(em);
    }

    @After
    public void closeEntityManager() {
        em.close();
        emf.close();
    }

    @Test
    public void entityManagerTestaaminenJaLyödäCategory() {
        Long categorinId = executor.executeCommand(() ->
                categoryRepository.add(javaCategory()).getId()
        );

        assertNotNull(categorinId);
        Category category = categoryRepository.findById(categorinId);
        assertNotNull(category);
        assertEquals(category.getName(), javaCategory().getName());
    }

    @Test
    public void palautaNullJosIdEilöydy() {
        Category category = categoryRepository.findById(33L);
        assertNull(category);
    }

    @Test
    public void etsiCategoryId_llaNull() {
        Long catecorynId = executor.executeCommand(() -> {
            categoryRepository.findById(null);
            return null;
        });
        assertNull(catecorynId);
    }

    @Test
    public void päivitäCategoty() {
        long categoryId = executor.executeCommand(() -> categoryRepository.add(javaCategory())).getId();
        Category category = categoryRepository.findById(categoryId);
        assertEquals(javaCategory().getName(), category.getName());

        category.setName(pythonCategory().getName());
        executor.executeCommand(() -> {
            categoryRepository.update(category);
            return null;
        });
        Category päivitettyCategory = categoryRepository.findById(categoryId);
        assertEquals(pythonCategory().getName(), päivitettyCategory.getName());
    }

    @Test
    public void löydäKaikkiCategorynNimellä() {
        executor.executeCommand(() -> {
            allCategories().forEach(categoryRepository::add);
            return null;
        });

        assertEquals(4, categoryRepository.categoryienLukumäärä());
        assertEquals("Java", allCategories().get(0).getName());
        assertEquals("Python", allCategories().get(1).getName());
        assertEquals("Clojure", allCategories().get(2).getName());
        assertEquals("JavaScript", allCategories().get(3).getName());
    }

    @Test
    public void categoryNimiOnAinoa() {
        executor.executeCommand(() -> categoryRepository.add(javaCategory()));

        assertTrue(categoryRepository.isExist(javaCategory()));
        assertFalse(categoryRepository.isExist(pythonCategory()));
    }

    @Test
    public void onkoCategoryOlemassaNimellä() {
        final Category javaCategory = executor.executeCommand(() -> {
            categoryRepository.add(pythonCategory());
            return categoryRepository.add(javaCategory());
        });
        categoryRepository.categoryienLukumäärä();
        assertFalse(categoryRepository.isExist(javaCategory));

        javaCategory.setName(pythonCategory().getName());
        assertTrue(categoryRepository.isExist(pythonCategory()));

        javaCategory.setName(clojureCategory().getName());
        assertFalse(categoryRepository.isExist(clojureCategory()));

        javaCategory.setName(clojureCategory().getName());
        executor.executeCommand(() -> {
            categoryRepository.update(javaCategory);
            return null;
        });
        assertTrue(categoryRepository.isExist(clojureCategory()));
    }

    @Test
    public void onkoCategoryOlemassaId_lla() {

        Long categoryId = executor.executeCommand(() -> categoryRepository.add(pythonCategory()).getId());

        assertTrue(categoryRepository.isExistById(categoryId));
        assertFalse(categoryRepository.isExistById(226L));
    }
}

package com.library.app.category.model.repository;

import com.library.app.category.model.Category;
import com.library.app.category.repository.CategoryRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static com.library.app.commontests.category.CategoryForTests.javaCategory;


public class CategoryRepositoryUTest {
    private EntityManagerFactory emf;
    private EntityManager em;
    CategoryRepository categoryRepository = new CategoryRepository();

    @Before
    public void initTestCases() {
        emf = Persistence.createEntityManagerFactory("libraryPU");
        em = emf.createEntityManager();
        categoryRepository.entityManager = em;
    }

    @After
    public void closeEntityManager() {
        em.close();
        emf.close();
    }

    @Test
    public void entityManagerTestaaminenJaLyödäCategory() {
        Long categoristaSaattuId = null;
        try {
            em.getTransaction().begin();
            categoristaSaattuId = categoryRepository.add(javaCategory()).getId();
            assertNotNull(categoristaSaattuId);
            em.getTransaction().commit();
            em.clear();
        } catch (final Exception ex) {
            fail("This Exception should not be thrown :(");
            ex.printStackTrace();
            em.getTransaction().rollback();

        }

        Category category = categoryRepository.findById(categoristaSaattuId);
        assertNotNull(category);
        assertEquals(category.getName(), javaCategory().getName());
    }
}

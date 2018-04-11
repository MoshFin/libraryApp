package com.library.app.category.model.repository;

import com.library.app.category.model.Category;
import com.library.app.category.repository.CategoryRepository;
import com.library.app.commontests.utils.DBCommand;
import com.library.app.commontests.utils.DBCommandTransactionalExecutor;
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
        Long categoristaSaattuId = executor.executeCommand(() ->
            categoryRepository.add(javaCategory()).getId()
        );

        assertNotNull(categoristaSaattuId);
        Category category = categoryRepository.findById(categoristaSaattuId);
        assertNotNull(category);
        assertEquals(category.getName(), javaCategory().getName());
    }
}

package com.library.app.commontests.utils;

import org.junit.Ignore;

import javax.persistence.EntityManager;

@Ignore
public class DBCommandTransactionalExecutor {
    EntityManager em;

    public DBCommandTransactionalExecutor(EntityManager em) {
        this.em = em;
    }

    public <T> T executeCommand(DBCommand<T> dbCommand) {
       try {
           em.getTransaction().begin();
           T palautaTämäArvo = dbCommand.execute();
           em.getTransaction().commit();
           em.clear();
           return palautaTämäArvo;
       } catch (final Exception e) {
           e.printStackTrace();
           em.getTransaction().rollback();
           throw new IllegalStateException(e);
       }
    }
}

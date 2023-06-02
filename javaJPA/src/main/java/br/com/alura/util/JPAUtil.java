package br.com.alura.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

    public static final EntityManagerFactory FACTORY =  Persistence
            .createEntityManagerFactory("loja");

    public static EntityManager getEM() {
        return FACTORY.createEntityManager();
    }

}

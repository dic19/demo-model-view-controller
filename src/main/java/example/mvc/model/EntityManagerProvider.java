/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package example.mvc.model;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author Delcio Amarillo
 */
public class EntityManagerProvider {
    
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private static EntityManagerProvider provider;
    
    
    private EntityManagerProvider() {
        initEntityManager();
    }

    private void initEntityManager() {
        entityManagerFactory = Persistence.createEntityManagerFactory("MvcDemoPU");
        entityManager = entityManagerFactory.createEntityManager();
    }
    
    public static synchronized EntityManagerProvider getProvider() {
        if (provider == null) {
            provider = new EntityManagerProvider();
        }
        return provider;
    }
    
    public EntityManager getEntityManager() {
        return entityManager;
    }
    
    public void closeResources() {
        if (entityManager != null && entityManager.isOpen()) {
            entityManager.close();
        }
        
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }
}

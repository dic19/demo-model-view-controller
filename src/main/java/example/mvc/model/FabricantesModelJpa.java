/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package example.mvc.model;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

/**
 * @author Delcio Amarillo
 */
public class FabricantesModelJpa implements IFabricantesModel {
    
    private final EntityManager entityManager;
    
    public FabricantesModelJpa(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Fabricante getFabricantePorId(BigInteger id) {
        return entityManager.find(Fabricante.class, id);
    }

    @Override
    public List<Fabricante> getFabricantes() {
        List<Fabricante> fabricantes = new ArrayList<>();
        String jpql = "SELECT f FROM Fabricante f";
        TypedQuery<Fabricante> query = entityManager.createQuery(jpql, Fabricante.class);
        fabricantes.addAll(query.getResultList());
        return fabricantes;
    }

    @Override
    public int insertar(Fabricante fabricante) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            entityManager.persist(fabricante);
            transaction.commit();
            return EXITO;
        } catch (EntityExistsException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            transaction.rollback();
            return FALLO;
        }
    }

    @Override
    public int modificar(Fabricante fabricante) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            entityManager.merge(fabricante);
            transaction.commit();
            return EXITO;
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            transaction.rollback();
            return FALLO;
        }
    }

    @Override
    public int eliminar(Fabricante fabricante) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            Fabricante fabricanteAEliminar = entityManager.getReference(Fabricante.class, fabricante.getId());
            entityManager.remove(fabricanteAEliminar);
            transaction.commit();
            return EXITO;
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            transaction.rollback();
            return FALLO;
        }
    }

    @Override
    public int eliminar(List<Fabricante> fabricantes) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            for (Fabricante fabricante : fabricantes) {
                Fabricante fabricanteAEliminar = entityManager.getReference(Fabricante.class, fabricante.getId());
                entityManager.remove(fabricanteAEliminar);
            }
            transaction.commit();
            return EXITO;
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            transaction.rollback();
            return FALLO;
        }
    }
}

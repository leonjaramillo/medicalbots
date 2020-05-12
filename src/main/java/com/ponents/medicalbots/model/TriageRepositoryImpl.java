package com.ponents.medicalbots.model;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;

public class TriageRepositoryImpl implements TriageRepository {
    
    EntityManager em;
    
    public TriageRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Triage> getAll() {
        // Devuelve todos los registros de triage
        Query q = em.createQuery("select t from Triage t");
        return q.getResultList();
    }

    @Override
    public Triage getTriageById(long id) {
        Triage triage = em.find(Triage.class, id);
        if (triage == null) {
            throw new EntityNotFoundException("Can't find triage record with ID " + id);
        }
        return triage;
    }

    @Override
    public void add(Triage triage) {
        // Ingresa un registro de triage en la base de datos
        em.getTransaction().begin();
        em.persist(triage);
        em.getTransaction().commit();
    }

    @Override
    public void delete(long id) {
        Triage triage = em.find(Triage.class, id);
        if (triage == null) {
            throw new EntityNotFoundException("Can't find triage record with ID "
                    + id);
        } else {
            em.getTransaction().begin();
            em.remove(triage);
            em.getTransaction().commit();
        }
    }
    
}

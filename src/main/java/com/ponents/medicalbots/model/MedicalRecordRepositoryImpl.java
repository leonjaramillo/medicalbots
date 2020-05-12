package com.ponents.medicalbots.model;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;

public class MedicalRecordRepositoryImpl implements MedicalRecordRepository {

    EntityManager em;

    public MedicalRecordRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<MedicalRecord> getAll() {
        // Devuelve todas las historias médicas existentes
        Query q = em.createQuery("select t from MedicalRecord t");
        return q.getResultList();
    }

    @Override
    public MedicalRecord getMedicalRecordById(long id) throws EntityNotFoundException {
        MedicalRecord medicalRecord = em.find(MedicalRecord.class, id);
        if (medicalRecord == null) {
            throw new EntityNotFoundException("Can't find medical record with ID " + id);
        }
        return medicalRecord;
    }

    @Override
    public void add(MedicalRecord medicalRecord) {
        // Ingresa una historia médica en la base de datos
        em.getTransaction().begin();
        em.persist(medicalRecord);
        em.getTransaction().commit();
    }

    @Override
    public void delete(long id) throws EntityNotFoundException {
        MedicalRecord medicalRecord = em.find(MedicalRecord.class, id);
        if (medicalRecord == null) {
            throw new EntityNotFoundException("Can't find medical record with ID "
                    + id);
        } else {
            em.getTransaction().begin();
            em.remove(medicalRecord);
            em.getTransaction().commit();
        }
    }

}

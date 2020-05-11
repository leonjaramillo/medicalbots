package com.ponents.medicalbots.model;

import java.util.List;

public interface MedicalRecordRepository {
    List<MedicalRecord> getAll();
    MedicalRecord getMedicalRecordById(long id);
    void add(MedicalRecord medicalRecord);
    void delete(long id);
}

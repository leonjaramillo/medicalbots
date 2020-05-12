package com.ponents.medicalbots.model;

import java.util.List;

public interface TriageRepository {
    List<Triage> getAll();
    Triage getTriageById(long id);
    void add(Triage triage);
    void delete(long id);
}

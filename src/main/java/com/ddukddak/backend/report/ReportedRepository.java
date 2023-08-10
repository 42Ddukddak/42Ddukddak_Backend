package com.ddukddak.backend.report;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReportedRepository {

    private final EntityManager em;

    public ReportedMessage findOne(Long id) {
        return em.find(ReportedMessage.class, id);
    }

    public void save(ReportedMessage reportedMessage) {
        em.persist(reportedMessage);
    }

    public void delete(ReportedMessage reportedMessage) {
        em.remove(reportedMessage);
    }
}

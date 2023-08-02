package com.ddukddak.backend.reservation;

import com.ddukddak.backend.reservation.Reservation;
import com.ddukddak.backend.user.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReservationRepository {

    private final EntityManager em;

    public void save(Reservation reservation){
        em.persist(reservation);
    }

    public Reservation findOne(Long id){
        return em.find(Reservation.class, id);
    }

    public List<Reservation> findAll(User user) {
        return em.createQuery("select r from Reservation r where r.user.id = :id", Reservation.class)
                .setParameter("id", user.getId())
                .getResultList();
    }

    public void delete(Reservation reservation){
        em.remove(reservation);
    }
}

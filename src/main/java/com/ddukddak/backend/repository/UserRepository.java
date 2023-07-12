package com.ddukddak.backend.repository;

import com.ddukddak.backend.domain.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserRepository {

    private final EntityManager em;

    @Transactional
    public void save(User user) {
        em.persist(user);
    }

    /*
    * 기본적인 조회 로직
    * */
    public User findOne(Long id) {
        return em.find(User.class, id);
    }

    public List<User> findAll() {
        return em.createQuery("select m from User m", User.class)
                .getResultList();
    }

    public List<User> findByName(String userName) {
        return em.createQuery("select m from User m where m.intraId = :userName", User.class)
                .setParameter("login", userName)
                .getResultList();
    }
}

package com.ddukddak.backend.repository;

import com.ddukddak.backend.domain.OauthToken;
import com.ddukddak.backend.domain.Token;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class TokenRepository {

    private final EntityManager em;

    @Transactional
    public String saveRefreshToken(String name, OauthToken oauthToken){
        Token token = new Token(name, oauthToken.getAccess_token(), oauthToken.getRefresh_token());
        em.persist(token);
        return token.getUUID();
    }

    public Token findByName(String name){
        try{
            return em.createQuery("select m from Token m where m.userName = :name", Token.class)
                    .setParameter("name", name)
                    .getSingleResult();
        }catch(IllegalStateException e){
            return null;
        }
    }
}

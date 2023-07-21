package com.ddukddak.backend.api;

import com.ddukddak.backend.api.entity.OauthToken;
import com.ddukddak.backend.api.entity.Token;
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

    public Token findByKey(String key){
        try{
            return em.createQuery("select m from Token m where m.UUID = :key", Token.class)
                    .setParameter("key", key)
                    .getSingleResult();
        }catch(IllegalStateException e){
            return null;
        }
    }
}

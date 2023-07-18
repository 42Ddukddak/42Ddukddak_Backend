package com.ddukddak.backend.service;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
@Slf4j
@ServerEndpoint("/")
public class WebSocketChatService {
    private static Set<Session> clients = Collections.synchronizedSet(new HashSet<Session>());

    @OnOpen
    public void onOpen(Session session) {
        log.info("session  : " + session.toString());
        Map<String, List<String>> res = session.getRequestParameterMap();
        log.info("res :" + res);

        if (!clients.contains(session)) {
            clients.add(session);
            log.info("if no session  : " + session.toString());
        } else {
            log.info("이미 연결됨");
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        log.info("받은 메세지 : " + message);

        for (Session s : clients) {
            log.info("보낸 메세지: " + message);

            s.getBasicRemote().sendText(message);
        }
    }

    @OnClose
    public void onClose(Session session) {
        log.info("세션 종료" + session);
        clients.remove(session);
    }
}

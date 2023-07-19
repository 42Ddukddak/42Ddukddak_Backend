//package com.ddukddak.backend.service;
//
//import jakarta.websocket.OnClose;
//import jakarta.websocket.OnMessage;
//import jakarta.websocket.OnOpen;
//import jakarta.websocket.Session;
//import jakarta.websocket.server.ServerEndpoint;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//import java.util.*;
//
///*
//* publicChatRoom에서 websocket 사용을 위한 endpoint 설정,
//* OnOpen - 처음 접속 시 Session 부여,
//* OnMessage - 메세지 수송신 어노테이션
//* OnClose - 세션 종료 시 remove
//* */
//@Service
//@Slf4j
//@ServerEndpoint("/ws")
//public class WebSocketChatService {
//    private static Set<Session> clients = Collections.synchronizedSet(new HashSet<Session>());
//
//    @OnOpen
//    public void onOpen(Session session) {
//        log.info("session  : " + session.toString());
//        Map<String, List<String>> res = session.getRequestParameterMap();
//        log.info("res :" + res);
//
//        if (!clients.contains(session)) {
//            clients.add(session);
//            log.info("if no session  : " + session.toString());
//        } else {
//            log.info("이미 연결됨");
//        }
//    }
//
//    @OnMessage
//    public void onMessage(String message, Session session) throws IOException {
//        log.info("받은 메세지 : " + message);
//
//        for (Session s : clients) {
//            log.info("보낸 메세지: " + message);
//
//            s.getBasicRemote().sendText(message);
//        }
//    }
//
//    @OnClose
//    public void onClose(Session session) {
//        log.info("세션 종료" + session);
//        clients.remove(session);
//    }
//}

package com.ddukddak.backend.utils;

public class Define {
    public static final String REDIRECT_URI = "https://api.intra.42.fr/oauth/authorize?client_id=u-s4t2ud-3b57ef43b210f8fbf7a0029fa629f976bd0a1506976d74b843eab9f4bafa2727&redirect_uri=http%3A%2F%2Flocalhost%2Fauth%2Fcallback&response_type=code";
    public static final String DOMAIN = "http://localhost";
    public static final String PUBLIC_ROOM_ID = java.util.UUID.randomUUID().toString();
    public static final Long PUBLIC_CHAT_ROOM_ID = 999L;
    public static final int MAX_COUNT = 50;
    public static final Long EXPIRE_MIN = 15L;
}

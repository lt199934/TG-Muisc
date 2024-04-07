package net.ltbk.music.common;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @Program: TG-Music
 * @ClassName SessionManager
 * @Description: 自定义会话管理
 * @Author: liutao
 * @Create: 2024/4/7 21:51
 * @Version 1.0
 **/

public class SessionManager {
    private static final Map<String, HttpSession> SESSION_MAP = new HashMap<>();

    public static synchronized void addSession(String userId, HttpSession session) {
        HttpSession oldSession = getSession(userId);
        if (oldSession != null && !oldSession.isNew()) {
            oldSession.invalidate();
        }
        SESSION_MAP.put(userId, session);
    }

    public static synchronized void removeSession(String userId) {
        HttpSession session = getSession(userId);
        session.removeAttribute(userId);
        session.invalidate();
        SESSION_MAP.remove(userId);
    }

    public static synchronized HttpSession getSession(String userId) {
        return SESSION_MAP.get(userId);
    }

    public static boolean isLogin(String userId) {
        return SESSION_MAP.containsKey(userId);
    }
}

package net.ltbk.music.common;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Program: TG-Music
 * @ClassName SessionManager
 * @Description: 自定义会话管理
 * @Author: liutao
 * @Create: 2024/4/7 21:51
 * @Version 1.0
 **/

public class SessionManager {
    private static final Map<String, HttpSession> SESSION_MAP = new ConcurrentHashMap<>();

    public static synchronized void addSession(String userId, HttpSession session) {
        HttpSession oldSession = getSession(userId);
        if (oldSession != null) {
            try {
                // 如果存新再过期旧session
                if (!oldSession.isNew()) {
                    oldSession.invalidate();
                }
            } catch (IllegalStateException e) {
                SESSION_MAP.remove(userId);
            }
        }
        // 存储新session
        SESSION_MAP.put(userId, session);
    }

    public static synchronized void removeSession(String userId) {
        HttpSession session = getSession(userId);
        session.removeAttribute(userId);
        session.removeAttribute("user");
        session.invalidate();
        SESSION_MAP.remove(userId);
    }

    public static synchronized HttpSession getSession(String userId) {
        return SESSION_MAP.get(userId);
    }

    public static synchronized boolean isLogin(String userId) {
        return SESSION_MAP.containsKey(userId);
    }
}

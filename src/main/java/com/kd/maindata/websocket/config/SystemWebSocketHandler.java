package com.kd.maindata.websocket.config;

import com.kd.maindata.constants.WebSocketConstant;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.*;

import java.util.ArrayList;

/**
 * Created by xhuanlee on 2017/3/19.
 */
public class SystemWebSocketHandler implements WebSocketHandler {

    private static final Logger logger;

    private static final ArrayList<WebSocketSession> users;

    static {
        logger = LoggerFactory.getLogger(SystemWebSocketHandler.class);
        users = new ArrayList<WebSocketSession>();
    }



    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        logger.debug("websocket connect success: " + webSocketSession.toString());
        users.add(webSocketSession);
        String userName = (String)webSocketSession.getAttributes().get(WebSocketConstant.WEBSOCKET_USER);
        if(!StringUtils.isBlank(userName)) {
            sendMessageToAllUser(new TextMessage(userName + " 已经成功连接"));
            logger.debug("websocket connect success: user(" + userName + ")");
        }
    }

    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        // sendMessage();
    }

    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        if(webSocketSession.isOpen()) {
            webSocketSession.close();
        }
        logger.debug("websocket error closed: " + webSocketSession.toString());
        users.remove(webSocketSession);
    }

    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        logger.debug("websocket closed: " + webSocketSession.toString());
        users.remove(webSocketSession);
    }

    public boolean supportsPartialMessages() {
        return false;
    }

    public void sendMessageToAllUser(TextMessage message) {
        for (WebSocketSession user : users) {
            try {
                if(user.isOpen()) {
                    user.sendMessage(message);
                }
            } catch (Exception e) {
                logger.debug(e.getMessage());
            }
        }
    }

    public void sendMessageToSpecificUser(String userName, TextMessage message) {
        for(WebSocketSession user : users) {
            if(user.getAttributes().get(WebSocketConstant.WEBSOCKET_USER).equals(userName)) {
                try {
                    if(user.isOpen()) {
                        user.sendMessage(message);
                    }
                } catch (Exception e) {
                    logger.debug(e.getMessage());
                }
                break;
            }
        }
    }
}

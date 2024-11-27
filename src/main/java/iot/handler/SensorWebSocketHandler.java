package iot.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class SensorWebSocketHandler extends TextWebSocketHandler {

    private final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
        System.out.println("WebSocket 연결됨: " + session.getId());
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        System.out.println("Received message: " + payload);

        // Ping 처리
        if (payload.contains("ping")) {
            session.sendMessage(new TextMessage("pong")); // Pong 응답
            System.out.println("Pong sent to client");
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);
        System.out.println("WebSocket 닫힘: " + session.getId() + ", 상태: " + status);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        System.err.println("WebSocket 에러 - 세션 ID: " + session.getId());
        exception.printStackTrace();
    }
    public void broadcastMessage(String message) {
        for (WebSocketSession session : sessions) {
            try {
                if (session.isOpen()) {
                    String jsonMessage = "{\"type\":\"pong\",\"message\":\"Server Response\"}";
                    session.sendMessage(new TextMessage(jsonMessage));
                    System.out.println("메시지 전송: " + jsonMessage);
                }
            } catch (IOException e) {
                System.err.println("메시지 전송 실패 - 세션 ID: " + session.getId());
                e.printStackTrace();
            }
        }
    }

}

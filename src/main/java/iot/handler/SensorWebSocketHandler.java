package iot.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import iot.dto.ResponseDto;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class SensorWebSocketHandler extends TextWebSocketHandler {

    private final Set<WebSocketSession> sessions = ConcurrentHashMap.newKeySet();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
        System.out.println("WebSocket 연결됨: " + session.getId());
        System.out.println("현재 세션 수: " + sessions.size());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);
        System.out.println("WebSocket 닫힘: " + session.getId() + ", 상태: " + status);
        System.out.println("현재 세션 수: " + sessions.size());
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
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        System.err.println("WebSocket 에러 - 세션 ID: " + session.getId());
        exception.printStackTrace();
    }

    public void broadcastMessage(/*String messageContent*/ ResponseDto responseDto) {
        //String jsonMessage = "{\"type\":\"notification\",\"message\":\"" + messageContent + "\"}";
        //System.out.println(messageContent);
        //System.out.println(sessions.size());
        for (WebSocketSession session : sessions) {
            try {
                if (session.isOpen()) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    String jsonMessage = objectMapper.writeValueAsString(responseDto);
                    System.out.println("세션 열림: " + session.getId());
                    session.sendMessage(new TextMessage(jsonMessage));
                    System.out.println("알림 메시지 전송: " + jsonMessage);
                } else {
                    System.err.println("세션 닫힘: " + session.getId());
                }
            } catch (IOException e) {
                System.err.println("알림 전송 실패 - 세션 ID: " + session.getId());
                e.printStackTrace();
            }
        }
    }
}

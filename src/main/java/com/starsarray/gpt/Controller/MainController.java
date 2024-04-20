package com.starsarray.gpt.Controller;

import com.starsarray.gpt.GPT.ChatGptRequest;
import com.starsarray.gpt.GPT.ChatGptResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("api/v1/chat/gpt")
@RequiredArgsConstructor
public class MainController {

        // 定义鉴权请求头和秘钥
        public static final String AUTH_REQUEST_HEADER = "auth";
        public static final String AUTH_REQUEST_SECRET = "secretKey";
//        private static final String API_KEY = "sk-proj-UzYAyM6BYZpD238ObQBsT3BlbkFJfbLX0GBEbAXcT1PwkWQ6";
        private static final String API_KEY = "fastgpt-W2VX2y3Znfhq9spSRF87AN3WQwxtrFQ0q0CmxQybnFZK1SXVVDQ2S4okaj8o8xPxf";

//        private static final String CHAT_BASE_URL = "https://api.openai.com/%s";
        private static final String CHAT_BASE_URL = "https://api.fastgpt.in/%s";

//        private static final String CHAT_URL = "/v1/chat/completions";
        private static final String CHAT_URL = "/api/v1/chat/completions";

        @PostMapping("test")
        public String wxCallback(@RequestBody String message, HttpServletRequest request,
                                 HttpServletResponse response) {
// 基本认证
            String authHeader = request.getHeader(AUTH_REQUEST_HEADER);
            if (!AUTH_REQUEST_SECRET.equals(authHeader)) {
                response.setStatus(403);
                return null;
            }
            if (message == null) {
                throw new RuntimeException("请求参数为空");
            }
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + API_KEY);
            headers.add("Content-Type", "application/json");
            ChatGptRequest request1 = new ChatGptRequest(message);
            HttpEntity<ChatGptRequest> httpEntity = new HttpEntity<>(request1, headers);
            //本机代理 如果是全局梯子了之后可以注释本段
            RestTemplate restTemplate = new RestTemplate(new SimpleClientHttpRequestFactory() {{
//                setProxy(new java.net.Proxy(java.net.Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 7890)));
                setConnectTimeout(180000);
                setReadTimeout(180000);
            }});
            ResponseEntity<ChatGptResponse> response1;
            response1 = restTemplate.exchange(String.format(CHAT_BASE_URL, CHAT_URL), HttpMethod.POST, httpEntity, ChatGptResponse.class);
            ChatGptResponse result = response1.getBody();
            if (result != null) {
                return "CHAT-GPT :" + result.getChoices().get(0).getMessage().getContent();
            } else {
                return "No response from ChatGPT API.";
            }
        }
}

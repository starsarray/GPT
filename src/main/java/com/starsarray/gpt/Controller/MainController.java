package com.starsarray.gpt.Controller;

import com.starsarray.gpt.GPT.ChatGptRequest;
import com.starsarray.gpt.GPT.ChatGptResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
@RequestMapping("api/v1/chat/gpt")
@RequiredArgsConstructor
public class MainController {


//        private static final String API_KEY = "sk-proj-UzYAyM6BYZpD238ObQBsT3BlbkFJfbLX0GBEbAXcT1PwkWQ6";
        private static final String API_KEY = "fastgpt-W2VX2y3Znfhq9spSRF87AN3WQwxtrFQ0q0CmxQybnFZK1SXVVDQ2S4okaj8o8xPxf";

//        private static final String CHAT_BASE_URL = "https://api.openai.com/%s";
        private static final String CHAT_BASE_URL = "https://api.fastgpt.in/%s";

//        private static final String CHAT_URL = "/v1/chat/completions";
        private static final String CHAT_URL = "/api/v1/chat/completions";

        @GetMapping("test")
        public String wxCallback(@RequestParam("message") String message) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + API_KEY);
            headers.add("Content-Type", "application/json");
            ChatGptRequest request = new ChatGptRequest(message);
            HttpEntity<ChatGptRequest> httpEntity = new HttpEntity<>(request, headers);
            //本机代理 如果是全局梯子了之后可以注释本段
            RestTemplate restTemplate = new RestTemplate(new SimpleClientHttpRequestFactory() {{
//                setProxy(new java.net.Proxy(java.net.Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 7890)));
                setConnectTimeout(180000);
                setReadTimeout(180000);
            }});
            ResponseEntity<ChatGptResponse> response;
            response = restTemplate.exchange(String.format(CHAT_BASE_URL, CHAT_URL), HttpMethod.POST, httpEntity, ChatGptResponse.class);
            ChatGptResponse result = response.getBody();
            if (result != null) {
                return "CHAT-GPT :" + result.getChoices().get(0).getMessage().getContent();
            } else {
                return "No response from ChatGPT API.";
            }
        }
}

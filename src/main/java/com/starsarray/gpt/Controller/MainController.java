package com.starsarray.gpt.Controller;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.starsarray.gpt.GPT.ChatGptRequest;
import com.starsarray.gpt.GPT.ChatGptResponse;
import com.starsarray.gpt.vo.Gpt35TurboVO;
import com.theokanning.openai.client.OpenAiApi;
import com.theokanning.openai.completion.CompletionChoice;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import retrofit2.Retrofit;

import java.net.InetSocketAddress;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
//    @PostMapping("/gpt")
//    public String chat(String prompt){
//
//        //api key
////        String token = "fastgpt-W2VX2y3Znfhq9spSRF87AN3WQwxtrFQ0q0CmxQybnFZK1SXVVDQ2S4okaj8o8xPxf";
//        String token = "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";
//        String url = "https://api.openai.com/v1/chat/completions";
////        String url = "https://api.fastgpt.in/api/";
//        ObjectMapper mapper = OpenAiService.defaultObjectMapper();
//        OkHttpClient client = OpenAiService.defaultClient(token, Duration.ofSeconds(10L));
//        //这里重新设置了一下请求地址,官方原地址:https://api.openai.com/
//        Retrofit retrofit = OpenAiService.defaultRetrofit(client, mapper).newBuilder().baseUrl(url).build();
//
//        OpenAiApi api = retrofit.create(OpenAiApi.class);
//        OpenAiService service = new OpenAiService(api);
//
//        CompletionRequest completionRequest = CompletionRequest.builder()
//                .model("gpt-3.5-turbo")
//                .prompt(prompt)
//                .temperature(0.5)
//                .maxTokens(2048)
//                .topP(1D)
//                .frequencyPenalty(0D)
//                .presencePenalty(0D)
//                .build();
//        List<CompletionChoice> compList = service.createCompletion(completionRequest).getChoices();
//        compList.forEach(System.out::println);
//        StringBuilder sb = new StringBuilder();
//        for (CompletionChoice comp : compList) {
//            sb.append(comp.getText());
//        }
//        return sb.toString();
//    }
//@GetMapping(value = "/test", produces = "text/event-stream;charset=UTF-8")
//public String test(@RequestParam String message) {
//    //回复用户
////    String apikey = "fastgpt-W2VX2y3Znfhq9spSRF87AN3WQwxtrFQ0q0CmxQybnFZK1SXVVDQ2S4okaj8o8xPxf";
//    String apikey = "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";
//    //请求ChatGPT的URL
//    String url = "https://api.openai.com/v1/chat/completions";
////    String url = "https://api.fastgpt.in/api";
//
//    Gpt35TurboVO gpt35TurboVO = new Gpt35TurboVO();
//    gpt35TurboVO.setRole("user");
//    gpt35TurboVO.setContent(message);
//    List<Gpt35TurboVO> objects = new ArrayList<>();
//    objects.add(gpt35TurboVO);
//    Map<Object, Object> objectObjectHashMap = new HashMap<>();
//    objectObjectHashMap.put("model", "gpt-3.5-turbo-instruct");  //使用的模型
//    objectObjectHashMap.put("messages", objects);       //提问信息
//    objectObjectHashMap.put("stream", false);            //流
//    objectObjectHashMap.put("temperature", 0);          //GPT回答温度（随机因子）
//    objectObjectHashMap.put("frequency_penalty", 0);    //重复度惩罚因子
//    objectObjectHashMap.put("presence_penalty", 0.6);   //控制主题的重复度
//    String postData = JSONUtil.toJsonStr(objectObjectHashMap);
//    String result2 = HttpRequest.post(url)
//            .header("Authorization", "Bearer " + apikey)//头信息，多个头信息多次调用此方法即可
//            .header("Content-Type", "application/json")
//            .body(postData)//表单内容
//            .timeout(200000)//超时，毫秒
//            .execute().body();
//
//    System.out.println(result2);
//
//    return result2;
//
//}

}

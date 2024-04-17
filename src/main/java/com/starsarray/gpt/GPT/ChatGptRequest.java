package com.starsarray.gpt.GPT;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ChatGptRequest {

    /**
     * 模型  具体可以查看官方文档 https://platform.openai.com/docs/models
     */
    private String model = "gpt-3.5-turbo";
    /**
     * 消息
     */
    private List<ChoiceMessage> messages = Lists.newArrayList();

    /**
     * 用户ID
     */
    private String user = "123456";

    /**
     * 随机值
     * 0.0对于同一个问题会回答相同答案
     */
    private Double temperature = 0.9;

    public ChatGptRequest(String message) {
        this.messages.add(new ChoiceMessage(message));
    }
}
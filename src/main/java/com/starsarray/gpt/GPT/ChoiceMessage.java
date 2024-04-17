package com.starsarray.gpt.GPT;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ChoiceMessage {

    /**
     * 角色
     */
    private String role;

    /**
     * 消息体
     */
    private String content;

    public ChoiceMessage(String content) {
        this.role = "user";
        this.content = content;
    }
}
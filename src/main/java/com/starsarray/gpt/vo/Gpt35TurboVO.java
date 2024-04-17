package com.starsarray.gpt.vo;

import lombok.Data;

@Data
public class Gpt35TurboVO {
    private String role;     // 角色一般为 user
    private String content;  // 询问内容
}
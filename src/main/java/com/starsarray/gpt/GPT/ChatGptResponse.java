package com.starsarray.gpt.GPT;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
public class ChatGptResponse {

    private String id;

    private String object;

    private Date created;

    private String model;

    private List<ChoiceResponse> choices;
}
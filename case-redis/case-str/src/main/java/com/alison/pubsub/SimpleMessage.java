package com.alison.pubsub;

import lombok.Data;

@Data
public class SimpleMessage {
    private int id;
    private String publisher;
    private String content;
    private Long createTime;
    private Long updateTime;
}

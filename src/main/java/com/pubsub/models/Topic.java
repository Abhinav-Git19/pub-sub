package com.pubsub.models;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Data
public class Topic {
    private static AtomicInteger counter = new AtomicInteger(0);
    private int topicId;

    private String name;

    private  List<Subscriber> subscriberList;


    public Topic(String name){
        this.topicId = counter.incrementAndGet();
        this.name = name;
        subscriberList = new ArrayList<>();
    }
}

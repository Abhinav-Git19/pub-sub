package com.pubsub.models;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Data
public class Subscriber {

    private static AtomicInteger counter = new AtomicInteger(0);
    private int subscriberId;

    private  String name;

    private  Map<String,String> filter;
    private  List<Message<?>> buffer;

    public Subscriber(String name){
        this.subscriberId = counter.incrementAndGet();
        this.name = name;
        buffer = new ArrayList<>();
        filter = new HashMap<>();
    }
}

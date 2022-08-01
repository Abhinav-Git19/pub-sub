package com.pubsub.models;

import lombok.Data;
import lombok.NonNull;

import java.util.Map;

@Data
public class Message<T> {

    Map<String,String> headers;
    @NonNull
    T payload;
}

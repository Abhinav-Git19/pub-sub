package com.pubsub.repository;

import com.pubsub.models.Subscriber;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SubscriberRepository {

    Map<Integer, Subscriber> subscriberMap;

    public SubscriberRepository(){
        subscriberMap = new ConcurrentHashMap<>();
    }

    public void addSubscriber(Subscriber subscriber){
        subscriberMap.put(subscriber.getSubscriberId(),subscriber);
    }

    public Subscriber getSubscriber(int subscriberId){
        return subscriberMap.get(subscriberId);
    }

    public Subscriber getSubscriberByName(String name){

        for(Map.Entry<Integer,Subscriber> subscriberEntry : subscriberMap.entrySet()){
            if(subscriberEntry.getValue().getName().equals(name)) return subscriberEntry.getValue();
        }
        return null;
    }


}

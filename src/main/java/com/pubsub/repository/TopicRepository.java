package com.pubsub.repository;

import com.pubsub.models.Topic;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


public class TopicRepository {

    Map<Integer, Topic> topicMap;

    public TopicRepository() {
        this.topicMap = new ConcurrentHashMap<>();
    }


    public void addTopic(Topic topic){
        topicMap.put(topic.getTopicId(),topic);
    }

    public Topic getTopic (int topicId){
        return topicMap.get(topicId);
    }

    public Topic getTopicByName(String name){

        for(Map.Entry<Integer,Topic> topicEntry : topicMap.entrySet()){
            if(topicEntry.getValue().getName().equals(name)) return topicEntry.getValue();
        }
        return null;
    }

    public List<Topic> getAllTopics(){
        return new ArrayList<>(topicMap.values());
    }


}

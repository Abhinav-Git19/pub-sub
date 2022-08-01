package com.pubsub.service;

import com.pubsub.exceptions.InvalidPubSubOpException;
import com.pubsub.models.Message;
import com.pubsub.models.Subscriber;
import com.pubsub.models.Topic;
import com.pubsub.repository.SubscriberRepository;
import com.pubsub.repository.TopicRepository;

import java.util.List;
import java.util.Map;

public class MessagingService {

    private final TopicRepository topicRepository;
    private final SubscriberRepository subscriberRepository;

    public MessagingService(TopicRepository topicRepository, SubscriberRepository subscriberRepository) {
        this.topicRepository = topicRepository;
        this.subscriberRepository = subscriberRepository;
    }


    public void createTopic(String topicName) throws InvalidPubSubOpException {

        Topic topic = topicRepository.getTopicByName(topicName);
        if(topic!=null)
            throw new InvalidPubSubOpException("Topic already exists");

        Topic newTopic = new Topic(topicName);
        topicRepository.addTopic(newTopic);
    }

    public List<Topic> viewAllTopics(){
        return topicRepository.getAllTopics();
    }

    public void createDefaultSubscriber(int topicId, String subscriberName) throws InvalidPubSubOpException {

        Topic existingTopic = getTopic(topicId);

        Subscriber newSubscriber = new Subscriber(subscriberName);
        subscriberRepository.addSubscriber(newSubscriber);
        existingTopic.getSubscriberList().add(newSubscriber);

    }

    public void createSubscriberWithFilter(int topicId, String subscriberName, Map<String,String> filter) throws InvalidPubSubOpException {
        Topic existingTopic = getTopic(topicId);
        Subscriber newSubscriber = new Subscriber(subscriberName);
        newSubscriber.setFilter(filter);
        subscriberRepository.addSubscriber(newSubscriber);
        existingTopic.getSubscriberList().add(newSubscriber);
    }




    private Topic getTopic(int topicId) throws InvalidPubSubOpException {
        Topic existingTopic = topicRepository.getTopic(topicId);
        if(existingTopic == null)
            throw new InvalidPubSubOpException("Topic does not exists");
        return existingTopic;
    }
}

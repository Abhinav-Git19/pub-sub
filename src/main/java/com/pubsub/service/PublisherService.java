package com.pubsub.service;

import com.pubsub.exceptions.InvalidPubSubOpException;
import com.pubsub.models.Message;
import com.pubsub.models.Subscriber;
import com.pubsub.models.Topic;
import com.pubsub.repository.SubscriberRepository;
import com.pubsub.repository.TopicRepository;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PublisherService {

    private final TopicRepository topicRepository;
    private final SubscriberRepository subscriberRepository;

    private final ExecutorService executorService;

    public PublisherService(TopicRepository topicRepository, SubscriberRepository subscriberRepository,
                            int nofOfWorkerThreads) {
        this.topicRepository = topicRepository;
        this.subscriberRepository = subscriberRepository;
        executorService = Executors.newFixedThreadPool(nofOfWorkerThreads);
    }

    public void pushMessage(int topicId,List<Message> messages) throws InvalidPubSubOpException {

        Topic topic = topicRepository.getTopic(topicId);
        if(topic == null)
            throw new InvalidPubSubOpException("Cannot push message to nonExisting topic");

        for(Subscriber subscriber : topic.getSubscriberList()){
            executorService.submit(() -> pushMessagesToSubscriber(subscriber,messages));
        }

    }


    private void pushMessagesToSubscriber(Subscriber subscriber,List<Message> messages){

        for(Message message : messages){
            if(subscriber.getFilter().isEmpty() || subscriber.getFilter().equals(message.getHeaders()))
                subscriber.getBuffer().add(message);
        }
    }



    public List<Message<?>> viewMessages(int subscriberId) throws InvalidPubSubOpException {
        Subscriber subscriber = subscriberRepository.getSubscriber(subscriberId);
        if(subscriber==null)
            throw new InvalidPubSubOpException("Subscriber does not exists");

        return  subscriber.getBuffer();
    }



}

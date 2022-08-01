package org.example;

import com.pubsub.exceptions.InvalidPubSubOpException;
import com.pubsub.models.Message;
import com.pubsub.repository.SubscriberRepository;
import com.pubsub.repository.TopicRepository;
import com.pubsub.service.MessagingService;
import com.pubsub.service.PublisherService;
import com.pubsub.utils.CommandParser;

import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to PubSubApp!");
        System.out.println("Enter commands");
        System.out.println(introText());
        TopicRepository topicRepository = new TopicRepository();
        SubscriberRepository subscriberRepository = new SubscriberRepository();

        MessagingService messagingService = new MessagingService(topicRepository,subscriberRepository);
        PublisherService publisherService = new PublisherService(topicRepository,subscriberRepository,5);
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        do{
            try{
                String input = scanner.nextLine();
                String[] inputArgs = CommandParser.validateCommand(input);

                switch (inputArgs[0]){
                    case "1":
                        messagingService.createTopic(inputArgs[1]);
                        System.out.println("Topic Addition successful!");
                        break;
                    case "2":
                        System.out.println(messagingService.viewAllTopics());
                        break;
                    case "3":
                        messagingService.createDefaultSubscriber(Integer.parseInt(inputArgs[1]),inputArgs[2]);
                        System.out.println("Subscription created");
                        break;
                    case "4":
                        List<Message> messageList = CommandParser.generateMessagesFromInput(inputArgs);
                        publisherService.pushMessage(Integer.parseInt(inputArgs[inputArgs.length-1]),messageList);
                        System.out.println("Messages pushed");
                        break;
                    case "5":
                        List<Message<?>>  displayMessages = publisherService.viewMessages(Integer.parseInt(inputArgs[1]));
                        CommandParser.printMessagesOnConsole(displayMessages);
                        break;
                    case "6":
                        System.out.println("Exiting...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Wrong command try again...");
                        break;
                }

            }catch (InvalidPubSubOpException e){
                System.out.println(e.getMessage());
            }

        }while (true);


    }

    private static String introText(){
        return "1  create <topic_name>\n"+
                "2 viewAll topics\n"+
                "3 add <topic_id> <subscriber_name>\n"+
                "4 push message <topic_name>\n"+
                "5 view <subscriber_name> data\n"+
                "6 exit";
    }
}
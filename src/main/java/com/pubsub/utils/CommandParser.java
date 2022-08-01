package com.pubsub.utils;

import com.pubsub.exceptions.InvalidPubSubOpException;
import com.pubsub.models.Message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CommandParser {

    public static String[] validateCommand(String input) throws InvalidPubSubOpException {

        try {
            String[] args = input.split(" ");

            int commandInt = Integer.parseInt(args[0]);
            if(commandInt<1 || commandInt>6)
                throw new InvalidPubSubOpException("Invalid command number");

            switch (commandInt){
                case 1:
                    if(args.length!=2) throw new InvalidPubSubOpException("Invalid Topic create command!");
                    break;
                case 3:
                    if(args.length!=3) throw new InvalidPubSubOpException("Invalid subcription addition command!");
                    break;
                case 5:
                    if(args.length!=2) throw new InvalidPubSubOpException("Invalid Subscription data view  command!");
                    break;

            }
            return args;


        }catch (Exception e) {
            throw new InvalidPubSubOpException("Command is invalid\n" +e.getMessage());
        }

    }

    public static List<Message> generateMessagesFromInput(String[] inputArgs){

        return new ArrayList<>(Arrays.asList(Arrays.
                copyOfRange(inputArgs, 1, inputArgs.length - 1))).stream()
                .map(Message::new)
                .collect(Collectors.toList());


    }

    public static void printMessagesOnConsole(List<Message<?>> messages){
        messages.forEach(System.out::println);
    }
}

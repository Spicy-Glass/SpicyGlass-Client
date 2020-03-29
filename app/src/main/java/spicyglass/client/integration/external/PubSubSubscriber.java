package spicyglass.client.integration.external;

import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.cloud.pubsub.v1.Subscriber;
import com.google.pubsub.v1.ProjectSubscriptionName;

public class PubSubSubscriber {
    public static void init() {
        String projectId = "pub-sub132608";
        String subscriptionId = "testSub";

        ProjectSubscriptionName subscriptionName =
                ProjectSubscriptionName.of(projectId, subscriptionId);
        // Instantiate an asynchronous message receiver
        MessageReceiver receiver =
                (message, consumer) -> {
                    // handle incoming message, then ack/nack the received message
                    System.out.println("Id : " + message.getMessageId());
                    System.out.println("Data : " + message.getData().toStringUtf8());
                    consumer.ack();
                };

        Subscriber subscriber = null;
        try {
            // Create a subscriber for "my-subscription-id" bound to the message receiver
            subscriber = Subscriber.newBuilder(subscriptionName, receiver).build();
            subscriber.startAsync().awaitRunning();
            // Allow the subscriber to run indefinitely unless an unrecoverable error occurs
            subscriber.awaitTerminated();
        } finally {
            // Stop receiving messages
            if (subscriber != null) {
                subscriber.stopAsync();
            }
        }
    }
}

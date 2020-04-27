package spicyglass.client.integration.external;

import android.content.Context;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.cloud.pubsub.v1.Subscriber;
import com.google.pubsub.v1.ProjectSubscriptionName;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import spicyglass.client.integration.system.SGLogger;
import spicyglass.client.model.VehicleState;

public final class PubSubSubscriber {
    public static void init(Context ctx) {
        String projectId = "pub-sub132608";
        String subscriptionId = "sub_app";

        ProjectSubscriptionName subscriptionName =
                ProjectSubscriptionName.of(projectId, subscriptionId);
        // Instantiate an asynchronous message receiver
        MessageReceiver receiver =
                (message, consumer) -> {
                    // handle incoming message, then ack/nack the received message
                    SGLogger.info("Subscriber received data!");
                    SGLogger.info("Id : " + message.getMessageId());
                    SGLogger.info("Data : " + message.getData().toStringUtf8());
                    try {
                        JSONObject object = new JSONObject(message.getData().toStringUtf8());
                        VehicleState.INSTANCE.onStatesRetrieved(new APIResponse<>(object, 200, true, null));
                    } catch(JSONException e) {
                        e.printStackTrace();
                    }
                    consumer.ack();
                };

        Subscriber subscriber = null;
        try {
            InputStream creds = ctx.getAssets().open("message.json");
            // Create a subscriber for "my-subscription-id" bound to the message receiver
            subscriber = Subscriber.newBuilder(subscriptionName, receiver)
                    .setCredentialsProvider(FixedCredentialsProvider.create(GoogleCredentials.fromStream(creds)))
                    .build();
            creds.close();
            subscriber.startAsync().awaitRunning();
            // Allow the subscriber to run indefinitely unless an unrecoverable error occurs
            subscriber.awaitTerminated();
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            // Stop receiving messages
            if (subscriber != null)
                subscriber.stopAsync();
        }
    }
}

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

public class RecvTest {
    private final static String QUEUE_NAME = "hello";

    /**
     * Like the SendTest, we need to finally create a channel to receive messages.
     * Here, we don't use the try-catch-with capsule to close the Connection since we need it to receive messages continuously.
     */
    @Test
    public void receiveMessageTest() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" + message + "'");
        };

        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
        });
    }
}

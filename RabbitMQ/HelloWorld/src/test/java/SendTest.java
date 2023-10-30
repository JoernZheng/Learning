import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.jupiter.api.Test;

public class SendTest {
    private final static String QUEUE_NAME = "hello";

    /***
     * 1.Prepare the message content.
     * 2.We need a connection to connect the Client and the RabbitMQ system.
     * 3.In the connection, we need a channel to deliver our message. A channel can be seen as a message delivery pipeline.
     * 4.Send the message through the channel.
     * 5.close all the resources we used during this period.
     */
    @Test
    public void SendMessagesTest() throws Exception {
        String msg = "Hello RabbitMQ!";
        ConnectionFactory factory = new ConnectionFactory();
        try (Connection connection = factory.newConnection()) {
            Channel channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            System.out.println("[x] Sent: '" + msg + "'");
            // We don't need this because when a connection was closed, all the channels inside the connection will be
            // closed automatically.
            // channel.close();
        }
    }
}

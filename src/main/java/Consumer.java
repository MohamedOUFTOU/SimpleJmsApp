import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.Scanner;

public class Consumer {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("User Code : ");
        String code = scanner.nextLine();

        // 1- Create connection to Broker provider
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                "tcp://localhost:61616"
        );

        try {
            Connection connection = connectionFactory.createConnection();
            connection.start();
            // 2- Create a session for the consumer
            // Auto-Acknowledge to send automatiqualy a accusate of reception when the message is delivered
            // False we don't need commit to send mesagges
            Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);

            // 3- Create a Queue or a Topic
            //Destination destination = session.createQueue("enset.queue");
            Destination destination = session.createTopic("enset.topic");
            // 4- Create a consumer of messages in the queue/Topic
            // Code to set a filter of messages to receive ( if code == code? receive else skipMessage)
            MessageConsumer consumer = session.createConsumer(destination,"code='"+code+"'");
            consumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    if (message instanceof TextMessage){
                        TextMessage textMessage = (TextMessage) message;
                        try {
                            System.out.println("Received : "+textMessage.getText());
                        } catch (JMSException e) {
                            e.printStackTrace();
                        }
                    }else {

                    }
                }
            });
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.Scanner;

public class Producer {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("User Code : ");
        String code = scanner.nextLine();

        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                "tcp://localhost:61616"
        );

        try {
            Connection connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
            //Destination destination = session.createQueue("enset.queue");
            Destination destination = session.createTopic("enset.topic");
            MessageProducer messageProducer = session.createProducer(destination);
            messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            TextMessage textMessage = session.createTextMessage();
            textMessage.setText("Hello.......................");
            textMessage.setStringProperty("code",code);

            messageProducer.send(textMessage);
            System.out.println("Message Sent !!!!!");
            session.close();
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}

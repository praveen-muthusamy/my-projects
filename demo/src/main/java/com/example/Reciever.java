/**
 * 
 */
package com.example;

import java.io.IOException;
import java.util.Scanner;

import com.rabbitmq.client.*;

/**
 * @author praveen.muthusamy
 *
 */
public class Reciever {
	private static final String EXCHANGE_NAME = "perficient_notifier";
	private static final String QUEUE_NAME = "prft_all";
	
	  public static void main(String[] argv) throws Exception {
	    ConnectionFactory factory = new ConnectionFactory();
	    factory.setHost("localhost");
	    Connection connection = factory.newConnection();
	    Channel channel = connection.createChannel();

	    channel.exchangeDeclare(EXCHANGE_NAME, "topic");
	    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
	    
	    Scanner scanner = new Scanner(System.in);
		System.out.println("Enter topic name : ");
		String bindingKey = scanner.nextLine();
		scanner.close();
		channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, bindingKey);

	    System.out.println(" [*]Reciever Waiting for messages. To exit press CTRL+C");

	    Consumer consumer = new DefaultConsumer(channel) {
	      @Override
	      public void handleDelivery(String consumerTag, Envelope envelope,
	                                 AMQP.BasicProperties properties, byte[] body) throws IOException {
	        String message = new String(body, "UTF-8");
	        System.out.println(" [x] Received '" + envelope.getRoutingKey() + "':'" + message + "'");
	      }
	    };
	    channel.basicConsume(QUEUE_NAME, true, consumer);
	  }
}

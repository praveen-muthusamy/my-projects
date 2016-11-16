/**
 * 
 */
package com.example;

import java.util.Scanner;

import com.rabbitmq.client.*;

/**
 * @author praveen.muthusamy
 *
 */
public class Publisher {
	private static final String EXCHANGE_NAME = "perficient_notifier";

	  public static void main(String[] argv) {
	    Connection connection = null;
	    Channel channel = null;
	    try {
	      ConnectionFactory factory = new ConnectionFactory();
	      factory.setHost("localhost");

	      connection = factory.newConnection();
	      channel = connection.createChannel();

	      channel.exchangeDeclare(EXCHANGE_NAME, "topic");
		  Scanner scanner = new Scanner(System.in);
		  System.out.println("Enter topic name : ");
		  String routingKey = scanner.nextLine();
		  System.out.println("Enter message : ");
	      String message = scanner.nextLine();
	      scanner.close();
	      String queueName = "prft_"+routingKey.substring(routingKey.indexOf(".")+1,routingKey.length());
	      channel.queueBind(queueName, EXCHANGE_NAME, routingKey);
	      channel.basicPublish("", queueName, null, message.getBytes("UTF-8"));
	      System.out.println(" [x] Sent '"+ queueName +"':'"  + routingKey + "':'" + message + "'");

	    }
	    catch  (Exception e) {
	      e.printStackTrace();
	    }
	    finally {
	      if (connection != null) {
	        try {
	          connection.close();
	        }
	        catch (Exception ignore) {}
	      }
	    }
	  }
}

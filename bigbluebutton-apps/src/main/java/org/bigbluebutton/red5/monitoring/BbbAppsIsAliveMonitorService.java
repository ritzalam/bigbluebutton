package org.bigbluebutton.red5.monitoring;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.bigbluebutton.common.messages.ChannelConstants;
import org.bigbluebutton.common.messages.MessageHeader;
import org.bigbluebutton.common.messages.PubSubPingMessage;
import org.bigbluebutton.red5.client.messaging.ConnectionInvokerService;
import org.bigbluebutton.red5.client.messaging.DisconnectAllMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.bigbluebutton.pubsub.redis.MessagePublisher;
import org.bigbluebutton.pubsub.redis.MessageSender;
import com.google.gson.Gson;

public class BbbAppsIsAliveMonitorService {
	private static Logger log = LoggerFactory.getLogger(BbbAppsIsAliveMonitorService.class);
	
	private static final Executor msgSenderExec = Executors.newFixedThreadPool(1);
	private static final Executor runExec = Executors.newFixedThreadPool(1);
	
	private ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(1);
	
	private BlockingQueue<IKeepAliveMessage> messages = new LinkedBlockingQueue<IKeepAliveMessage>();
	private volatile boolean processMessages = false;
	private KeepAliveTask task = new KeepAliveTask();
	
	private ConnectionInvokerService service;
	private Long lastKeepAliveMessage = 0L;
	
	private MessageSender sender;
	
	private final String SYSTEM_NAME = "BbbAppsRed5";
	
	public void setMessageSender(MessageSender sender) {
		this.sender = sender;
	}
	
	public void setConnectionInvokerService(ConnectionInvokerService s) {
		this.service = s;
	}
	
	public void start() {	
		scheduledThreadPool.scheduleWithFixedDelay(task, 5000, 10000, TimeUnit.MILLISECONDS);
		processKeepAliveMessage();
	}
	
	public void stop() {
		processMessages = false;
		scheduledThreadPool.shutdownNow();
	}
	
	public void handleKeepAliveMessage(String system, Long timestamp) {
		if (system.equals(SYSTEM_NAME)) {
			queueMessage(new KeepAliveMessage(system, timestamp));
		}		
	}
	
	private void queueMessage(IKeepAliveMessage msg) {
		messages.add(msg);
	}
    
  private void processKeepAliveMessage() {
  	processMessages = true;
  	Runnable sender = new Runnable() {
  		public void run() {
  			while (processMessages) {
  				IKeepAliveMessage message;
  				try {
  					message = messages.take();
  					processMessage(message);	
  				} catch (InterruptedException e) {
  					// TODO Auto-generated catch block
  					e.printStackTrace();
  				}	catch (Exception e) {
  					//log.error("Catching exception [{}]", e.toString());
  				}
  			}
  		}
  	};
  	msgSenderExec.execute(sender);		
  } 
  	
  private void processMessage(final IKeepAliveMessage msg) {
  	Runnable task = new Runnable() {
  		public void run() {
	  	  	if (msg instanceof KeepAliveMessage) {
	  	  		processKeepAliveMessage((KeepAliveMessage) msg);
	  	  	} else if (msg instanceof CheckIsAliveTimer) {
	  	  		processCheckIsAliveTimer((CheckIsAliveTimer) msg);
	  	  	}  			
  		}
  	};
  	
    runExec.execute(task);
  }
  
  private void processKeepAliveMessage(KeepAliveMessage msg) {
	  //log.info("BBB Apps Red5 pubsub pong!" + msg.system);
	  lastKeepAliveMessage = System.currentTimeMillis();
  }
  
  private void processCheckIsAliveTimer(CheckIsAliveTimer msg) {
	  Long now = System.currentTimeMillis();

	  if (lastKeepAliveMessage != 0 && (now - lastKeepAliveMessage > 30000)) {
		  log.error("BBB Apps Red5 pubsub error!");
		  service.sendMessage(new DisconnectAllMessage());
	  }
  }
  
	class KeepAliveTask implements Runnable {
	    public void run() {
	     	CheckIsAliveTimer ping = new CheckIsAliveTimer();
	     	queueMessage(ping);
				PubSubPingMessage.PubSubPingMessagePayload payload =
					new PubSubPingMessage.PubSubPingMessagePayload(SYSTEM_NAME, System.currentTimeMillis());

	     	PubSubPingMessage msg = new PubSubPingMessage(payload);

	     	Gson gson = new Gson();
	     	sender.send(ChannelConstants.TO_SYSTEM_CHANNEL, gson.toJson(msg));
	    }
	  }
}

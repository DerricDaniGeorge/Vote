package com.derric.vote.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

@Component
public class SpringListener {


@EventListener
public void handleContextClosedEvent(ContextClosedEvent ctxClosedEvt) {
	System.out.println("Context Closed.......");
	ApplicationContext context=ctxClosedEvt.getApplicationContext();
	Session cassandraSession=context.getBean(Session.class);
	Cluster cassandraCluster=context.getBean(Cluster.class);
	cassandraSession.close();
	cassandraCluster.close();
	System.out.println("Cleared up cassandra session and cluster objects");
} 
@EventListener
public void handleContextStoppedEvent(ContextStoppedEvent ctxStoppedEvt) {
	System.out.println("Context Stopped.......");
	ApplicationContext context=ctxStoppedEvt.getApplicationContext();
	Session cassandraSession=context.getBean(Session.class);
	Cluster cassandraCluster=context.getBean(Cluster.class);
	cassandraSession.close();
	cassandraCluster.close();
	System.out.println("Cleared up cassandra session and cluster objects");
}

}

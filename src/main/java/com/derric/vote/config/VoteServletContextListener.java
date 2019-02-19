package com.derric.vote.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

public class VoteServletContextListener implements ServletContextListener{
	public void contextInitialized(ServletContextEvent event) {
		
	}
	public void contextDestroyed(ServletContextEvent event) {
		ServletContext ctx=event.getServletContext();
		WebApplicationContext springContext=WebApplicationContextUtils.getWebApplicationContext(ctx);
		Session cassandraSession=springContext.getBean(Session.class);
		Cluster cassandraCluster=springContext.getBean(Cluster.class);
		cassandraSession.close();
		cassandraCluster.close();
		System.out.println("cleared up cassandra session and cluster");
	}
}

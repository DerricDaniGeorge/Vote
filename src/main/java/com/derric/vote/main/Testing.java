package com.derric.vote.main;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

public class Testing {

	public static void main(String[] args) {
		Cluster cluster=Cluster.builder().addContactPoint("127.0.0.1").build();
		Session session=cluster.connect("vote");
		ResultSet rs=session.execute("SELECT * from app_user");
		for(Row row:rs) {
			System.out.println(row.getString("email")+"|"+row.getString("voters_id"));
		}
		session.close();
		cluster.close();
	}

}

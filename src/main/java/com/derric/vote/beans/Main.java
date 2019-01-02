package com.derric.vote.beans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;

public class Main {
	
	
	
	public static void main(String[] args) throws ClassNotFoundException,SQLException {
		Main m=new Main();
		String dbpath="jdbc:mysql://localhost:3306/vote";
		String userName="root";
		String password="admin";
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		BasicDataSource dataSource=new BasicDataSource();
		dataSource.setDriverClassName(com.mysql.cj.jdbc.Driver.class.getName());
		dataSource.setUrl("jdbc:mysql://localhost:3306/vote");
		dataSource.setUsername("root");
		dataSource.setPassword("admin");
		dataSource.setInitialSize(2);
		dataSource.setMaxTotal(5);
		
		Connection conn=dataSource.getConnection();
		PreparedStatement ps=conn.prepareStatement("select * from app_user");
		ResultSet rs=ps.executeQuery();
		System.out.println(rs.getFetchSize());
		
	}
}

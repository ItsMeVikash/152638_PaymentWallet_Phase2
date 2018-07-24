package com.cg_152638.pwp2.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/*
create database parallelproject;
use parallelproject;
CREATE USER 'vikash'@'localhost' IDENTIFIED BY 'admin';
GRANT ALL PRIVILEGES ON * . * TO 'vikash'@'localhost';
FLUSH PRIVILEGES;


CREATE TABLE `customer` (
  `mobilenumber` varchar(10) NOT NULL,
  `name` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `balance` varchar(20) NOT NULL,
  `transactions` longtext,
  PRIMARY KEY (`mobilenumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

 */
public class DataBaseUtil {
	public Connection getConnection() throws ClassNotFoundException, IOException {
		Properties props = new Properties();
		FileInputStream in = new FileInputStream("db.properties");
		props.load(in);
		in.close();
		String driver = props.getProperty("driver");
		if (driver != null) {
			Class.forName(driver);
		}
		String url = props.getProperty("url");
		String username = props.getProperty("username");
		String password = props.getProperty("password");
		Connection con = null;
		try {
			con = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return con;
	}

}
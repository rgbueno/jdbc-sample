package br.com.dio.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConnectionUtil {
	public static Connection getConnetction() throws SQLException {
		return DriverManager.getConnection("jdbc:mysql://localhost:3308/jdbc-sample", "root", "root");
	}

}

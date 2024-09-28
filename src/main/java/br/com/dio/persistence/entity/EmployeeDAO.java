package br.com.dio.persistence.entity;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mysql.cj.jdbc.StatementImpl;

import br.com.dio.persistence.ConnectionUtil;

@Component
public class EmployeeDAO {
	public void insert(final EmployeeEntity entity) {
		try	(
			var connection = ConnectionUtil.getConnetction();
			var statement = connection.createStatement()
				){
			String sql = "INSERT INTO employees (name, salary, birthday) values ('"+ 
				entity.getName() + "'," +
				entity.getSalary().toString() + ",'" +
				entity.getBirthday() + "')"
				;
			statement.executeUpdate(sql);
			System.out.printf("Foram inseridos %s registros na base de dados", statement.getUpdateCount());
			if(statement instanceof StatementImpl impl)
				entity.setId(impl.getLastInsertID());
				
		}catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
	public void update(final EmployeeEntity entity) {
		try	(
			var connection = ConnectionUtil.getConnetction();
			var statement = connection.createStatement()
				){
			String sql = "UPDATE employees SET " + 
			"name = '" + entity.getName() + "'," + 
			"salary = "+ entity.getSalary().toString() + "," + 
			"birthday = '" + formatOffsetDateTime(entity.getBirthday()) + "'" +
			"WHERE id = " + entity.getId();
				
			statement.executeUpdate(sql);
		}catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void delete(final long id) {
		try	(
			var connection = ConnectionUtil.getConnetction();
			var statement = connection.createStatement()
				){
			String sql = "DELETE FROM employees WHERE id =" + id;
			statement.executeUpdate(sql);
		}catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public List<EmployeeEntity> findAll(){
		List<EmployeeEntity> entities = new ArrayList<>();
		try	(
			var connection = ConnectionUtil.getConnetction();
			var statement = connection.createStatement();
				){
			String sql = "SELECT * FROM employees";
			statement.executeQuery(sql);
			var resultSet = statement.getResultSet();
			
			while(resultSet.next()) {
				var entity = new EmployeeEntity();
				entity.setId(resultSet.getLong("id"));
				entity.setName(resultSet.getString("name"));
				entity.setSalary(resultSet.getBigDecimal("salary"));
				var birthDayInstant = resultSet.getTimestamp("birthday").toInstant();
				entity.setBirthday(OffsetDateTime.ofInstant(birthDayInstant, ZoneOffset.UTC));
				entities.add(entity);
			}
				
		}catch (Exception ex) {
			ex.printStackTrace();
		}
		return entities;
	}
	
	public EmployeeEntity findById(final long id) {
		var entity = new EmployeeEntity();
		try	(
				var connection = ConnectionUtil.getConnetction();
				var statement = connection.createStatement();
					){
				String sql = "SELECT * FROM employees WHERE id = "+id;
				statement.executeQuery(sql);
				var resultSet = statement.getResultSet();
				
				if(resultSet.next()) {
					entity.setId(resultSet.getLong("id"));
					entity.setName(resultSet.getString("name"));
					entity.setSalary(resultSet.getBigDecimal("salary"));
					var birthDayInstant = resultSet.getTimestamp("birthday").toInstant();
					entity.setBirthday(OffsetDateTime.ofInstant(birthDayInstant, ZoneOffset.UTC));
				}
					
			}catch (Exception ex) {
				ex.printStackTrace();
			}
		return entity;
	}
	
	private String formatOffsetDateTime(final OffsetDateTime dateTime) {
		var utcDateTime = dateTime.withOffsetSameInstant(ZoneOffset.UTC);
		return utcDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}
}

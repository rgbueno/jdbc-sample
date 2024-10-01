package br.com.dio.persistence.entity;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import org.springframework.stereotype.Component;

import com.mysql.cj.jdbc.StatementImpl;

import br.com.dio.persistence.ConnectionUtil;

@Component
public class EmployeeParamDAO {
	public void insert(final EmployeeEntity entity) {
		try	(
			var connection = ConnectionUtil.getConnetction();
			var statement = connection.prepareStatement("INSERT INTO employees (name, salary, birthday) values (?, ?, ?)");
				){
			statement.setString(1, entity.getName());
			statement.setBigDecimal(2, entity.getSalary());
			statement.setTimestamp(3, Timestamp.valueOf(entity.getBirthday().atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime()));
			statement.executeUpdate();
			if(statement instanceof StatementImpl impl)
				entity.setId(impl.getLastInsertID());
				
		}catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
	public void insertWithProcedure(final EmployeeEntity entity) {
		try	(
			var connection = ConnectionUtil.getConnetction();
			var statement = connection.prepareCall("call prc_insert_employees(?, ?, ?, ?)");
				){
			statement.registerOutParameter(1, TimeZone.LONG);
			statement.setString(2, entity.getName());
			statement.setBigDecimal(3, entity.getSalary());
			statement.setTimestamp(4, Timestamp.valueOf(entity.getBirthday().atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime()));
			statement.execute();
			entity.setId(statement.getLong(1));
			if(statement instanceof StatementImpl impl)
				entity.setId(impl.getLastInsertID());
				
		}catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
	public void update(final EmployeeEntity entity) {
		try	(
			var connection = ConnectionUtil.getConnetction();
			var statement = connection.prepareStatement("UPDATE employees SET name = ?, salary = ?, birthday = ?")
				){
			statement.setString(1, entity.getName());
			statement.setBigDecimal(2, entity.getSalary());
			statement.setTimestamp(3, Timestamp.valueOf(entity.getBirthday().atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime()));
			statement.executeUpdate();
		}catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void delete(final long id) {
		try	(
			var connection = ConnectionUtil.getConnetction();
			var statement = connection.prepareStatement("DELETE FROM employees WHERE id = ?");
				){
			statement.setLong(1, id);
			statement.executeUpdate();
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
				var statement = connection.prepareStatement("SELECT * FROM employees WHERE id = ?");
					){
				
				statement.setLong(1, id);
				statement.executeQuery();
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

package br.com.dio.persistence.entity;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import com.mysql.cj.jdbc.StatementImpl;

import org.springframework.stereotype.Component;

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
		
	}
	
	public void delete(final long id) {
		
	}
	
	public List<EmployeeEntity> findAll(){
		return null;
	}
	
	public EmployeeEntity findById(final long id) {
		return null;
	}
	
	private String formatOffsetDateTime(final OffsetDateTime dateTime) {
		return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}
}

package br.com.dio.persistence.entity;


import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import br.com.dio.persistence.ConnectionUtil;

@Component
public class EmployeeAuditDAO {
	public List<EmployeeAuditEntity> findAll(){
		List<EmployeeAuditEntity> entities = new ArrayList();
		try	(
			var connection = ConnectionUtil.getConnetction();
			var statement = connection.createStatement();
				){
			String sql = "SELECT * FROM view_employees_audit";
			statement.executeQuery(sql);
			var resultSet = statement.getResultSet();
			
			while(resultSet.next()) {
				var birthdayInstant = resultSet.getTimestamp("birthday") == null ? null : resultSet.getTimestamp("birthday").toInstant();
				var oldBirthdayInstant = resultSet.getTimestamp("old_birthday") == null ? null : resultSet.getTimestamp("old_birthday").toInstant();
				
				entities.add(new EmployeeAuditEntity(
						resultSet.getLong("employee_id"),
						resultSet.getString("name"),
						resultSet.getString("old_name"),
						resultSet.getBigDecimal("salary"),
						resultSet.getBigDecimal("old_salary"),
						birthdayInstant != null ? OffsetDateTime.ofInstant(birthdayInstant, ZoneOffset.UTC) : null,
						oldBirthdayInstant != null ? OffsetDateTime.ofInstant(oldBirthdayInstant, ZoneOffset.UTC) : null,
						OperationEnum.getByDbOperation(resultSet.getString("operation"))
						));
			}
				
		}catch (Exception ex) {
			ex.printStackTrace();
		}
		return entities;
	}
	
	
}

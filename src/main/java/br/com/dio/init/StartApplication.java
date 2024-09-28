package br.com.dio.init;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import br.com.dio.persistence.entity.EmployeeDAO;
import br.com.dio.persistence.entity.EmployeeEntity;

@Component
public class StartApplication implements CommandLineRunner {
    @Autowired
    private EmployeeDAO employeeDAO;
    
    @Override
    public void run(String... args) throws Exception {
    	
    	var flyway = Flyway.configure()
        		.dataSource("jdbc:mysql://localhost:3308/jdbc-sample", "root", "root")
        		.load();
        flyway.migrate();
        
        /*
        var employee = new EmployeeEntity();
        employee.setName("Augusto");
        employee.setSalary(new BigDecimal("10000"));
        employee.setBirthday(OffsetDateTime.now().minusYears(8));
        
        System.out.println(employee);
        employeeDAO.insert(employee);
        System.out.println(employee);*/
        
        //employeeDAO.findAll().forEach(System.out::println);
        
        //System.out.println(employeeDAO.findById(1));
        
        /*
        var employee = new EmployeeEntity();
        employee.setId(3);
        employee.setName("Augusto Cravo");
        employee.setSalary(new BigDecimal("15000"));
        employee.setBirthday(OffsetDateTime.now().minusYears(9));

        employeeDAO.update(employee);
        */
        
        employeeDAO.delete(3);
        
    }
}

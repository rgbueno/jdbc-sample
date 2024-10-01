package br.com.dio.init;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Locale;
import java.util.stream.Stream;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import br.com.dio.persistence.entity.EmployeeAuditDAO;
import br.com.dio.persistence.entity.EmployeeDAO;
import br.com.dio.persistence.entity.EmployeeEntity;
import br.com.dio.persistence.entity.EmployeeParamDAO;
import net.datafaker.Faker;

@Component
public class StartApplication implements CommandLineRunner {
    @Autowired
    private EmployeeDAO employeeDAO;
    @Autowired
    private EmployeeParamDAO employeeParamDAO;
    @Autowired
    private EmployeeAuditDAO employeeAuditDAO;
    
    private Faker faker = new Faker(Locale.of("pt", "BR"));
    
    
    
    @SuppressWarnings("deprecation")
	@Override
    public void run(String... args) throws Exception {
    	
    	var flyway = Flyway.configure()
        		.dataSource("jdbc:mysql://localhost:3308/jdbc-sample", "root", "root")
        		.load();
        flyway.migrate();
        
        
        /*
        EmployeeEntity employee = new EmployeeEntity();
        
        employee.setName("Augusto");
        employee.setSalary(new BigDecimal("10000"));
        employee.setBirthday(OffsetDateTime.now().minusYears(8));
        
        System.out.println(employee);
        employeeDAO.insert(employee);
        System.out.println(employee);
        
        //employeeDAO.findAll().forEach(System.out::println);
        
        //System.out.println(employeeDAO.findById(1));
        
        var id = employee.getId();
        employee = new EmployeeEntity();
        employee.setId(id);
        employee.setName("Augusto Cravo");
        employee.setSalary(new BigDecimal("15000"));
        employee.setBirthday(OffsetDateTime.now().minusYears(9));

        employeeDAO.update(employee);
        
        employeeDAO.delete(id);*/
        
        /*
        EmployeeEntity employee = new EmployeeEntity();
        
        employee.setName("Augusto");
        employee.setSalary(new BigDecimal("10000"));
        employee.setBirthday(OffsetDateTime.now().minusYears(8));
        
        employeeParamDAO.insert(employee);
        System.out.println(employee);
        
        employeeParamDAO.findAll().forEach(System.out::println);
        
        var id = employee.getId();
        
        System.out.println(employeeParamDAO.findById(id));
        
        employee = new EmployeeEntity();
        employee.setId(id);
        employee.setName("Augusto Cravo");
        employee.setSalary(new BigDecimal("15000"));
        employee.setBirthday(OffsetDateTime.now().minusYears(9));

        employeeParamDAO.update(employee);
        System.out.println(employee);
        
        employeeParamDAO.delete(id);*/
        
        /*
        EmployeeEntity employee = new EmployeeEntity();
        
        employee.setName("Guto");
        employee.setSalary(new BigDecimal("15000"));
        employee.setBirthday(OffsetDateTime.now().minusYears(15));
        
        employeeParamDAO.insertWithProcedure(employee);*/
        
        //employeeAuditDAO.findAll().forEach(System.out::println);
        
        var entities = Stream.generate(() -> {
        	var employee = new EmployeeEntity();
        	employee.setName(faker.name().fullName());
        	employee.setSalary(new BigDecimal(faker.number().digits(4)));
        	//employee.setBirthday(OffsetDateTime.of(null, null, null) of(faker.timeAndDate().birthday().atTime(LocalTime.MIN), ZoneOffset.UTC));
        	//employee.setBirthday(OffsetDateTime.of(LocalDate.now().minusYears(faker.number().numberBetween(60, 20)), LocalTime.MIN, ZoneOffset.UTC));
        	//employee.setBirthday(OffsetDateTime.now(ZoneOffset.UTC).minusYears(faker.number().numberBetween(18 , 70)).atZoneSimilarLocal(ZoneOffset.UTC).toOffsetDateTime());
        	employee.setBirthday(OffsetDateTime.now(ZoneOffset.UTC));
        	        	
        	return employee;
        }).limit(4000).toList();
        
        employeeParamDAO.insertBatch(entities);
        
    }
}

package br.com.dio.init;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import br.com.dio.persistence.entity.EmployeeDAO;

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
    }
}

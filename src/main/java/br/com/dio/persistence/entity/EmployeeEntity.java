package br.com.dio.persistence.entity;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import lombok.Data;

@Data
public class EmployeeEntity {
	private long id;
	private String name;
	private OffsetDateTime birthday;
	private BigDecimal salary;
}

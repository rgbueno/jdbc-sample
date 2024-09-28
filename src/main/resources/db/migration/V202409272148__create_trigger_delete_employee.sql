DELIMITER $
CREATE TRIGGER tgr_employees_audit_delete AFTER DELETE ON employees
FOR EACH ROW
BEGIN
	INSERT INTO employees_audit (
		employee_id, 
		name, 
		salary, 
		birthday,
		operation
		) 
	VALUES (
		OLD.id, 
		OLD.NAME,
		OLD.salary, 
		OLD.birthday,
		'D'
		);
END $
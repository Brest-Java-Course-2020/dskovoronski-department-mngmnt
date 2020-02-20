DROP TABLE IF EXISTS department;

CREATE TABLE department (
  departmentId INT NOT NULL AUTO_INCREMENT,
  departmentName VARCHAR(255) NOT NULL UNIQUE,
  PRIMARY KEY (departmentId)
);

DROP TABLE IF EXISTS employee;

CREATE TABLE employee (
  employeeId INT NOT NULL AUTO_INCREMENT,
  firstName VARCHAR(255) NOT NULL,
  lastName VARCHAR(255) NOT NULL,
  departmentId int NOT NULL,
  salary int NOT NULL,
  PRIMARY KEY (employeeId)
);

ALTER TABLE employee ADD FOREIGN KEY (departmentId) REFERENCES department(departmentId);


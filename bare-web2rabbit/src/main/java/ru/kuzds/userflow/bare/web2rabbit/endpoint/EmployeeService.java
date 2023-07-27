package ru.kuzds.userflow.bare.web2rabbit.endpoint;

import org.springframework.stereotype.Component;
import ru.kuzds.userflow.bare.web2rabbit.dto.Employee;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlElement;

@Component
@WebService(serviceName = "EmployeeService", portName = "EmployeeServicePort", targetNamespace = "http://soap.myapp.mycompany.com/sample/")
public class EmployeeService {

    @WebResult(name = "version")
    public String wsVersion() {
        return "1.0";
    }

    @WebResult(name = "employee")
    public Employee getEmployeeById(@WebParam(name = "id") @XmlElement(required = true) String id) {
        Employee employee = new Employee();
        employee.setId(id);
        employee.setName("foo");
        return employee;
    }
}

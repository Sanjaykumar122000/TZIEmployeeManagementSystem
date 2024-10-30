package EmployeeService.EmployeeService.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import EmployeeService.EmployeeService.Entity.Employee;
import EmployeeService.EmployeeService.Entity.EmployeePayrollRequest;
import EmployeeService.EmployeeService.Service.EmployeeService;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	// @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
	@GetMapping("/all")
	public List<Employee> getAllEmployees() {
		return employeeService.getAllEmployees();
	}

	// @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
	@GetMapping("/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
		Employee employee = employeeService.getEmployeeById(id);
		return ResponseEntity.ok(employee);
	}

	// @PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/create")
	public ResponseEntity<Employee> createEmployee(@RequestBody EmployeePayrollRequest request) {
		Employee createdEmployee = employeeService.createEmployee(request.getEmployee(), request.getPayroll());
		return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
	}

	// @PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/update/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee updatedEmployee) {
		Employee employee = employeeService.updateEmployee(id, updatedEmployee);
		return new ResponseEntity<>(employee, HttpStatus.OK);
	}

	// @PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
		try {
			employeeService.deleteEmployee(id);
			String message = "Employee deleted with employee ID: " + id;
			return ResponseEntity.ok(message); // Explicitly setting the type to String
		} catch (RuntimeException e) {
			String errorMessage = "Employee not found with ID: " + id;
			return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
		}
	}
}

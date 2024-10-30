package DepartmentService.DepartmentService.Controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import DepartmentService.DepartmentService.Entity.Department;
import DepartmentService.DepartmentService.Service.DepartmentService;

@RestController
@RequestMapping("/api/v1/department")
public class DepartmentController {

	private static final Logger logger = LoggerFactory.getLogger(DepartmentController.class);

	@Autowired
	private DepartmentService departmentService;

	@GetMapping("/getalldepartment")
	public List<Department> getAllDepartments() {
		logger.info("Received request to fetch all departments");
		return departmentService.getAllDepartments();
	}

	@GetMapping("/getdepartment/{id}")
	public ResponseEntity<Department> getDepartmentById(@PathVariable Long id) {
		logger.info("Received request to fetch department with id: {}", id);
		Department department = departmentService.getDepartmentById(id);
		return ResponseEntity.ok(department);

	}

	@PostMapping("/createdepartment")
	public Department createDepartment(@RequestBody Department department) {
		logger.info("Received request to create new department: {}", department);
		return departmentService.createDepartment(department);
	}

	@PutMapping("/updatedepartmentbyid/{id}")
	public ResponseEntity<Department> updateDepartment(@PathVariable Long id,
			@RequestBody Department departmentDetails) {
		logger.info("Received request to update department with id: {}", id);
		Department updatedDepartment = departmentService.updateDepartment(id, departmentDetails);
		return ResponseEntity.ok(updatedDepartment);
	}

	@DeleteMapping("/deletedepartmentbyid/{id}")
	public ResponseEntity<String> deleteDepartment(@PathVariable Long id) {
		logger.info("Received request to delete department with id: {}", id);
		try {
			departmentService.deleteDepartment(id);
			String message = "Department deleted with ID: " + id;
			return ResponseEntity.ok(message); // Return a message confirming deletion
		} catch (RuntimeException e) {
			String errorMessage = "Department not found with ID: " + id;
			return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
		}
	}
}
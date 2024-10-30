package DepartmentService.DepartmentService.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import DepartmentService.DepartmentService.Entity.Department;
import DepartmentService.DepartmentService.Exception.DepartmentNotFoundException;
import DepartmentService.DepartmentService.Repository.DepartmentRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class DepartmentService {

	private static final Logger logger = LoggerFactory.getLogger(DepartmentService.class);

	@Autowired
	private DepartmentRepository departmentRepository;

	public List<Department> getAllDepartments() {
		logger.info("Fetching all departments");
		return departmentRepository.findAll();
	}

	public Department getDepartmentById(Long id) {
		logger.info("Fetching department with id: {}", id);
		return departmentRepository.findById(id)
				.orElseThrow(() -> new DepartmentNotFoundException("Department not found with id: " + id));
	}

	public Department createDepartment(Department department) {
		logger.info("Creating new department: {}", department);
		return departmentRepository.save(department);
	}

	public Department updateDepartment(Long id, Department departmentDetails) {
		logger.info("Updating department with id: {}", id);
		Department department = departmentRepository.findById(id)
				.orElseThrow(() -> new DepartmentNotFoundException("Department not found with id: " + id));

		department.setName(departmentDetails.getName());
		department.setEmail(departmentDetails.getEmail());
		return departmentRepository.save(department);
	}

	public void deleteDepartment(Long id) {
		logger.info("Deleting department with id: {}", id);
		Department department = departmentRepository.findById(id)
				.orElseThrow(() -> new DepartmentNotFoundException("Department not found with id: " + id));
		departmentRepository.delete(department);
	}

}
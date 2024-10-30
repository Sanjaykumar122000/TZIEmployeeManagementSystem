package EmployeeService.EmployeeService.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import DepartmentService.DepartmentService.Entity.Department;
import EmployeeService.EmployeeService.Department_FeignClient;
import EmployeeService.EmployeeService.Payroll_FeignClient;
import EmployeeService.EmployeeService.Entity.Employee;
import EmployeeService.EmployeeService.Exception.EmployeeNotFoundException;
import EmployeeService.EmployeeService.Exception.InvalidDataException;
import EmployeeService.EmployeeService.Repository.EmployeeRepo;
import PayrollService.PayrollService.Entity.Payroll;
import jakarta.transaction.Transactional;

@Service
public class EmployeeService implements Payroll_FeignClient, Department_FeignClient {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

	@Autowired
	private EmployeeRepo employeeRepository;

	@Autowired
	private Department_FeignClient DepartmentClient;

	@Autowired
	private Payroll_FeignClient PayrollClient;

	public List<Employee> getAllEmployees() {
		List<Employee> employees = employeeRepository.findAll();
		List<Department> departments = DepartmentClient.getAllDepartments();
		List<Payroll> payrolls = PayrollClient.getAllPayrolls(); // Fetch payrolls

		// Map departments to a list based on department name
		Map<String, List<Department>> departmentMap = departments.stream()
				.collect(Collectors.groupingBy(Department::getName));

		// Map payrolls to employees based on employee ID
		Map<Long, Payroll> payrollMap = payrolls.stream()
				.collect(Collectors.toMap(Payroll::getEmployeeId, payroll -> payroll));

		// Assign each employee their respective department and payroll details
		employees.forEach(employee -> {
			// Set department details
			List<Department> deptList = departmentMap.get(employee.getDepartment());
			if (deptList != null && !deptList.isEmpty()) {
				employee.setDepartmentDetails(deptList.get(0)); // Set first department found
			}

			// Set payroll details
			Payroll payroll = payrollMap.get(employee.getEmpId());
			if (payroll != null) {
				employee.setPayrollDetails(payroll); // Set payroll details
			}
		});

		return employees;
	}

	public Employee getEmployeeById(Long id) {
		logger.debug("Fetching employee with ID: {}", id);

		// Fetch the employee by ID
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new EmployeeNotFoundException("Employee with ID " + id + " not found"));

		// Fetch all departments and payrolls
		List<Department> departments = DepartmentClient.getAllDepartments();
		List<Payroll> payrolls = PayrollClient.getAllPayrolls();

		// Map departments to a list based on department name
		Map<String, List<Department>> departmentMap = departments.stream()
				.collect(Collectors.groupingBy(Department::getName));

		// Map payrolls to employees based on employee ID
		Map<Long, Payroll> payrollMap = payrolls.stream()
				.collect(Collectors.toMap(Payroll::getEmployeeId, payroll -> payroll));

		// Set department details
		List<Department> deptList = departmentMap.get(employee.getDepartment());
		if (deptList != null && !deptList.isEmpty()) {
			employee.setDepartmentDetails(deptList.get(0)); // Set first department found
		}

		// Get specific payroll for the employee
		Payroll payroll = payrollMap.get(employee.getEmpId());
		if (payroll != null) {
			employee.setPayrollDetails(payroll); // Set payroll details
		} else {
			logger.warn("No payroll found for employee ID: {}", employee.getEmpId());
			// Optionally, you can set a default or null payroll if none found
			employee.setPayrollDetails(null);
		}

		return employee;
	}

	@Transactional
	public Employee createEmployee(Employee employee, Payroll payroll) {
		logger.info("Creating employee: {}", employee);
		if (employee.getEmail() == null || employee.getEmail().isEmpty()) {
			throw new InvalidDataException("Employee email is required");
		}

		// Set the created date explicitly
		employee.setCreatedAt(LocalDateTime.now());

		// Save the employee first
		Employee savedEmployee = employeeRepository.save(employee);

		// Set employee ID in payroll
		payroll.setEmployeeId(savedEmployee.getEmpId());
		// Set payroll details such as pay date, net pay, etc.
		payroll.setPayDate(LocalDateTime.now()); // or any other logic to determine pay date
		payroll.setNetPay(calculateNetPay(payroll.getBaseSalary(), payroll.getBonus(), payroll.getDeductions()));

		// Create the payroll record via the Payroll Feign Client
		ResponseEntity<Payroll> response = PayrollClient.createPayroll(payroll);
		Payroll createdPayroll = response.getBody();

		// Optionally, set the created payroll back to the employee (if needed)
		savedEmployee.setPayrollDetails(createdPayroll);

		return savedEmployee;
	}

	private BigDecimal calculateNetPay(BigDecimal baseSalary, BigDecimal bonus, BigDecimal deductions) {
		return baseSalary.add(bonus != null ? bonus : BigDecimal.ZERO)
				.subtract(deductions != null ? deductions : BigDecimal.ZERO);
	}

	@Transactional
	public Employee updateEmployee(Long id, Employee updatedEmployee) {
		logger.info("Updating employee with ID: {}", id);

		// Find the existing employee by ID
		return employeeRepository.findById(id).map(employee -> {
			// Update employee details
			employee.setFirstName(updatedEmployee.getFirstName());
			employee.setLastName(updatedEmployee.getLastName());
			employee.setEmail(updatedEmployee.getEmail());
			employee.setPhoneNumber(updatedEmployee.getPhoneNumber());
			employee.setPosition(updatedEmployee.getPosition());
			employee.setOnboardingStatus(updatedEmployee.getOnboardingStatus());
			employee.setProfileDetails(updatedEmployee.getProfileDetails());
			employee.setSalary(updatedEmployee.getSalary());
			employee.setRating(updatedEmployee.getRating());
			employee.setUpdatedAt(LocalDateTime.now());

			return employeeRepository.save(employee); // Save updated employee
		}).orElseThrow(() -> new RuntimeException("Employee not found with id " + id));
	}

	public void deleteEmployee(Long id) {
		logger.info("Deleting employee with ID: {}", id);
		if (employeeRepository.existsById(id)) {
			employeeRepository.deleteById(id);
		} else {
			throw new RuntimeException("Employee not found with id " + id);
		}
	}

	@Override
	public List<Department> getAllDepartments() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Payroll> getAllPayrolls() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Payroll> createPayroll(Payroll payroll) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Payroll> updatePayroll(Long id, Payroll payrollDetails) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Payroll> getPayrollByEmployeeId(Long employeeId) {
		// TODO Auto-generated method stub
		return null;
	}

}

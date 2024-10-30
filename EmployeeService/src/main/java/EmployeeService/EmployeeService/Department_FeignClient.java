package EmployeeService.EmployeeService;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import DepartmentService.DepartmentService.Entity.Department;

@FeignClient(name = "DepartmentService")
public interface Department_FeignClient {

	@GetMapping("/api/v1/department/getalldepartment")
	public List<Department> getAllDepartments();

}

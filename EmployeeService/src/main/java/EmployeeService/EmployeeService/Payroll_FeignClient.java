package EmployeeService.EmployeeService;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import PayrollService.PayrollService.Entity.Payroll;

@FeignClient(name = "PayrollService")
public interface Payroll_FeignClient {

	@GetMapping("/api/v1/payrolls/getallpayroll")
	public List<Payroll> getAllPayrolls();

	@GetMapping("/api/v1/payrolls/payrollbyemployee/{employeeId}")
	public ResponseEntity<Payroll> getPayrollByEmployeeId(@PathVariable Long employeeId);

	@PostMapping("/api/v1/payrolls/createpayrollforemployee")
	ResponseEntity<Payroll> createPayroll(@RequestBody Payroll payroll);

	@PutMapping("/api/v1/payrolls/{id}")
	ResponseEntity<Payroll> updatePayroll(@PathVariable Long id, @RequestBody Payroll payrollDetails);

}

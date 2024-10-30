package PayrollService.PayrollService.Controller;

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

import PayrollService.PayrollService.Entity.Payroll;
import PayrollService.PayrollService.Service.PayrollService;

@RestController
@RequestMapping("/api/v1/payrolls")
public class PayrollController {

	private static final Logger logger = LoggerFactory.getLogger(PayrollController.class);

	@Autowired
	private PayrollService payrollService;

	// @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
	@GetMapping("/getallpayroll")
	public List<Payroll> getAllPayrolls() {
		logger.info("Received request to fetch all payrolls");
		return payrollService.getAllPayrolls();
	}

	@GetMapping("/payrollbyemployee/{employeeId}")
	public ResponseEntity<Payroll> getPayrollByEmployeeId(@PathVariable Long employeeId) {
		logger.info("Received request to fetch payroll for employee ID: {}", employeeId);
		return payrollService.getPayrollByEmployeeId(employeeId).map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping("/createpayrollforemployee")
	public ResponseEntity<Payroll> createPayroll(@RequestBody Payroll payroll) {
		logger.info("Received request to create new payroll for employee ID: {}", payroll.getEmployeeId());
		Payroll createdPayroll = payrollService.createPayroll(payroll);
		return ResponseEntity.ok(createdPayroll);
	}

	@PutMapping("/updatepayrollofemployee/{employeeId}")
	public ResponseEntity<Payroll> updatePayroll(@PathVariable Long employeeId, @RequestBody Payroll payrollDetails) {
		logger.info("Received request to update payroll for employee ID: {}", employeeId);
		Payroll updatedPayroll = payrollService.updatePayrollByEmployeeId(employeeId, payrollDetails);
		return ResponseEntity.ok(updatedPayroll);
	}

	@DeleteMapping("/deletepayrollofemployee/{employeeId}")
	public ResponseEntity<String> deletePayroll(@PathVariable Long employeeId) {
		logger.info("Received request to delete payroll for employee ID: {}", employeeId);
		try {
			payrollService.deletePayrollByEmployeeId(employeeId);
			String message = "Payroll deleted for employee with ID: " + employeeId;
			return ResponseEntity.ok(message); // Return confirmation message
		} catch (RuntimeException e) {
			String errorMessage = "Payroll not found for employee ID: " + employeeId;
			return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
		}
	}
}

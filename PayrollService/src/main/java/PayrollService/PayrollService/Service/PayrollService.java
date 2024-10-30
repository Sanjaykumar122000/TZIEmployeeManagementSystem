package PayrollService.PayrollService.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import PayrollService.PayrollService.Entity.Payroll;
import PayrollService.PayrollService.Exception.PayrollNotFoundException;
import PayrollService.PayrollService.Repository.PayrollRepository;

@Service
public class PayrollService {

	private static final Logger logger = LoggerFactory.getLogger(PayrollService.class);

	@Autowired
	private PayrollRepository payrollRepository;

	public List<Payroll> getAllPayrolls() {
		logger.info("Fetching all payrolls");
		return payrollRepository.findAll();
	}

	public Optional<Payroll> getPayrollByEmployeeId(Long employeeId) {
		logger.info("Fetching payroll for employee with ID: {}", employeeId);
		return Optional.ofNullable(payrollRepository.findByEmployeeId(employeeId));
	}

	public Payroll createPayroll(Payroll payroll) {
		logger.info("Creating payroll for employee with ID: {}", payroll.getEmployeeId());

		// Set the net pay and pay date
		payroll.setNetPay(calculateNetPay(payroll.getBaseSalary(), payroll.getBonus(), payroll.getDeductions()));
		payroll.setPayDate(LocalDateTime.now()); // Set the current date and time as the pay date

		// Save the payroll entity to the database
		return payrollRepository.save(payroll);
	}

	private BigDecimal calculateNetPay(BigDecimal baseSalary, BigDecimal bonus, BigDecimal deductions) {
		BigDecimal netPay = baseSalary.add(bonus != null ? bonus : BigDecimal.ZERO)
				.subtract(deductions != null ? deductions : BigDecimal.ZERO);
		return netPay;
	}

	public Payroll updatePayrollByEmployeeId(Long employeeId, Payroll payrollDetails) {
		logger.info("Updating payroll for employee with ID: {}", employeeId);
		Payroll payroll = payrollRepository.findByEmployeeId(employeeId);

		if (payroll == null) {
			throw new PayrollNotFoundException("Payroll not found for employee ID: " + employeeId);
		}

		payroll.setBaseSalary(payrollDetails.getBaseSalary());
		payroll.setBonus(payrollDetails.getBonus());
		payroll.setDeductions(payrollDetails.getDeductions());
		payroll.setNetPay(calculateNetPay(payrollDetails.getBaseSalary(), payrollDetails.getBonus(),
				payrollDetails.getDeductions()));

		return payrollRepository.save(payroll);
	}

	public void deletePayrollByEmployeeId(Long employeeId) {
		logger.info("Deleting payroll for employee with ID: {}", employeeId);
		Payroll payroll = payrollRepository.findByEmployeeId(employeeId);

		if (payroll == null) {
			throw new PayrollNotFoundException("Payroll not found for employee ID: " + employeeId);
		}
		payrollRepository.delete(payroll);
		logger.info("Payroll for employee ID: {} deleted successfully", employeeId);
	}
}

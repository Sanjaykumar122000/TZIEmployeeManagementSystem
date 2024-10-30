package EmployeeService.EmployeeService.Entity;

import PayrollService.PayrollService.Entity.Payroll;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class EmployeePayrollRequest {
	private Employee employee;
	private Payroll payroll;
}

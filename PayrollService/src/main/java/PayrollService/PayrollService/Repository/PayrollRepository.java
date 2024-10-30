package PayrollService.PayrollService.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import PayrollService.PayrollService.Entity.Payroll;

@Repository
public interface PayrollRepository extends JpaRepository<Payroll, Long> {
	Payroll findByEmployeeId(Long employeeId);
}
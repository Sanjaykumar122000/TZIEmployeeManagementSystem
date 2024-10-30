package PayrollService.PayrollService.Entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Generates getters, setters, toString, equals, and hashCode methods
@NoArgsConstructor // Generates a no-args constructor
@AllArgsConstructor // Generates a constructor with all field
@Entity
@Table(name = "payrolls")
public class Payroll {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "employee_id", nullable = false)
	private Long employeeId;

	@Column(name = "base_salary", nullable = false)
	private BigDecimal baseSalary;

	@Column(name = "bonus")
	private BigDecimal bonus;

	@Column(name = "deductions")
	private BigDecimal deductions;

	@Column(name = "net_pay", nullable = false)
	private BigDecimal netPay;

	@Column(name = "pay_date", nullable = false)
	private LocalDateTime payDate;

}
package EmployeeService.EmployeeService.Entity;

import java.time.LocalDateTime;
import org.springframework.data.annotation.LastModifiedBy;

import DepartmentService.DepartmentService.Entity.Department;
import PayrollService.PayrollService.Entity.Payroll;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employees")

public class Employee {

	@Transient
	private Department departmentDetails;

	@Transient
	private Payroll payrollDetails;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "emp_seq_gen")
	@SequenceGenerator(name = "emp_seq_gen", sequenceName = "emp_sequence", initialValue = 10001, allocationSize = 1)
	private Long EmpId;

	@Column(name = "first_name", nullable = false)
	private String firstName;

	@Column(name = "last_name", nullable = false)
	private String lastName;

	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@Column(name = "phone_number")
	private String phoneNumber;

	@Column(name = "position")
	private String position;

	@Column(name = "active", nullable = false)
	private Boolean active = false; // Default to false (inactive)

	@Column(name = "onboarding_status", nullable = false)
	private Boolean onboardingStatus;

	@Column(name = "profile_details", length = 1000)
	private String profileDetails;

	@Column(name = "salary", nullable = false)
	private Double salary; // New field for salary

	@Column(name = "rating", nullable = true)
	private Double rating; // New field for employee rating

	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;

	@LastModifiedBy
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	private String department;

}
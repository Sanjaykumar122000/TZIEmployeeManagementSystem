package DepartmentService.DepartmentService.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import DepartmentService.DepartmentService.Entity.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
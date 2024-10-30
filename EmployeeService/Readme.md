Create Employee
http://localhost:8080/employeeservice/api/v1/employee/create

{
  "employee": {
    "firstName": "sanjay",
    "lastName": "kumar",
    "email": "sanjustefano12@gmail.com",
    "phoneNumber": "6369355323",
    "position": "Software Engineer",
    "active": true,
    "onboardingStatus": true,
    "profileDetails": "New employee in the tech department.",
    "salary": 70000,
    "rating": 4.5,
    "department": "Engineering"
  },
  "payroll": {
    "baseSalary": 70000,
    "bonus": 5000,
    "deductions": 2000
  }
}

GetAllEmployee
http://localhost:8080/employeeservice/api/v1/employee/all

GetEmployeeById
http://localhost:8080/employeeservice/api/v1/employee/10002

UpdateEmployee
http://localhost:8080/employeeservice/api/v1/employee/update/10001

DeleteEmployee
http://localhost:8080/employeeservice/api/v1/employee/delete/10004
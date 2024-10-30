Create Department
http://localhost:8080/departmentservice/api/v1/department/createdepartment
{
    "name": "Engineering",
    "email": "engineeringservices@virtusa.com"
}


GetAllDepartment
http://localhost:8080/departmentservice/api/v1/department/getalldepartment


GetDepartmentById
http://localhost:8080/departmentservice/api/v1/department/getdepartment/100


UpdateDepartment
http://localhost:8080/departmentservice/api/v1/department/updatedepartmentbyid/100
{
    "name": "IT-Service",
    "email": "itservices@virtusa.com"
}

DeleteDepartment
http://localhost:8082/api/v1/department/deletedepartmentbyid/102
GetAllPayroll
http://localhost:8080/payrollservice/api/v1/payrolls/getallpayroll

GetPayRollByEmployeeId
http://localhost:8080/payrollservice/api/v1/payrolls/payrollbyemployee/10001

UpdatePayRollByEmployeeId
http://localhost:8080/payrollservice/api/v1/payrolls/updatepayrollofemployee/10001
{
    "baseSalary": 700000,
    "bonus": 5000,
    "deductions": 2000,
    "netPay": 103000,
    "payDate": "2024-10-29T12:50:04.923335"
}

DeletePayRollByEmployeeId
http://localhost:8080/payrollservice/api/v1/payrolls/deletepayrollofemployee/10003



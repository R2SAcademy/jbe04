public class EmployeeManagement {

  public static void main(String[] args) {
    Employee firstEmp = new Employee("C133", "Minh", 2000, "1 Ba Trieu");
    Employee secondEmp = new Employee(); // creating an instance of Employee class
    System.out.println(firstEmp.getCode());
    firstEmp.setCode("C134"); // setting code for first employee
//    employee.input(); // calling input method to get employee details
  }

}

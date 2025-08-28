package polymorphism;

public class FulltTimeEmployee extends Employee {

  public FulltTimeEmployee(String name, double baseSalary) {
    super(name, baseSalary);
  }

  @Override
  public double calculateSalary() {
    return baseSalary;
  }
}

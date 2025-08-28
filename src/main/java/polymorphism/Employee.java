package polymorphism;

public abstract class Employee {
  protected String name;
  protected double baseSalary;

  public Employee(String name) {
    this.name = name;
  }

  public Employee(String name, double baseSalary) {
    this.name = name;
    this.baseSalary = baseSalary;
  }

  public abstract double calculateSalary();
}

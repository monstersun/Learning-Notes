package com.company.IOLearning;


import java.io.*;
import java.time.LocalDate;

public class Employee implements Serializable {
    private String name;
    private Double salary;
    private String birthDay;
    private LocalDate hireDay;

    public Employee() {
    }

    public Employee(String name, Double salary, int year, int month, int day) {
        this.name = name;
        this.salary = salary;
        hireDay = LocalDate.of(year, month, day);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public LocalDate getHireDay() {
        return hireDay;
    }

    public void setHireDay(LocalDate hireDay) {
        this.hireDay = hireDay;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", salary=" + salary +
                ", hireDay='" + hireDay + '\'' +
                '}';
    }

    public static Employee[] generateTestStaff() {
        Employee[] staff = new Employee[3];
        staff[0] = new Employee("Carl Cracker", 75000.0, 1987, 12, 15);
        staff[1] = new Employee("Harry Hacker", 50000.0, 1989, 10, 1);
        staff[2] = new Employee("Tony Tester", 40000.0, 1990, 1, 15);
        for (Employee e : staff) {
            System.out.println(e);
        }
        return staff;
    }

    public Employee serialClone() {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        try (ObjectOutputStream out = new ObjectOutputStream(bout)) {
            out.writeObject(this);
        } catch (IOException e) {
            System.out.println("IO Exception");
            return null;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bout.toByteArray()))) {
            return (Employee) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("IO, ClassNotFound");
            return null;
        } finally {
            try {
                bout.close();
            } catch (IOException e) {
                System.out.println("close io exception");
            }
        }
    }
}

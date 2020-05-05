package com.company.IOLearning;

public class Manager extends Employee {
    private Employee secretary;

    public Manager(String name, Double salary, int year, int month, int day) {
        super(name, salary, year, month, day);
    }

    public Employee getSecretary() {
        return secretary;
    }

    public void setSecretary(Employee secretary) {
        this.secretary = secretary;
    }

}

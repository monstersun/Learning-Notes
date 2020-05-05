package com.company.IOLearning;

public class SerialCloneTest {
    public static void main(String[] args) {
        Employee harry = new Employee("Harry Hacker",
                50000.0, 1989, 10, 1);
        Employee harry1 = harry.serialClone();
        System.out.println(harry == harry1);
        System.out.println(harry1);
        System.out.println(harry);
    }
}

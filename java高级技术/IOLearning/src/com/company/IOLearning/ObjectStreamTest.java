package com.company.IOLearning;

import java.io.*;

public class ObjectStreamTest {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Employee harry = new Employee("Harry Hacker",
                50000.0, 1989, 10, 1);
        Manager carl = new Manager("Carl Cracker", 80000.0, 1987, 12, 15);
        Manager tony = new Manager("Tony Tester", 40000.0, 1990, 3, 15);
        Employee[] staff = new Employee[3];
        staff[0] = carl;
        staff[1] = tony;
        staff[2] = harry;
        for (Employee e : staff) {
            System.out.println(e);
        }
        try (ObjectOutputStream oops = new ObjectOutputStream(
                new FileOutputStream("employee2.dat"))) {
            oops.writeObject(staff);
        }

        try (ObjectInputStream oips = new ObjectInputStream(
                new FileInputStream("employee2.dat"))) {
            Employee[] staffIn = (Employee[]) oips.readObject();
            for (Employee e : staffIn) {
                System.out.println(e);
            }
        }
    }
}

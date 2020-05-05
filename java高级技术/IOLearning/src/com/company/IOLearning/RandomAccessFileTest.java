package com.company.IOLearning;

import java.io.*;
import java.time.LocalDate;

public class RandomAccessFileTest {

    private static final int RECORD_SIZE = 40 * 2 + 8 + 3 * 4;

    public static void main(String[] args) throws IOException {
        Employee[] staff = new Employee[3];
        staff[0] = new Employee("Carl Cracker", 75000.0, 1987, 12, 15);
        staff[1] = new Employee("Harry Hacker", 50000.0, 1989, 10, 1);
        staff[2] = new Employee("Tony Tester", 40000.0, 1990, 1, 15);
        for (Employee e : staff) {
            System.out.println(e);
        }

        try (DataOutputStream dops = new DataOutputStream(new FileOutputStream("e.dat"))) {
            for (Employee e : staff) {
                writeData(dops, e);
            }
        }

        try (RandomAccessFile randomAccessFile = new RandomAccessFile("e.dat", "r")) {
            Employee[] employees = readStaff(randomAccessFile);
            System.out.println(employees);
            for (Employee e : employees) {
                System.out.println(e);
            }
        }
    }

    private static void writeData(DataOutput dataOutput, Employee e) throws IOException {
        LocalDate hireDay = e.getHireDay();
        int year = hireDay.getYear();
        int month = hireDay.getMonthValue();
        int dayOfMonth = hireDay.getDayOfMonth();
        writeFixedString(e.getName(), dataOutput, 40);
        dataOutput.writeDouble(e.getSalary());
        dataOutput.writeInt(year);
        dataOutput.writeInt(month);
        dataOutput.writeInt(dayOfMonth);

    }

    private static void writeFixedString(String s, DataOutput dataOutput, int size) throws IOException {
        int len = s.length();
        for (int i = 0; i < size; i++) {
            char ch = 0;
            if (i < len) {
                ch = s.charAt(i);
            }
            dataOutput.writeChar(ch);
        }
    }

    public static Employee[] readStaff(RandomAccessFile randomAccessFile) throws IOException {
        int n = (int) (randomAccessFile.length() / RECORD_SIZE);
        Employee[] staff = new Employee[n];
        for (int i = 0; i < n; i++) {
            randomAccessFile.seek(i * RECORD_SIZE);
            staff[i] = readData(randomAccessFile);
        }
        return staff;
    }

    private static Employee readData(DataInput dataInput) throws IOException {
        String name = readFixedString(dataInput, 40);
        double salary = dataInput.readDouble();
        int year = dataInput.readInt();
        int month = dataInput.readInt();
        int day = dataInput.readInt();
        return new Employee(name, salary, year, month, day);
    }

    private static String readFixedString(DataInput dataInput, int size) throws IOException {
        StringBuilder sb = new StringBuilder(size);
        int i = 0;
        boolean more = true;
        while (more && i < size) {
            char ch = dataInput.readChar();
            i++;
            if (ch == 0) {
                more = false;
            } else {
                sb.append(ch);
            }
        }
        dataInput.skipBytes(2 * (size - i));
        return sb.toString();
    }
}

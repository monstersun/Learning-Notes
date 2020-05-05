package com.company.IOLearning;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.Format;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

public class TextFileTest {
    private static int EMPLOYEE_RECORD_NUM = 3;

    public static void main(String[] args) {
        Employee[] staff = new Employee[3];
        staff[0] = new Employee("Carl Cracker", 75000.0, 1987, 12, 15);
        staff[1] = new Employee("Harry Hacker", 50000.0, 1989, 10, 1);
        staff[2] = new Employee("Tony Tester", 40000.0, 1990, 3, 15);
        try (PrintWriter out = new PrintWriter("employee.dat", "UTF-8")) {
            writeData(staff, out);
        } catch (IOException e) {
            System.out.println("IO has some problem");
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("employee.dat"), StandardCharsets.UTF_8))) {
            staff = readData(reader);
            System.out.println(Arrays.toString(staff));
        } catch (IOException e) {
            System.out.println("IO has some problem");
        }
    }

    public static void writeData(Employee[] staff, PrintWriter writer) {
        for (Employee employee : staff) {
            writeEmployee(writer, employee);
        }
    }

    private static void writeEmployee(PrintWriter writer, Employee employee) {
        Format decimalFormat = new DecimalFormat("0.00");
        Double salary = employee.getSalary();
        String employeeInfo = employee.getName() +
                "|" +
                decimalFormat.format(salary) +
                "|" +
                employee.getHireDay();
        writer.println(employeeInfo);
    }

    private static Employee[] readData(BufferedReader reader) {
        return (Employee[]) reader.lines().map(TextFileTest::record2Employee).
                filter(Objects::nonNull).toArray();
    }

    private static Employee record2Employee(String line) {
        String[] split = line.split("\\|");
        if (split.length == EMPLOYEE_RECORD_NUM) {
            Integer[] birthParams = (Integer[]) Stream.of(split[2].split("-")).map(Integer::parseInt).toArray();
            if (birthParams.length == EMPLOYEE_RECORD_NUM) {
                return new Employee(split[0], Double.parseDouble(split[1]),
                        birthParams[0], birthParams[1], birthParams[2]);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}

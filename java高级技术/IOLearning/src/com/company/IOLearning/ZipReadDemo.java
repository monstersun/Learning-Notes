package com.company.IOLearning;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class ZipReadDemo {

    private static final int SIZE = 1024;

    public static void main(String[] args) throws IOException, ClassNotFoundException {

//        try (ZipInputStream zis = new ZipInputStream(new FileInputStream("test.zip"))) {
//            ZipEntry zipEntry = null;
//            int count = 0;
//            while ((zipEntry = zis.getNextEntry()) != null) {
//                ObjectInputStream objectInputStream = new ObjectInputStream(zis);
//                Employee[] staff = (Employee[]) objectInputStream.readObject();
//                staffPrint(staff, ++count);
//            }
//        }

        FileSystem fs = FileSystems.newFileSystem(Paths.get("test.zip"), null);
        Path file = fs.getPath("/");
        List<InputStream> allZipStream = new ArrayList<>();
        Files.walkFileTree(file, new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (!Files.isDirectory(file)){
                    allZipStream.add(Files.newInputStream(file));
                }
                return FileVisitResult.CONTINUE;
            }
        });
        int count = 0;
        for (InputStream zis : allZipStream){
            ObjectInputStream objectInputStream = new ObjectInputStream(zis);
            Employee[] staff = (Employee[]) objectInputStream.readObject();
            staffPrint(staff, ++count);
        }
    }

    public static void staffPrint(Employee[] staff, int count) {
        if (null != staff && staff.length != 0) {
            System.out.println("this is " + count + "times!");
            for (Employee e : staff) {
                System.out.println(e);
            }
        }
    }
}


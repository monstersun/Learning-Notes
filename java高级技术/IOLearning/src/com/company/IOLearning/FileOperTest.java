package com.company.IOLearning;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class FileOperTest {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
//        Path src = Paths.get("src");
//        Path e1 = Paths.get("e1.dat");
//        Path e2 = Paths.get("e2.dat");
//        Path e5 = Paths.get("e5.dat");
//        System.out.println(Files.isDirectory(src));
//        System.out.println(Files.isDirectory(e2));
//        Files.copy(e1, e5);
//        ObjectInputStream objectInputStream = new ObjectInputStream(Files.newInputStream(e5));
//        Employee[] staff = (Employee[]) objectInputStream.readObject();
//        ZipReadDemo.staffPrint(staff, 0);
//        Files.delete(e5);
//        Path fileName = e1.getFileName();
//        System.out.println(fileName);
//        Path root = e1.getRoot();
//        System.out.println(root);
//        Path path = e1.toAbsolutePath();
//        System.out.println(path);
//        Path relativize = e1.relativize(e2);
//        System.out.println(relativize);
        Path test = Paths.get("test.zip");
        String target = test.toAbsolutePath().getParent().toString()+ File.separator;
        FileSystem fs = FileSystems.newFileSystem(test, null);
        Path file = fs.getPath("/");
        Files.walkFileTree(file, new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Path targetObj = Paths.get(target + file.toAbsolutePath());
                Files.copy(file, targetObj, StandardCopyOption.REPLACE_EXISTING);
                return FileVisitResult.CONTINUE;
            }
        });
    }
}

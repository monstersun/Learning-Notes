package com.company.IOLearning;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLOutput;
import java.util.Random;
import java.util.zip.CRC32;

public class MemoryFileTest {
    public static long checkSumInputStream(Path fileName) throws IOException {
        CRC32 crc = new CRC32();
        try (InputStream is = Files.newInputStream(fileName)) {
            int n;
            while ((n = is.read()) != -1) {
                crc.update(n);
            }
            return crc.getValue();
        }
    }

    public static long checkSumBufferedStream(Path fileName) throws IOException {
        CRC32 crc = new CRC32();
        try (BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(fileName))) {
            int n;
            while ((n = bis.read()) != -1) {
                crc.update(n);
            }
        }
        return crc.getValue();
    }

    public static long checkSumRandomAccessFile(Path fileName) throws IOException {
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(fileName.toFile(), "r")) {
            long len = randomAccessFile.length();
            CRC32 crc32 = new CRC32();
            for (long p = 0; p < len; p++) {
                randomAccessFile.seek(p);
                crc32.update(randomAccessFile.readByte());
            }
            return crc32.getValue();
        }
    }

    public static long checkSumMemoryTest(Path fileName) throws IOException {
        try (FileChannel channel = FileChannel.open(fileName)) {
            CRC32 crc32 = new CRC32();
            int len = (int) channel.size();
            MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, len);
            for (int p = 0; p < len; p++) {
                int c = buffer.get(p);
                crc32.update(c);
            }
            return crc32.getValue();
        }
    }

    public static void main(String[] args) throws IOException {
//        Path path = Paths.get("1.txt");
//        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
//            for (int i = 0;i < 100000;i++){
//                writer.write("test long file!!!!");
//                writer.newLine();
//            }
//            writer.flush();
//        }
        Path path = Paths.get("1.txt");
        long start = System.currentTimeMillis();
        System.out.println("crc:" + checkSumInputStream(path));
        long end = System.currentTimeMillis();
        System.out.println((end - start) + "secs");

        start = System.currentTimeMillis();
        System.out.println("crc:" + checkSumBufferedStream(path));
        end = System.currentTimeMillis();
        System.out.println((end - start) + "secs");

        start = System.currentTimeMillis();
        System.out.println("crc:" + checkSumRandomAccessFile(path));
        end = System.currentTimeMillis();
        System.out.println((end - start) + "secs");

        start = System.currentTimeMillis();
        System.out.println("crc:" + checkSumMemoryTest(path));
        end = System.currentTimeMillis();
        System.out.println((end - start) + "secs");

    }
}

package com.webui.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.NoSuchFileException;

public class ParseJsonFile {

    public static String readJsonFile(String file) {
        try {
            File fi = new File(file);
            long length = fi.length();
            byte[] byt = new byte[Long.valueOf(length).intValue()];
            FileInputStream fileInputStream = new FileInputStream(fi);
            fileInputStream.read(byt);
            fileInputStream.close();
            return new String(byt, StandardCharsets.UTF_8);
        } catch (NoSuchFileException e) {
            System.out.println("找不到文件");
        } catch (IOException e) {
            //
        }
        return null;

    }

}

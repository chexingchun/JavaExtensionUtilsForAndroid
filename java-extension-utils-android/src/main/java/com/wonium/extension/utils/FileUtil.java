/*
 * Copyright  2018  wonium
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wonium.extension.utils;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


/**
 *@date  2018/2/24
 *@author Wonium
 *@description File工具类 主要封装了一些对文件读写的操作
 *@version v1.0.0
 *@Modify Cyuan.Huang
 *@ModifyDescription
 *@ModifyDate 2018/7/19
 **/
public enum  FileUtil {
    /**
     * 实例对象
     */
    INSTANCE;

    /**
     * 分隔符.
     */

    public final static String FILE_EXTENSION_SEPARATOR = ".";

    /**
     * "/"
     */

    public final static String SEPARATOR = File.separator;


    /**
     * SD卡根目录
     */

    public static final String SD_PATH = Environment.getExternalStorageDirectory() + File.separator;



    /**
     * 读取文本数据
     * @param context  程序上下文
     * @param fileName 文件名
     * @return String, 读取到的文本内容，失败返回null
     */
    public  String readAssetsFile(Context context, String fileName) {
        InputStream is = null;
        String content = null;
        try
        {
            is = context.getAssets().open(fileName);

            byte[] buffer = new byte[1024];
            ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
            while (true)
            {
                int readLength = is.read(buffer);
                if (readLength == -1) {
                    break;
                }
                arrayOutputStream.write(buffer, 0, readLength);
            }
            is.close();
            arrayOutputStream.close();
            content = new String(arrayOutputStream.toByteArray());

        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            content = null;
        }
        finally
        {
            try
            {
                if (is != null) {
                    is.close();
                }
            }
            catch (IOException ioe)
            {
                ioe.printStackTrace();
            }
        }
        return content;
    }

    /**
     * 判断SD卡是否可用
     *
     * @return SD卡可用返回true
     */
    public  boolean hasSdcard() {
        String status = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(status);
    }

    /**
     * 生成文件路径
     * @param dir 文件
     * @param name
     * @return
     */
    public  String generateFilePath(String dir,String name,String uffix){

         String filePath = SEPARATOR + dir+ SEPARATOR+name+uffix;
         if (!FileUtil.INSTANCE.isFileExist(filePath)){
             File file =new File(filePath);
             File fileDir =file.getParentFile();
             if (!fileDir.exists()){
                 fileDir.mkdirs();
             }

         }
        return filePath;
    }


    /**
     * 读取文件的内容
     * <br>
     * 默认utf-8编码
     *
     * @param filePath 文件路径
     * @return 字符串
     * @throws IOException
     */
    public  String readFile(String filePath) throws IOException {
        return readFile(filePath, "utf-8");
    }



    public  byte[] readFileToByte(String path){
        byte[] data=null;
        try {
            InputStream inputStream =new FileInputStream(path);
            data =toByteArray(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  data;
    }

    public  byte[] toByteArray(InputStream in) throws IOException {
        ByteArrayOutputStream bos=new ByteArrayOutputStream();
        byte[] buffer=new byte[1024*4];
        int n;
        while ((n=in.read(buffer))!=-1){
            bos.write(buffer,0,n);
        }
        return bos.toByteArray();
    }

    /**
     * 读取文件的内容
     *
     * @param filePath    文件目录
     * @param charsetName 字符编码
     * @return String字符串
     */
    public  String readFile(String filePath, String charsetName) throws IOException {
        if (TextUtils.isEmpty(filePath)){
            return null;
        }

        if (TextUtils.isEmpty(charsetName)){
            charsetName = "utf-8";
        }

        File file = new File(filePath);
        StringBuilder fileContent = new StringBuilder();
        if (!file.isFile()){
            return null;
        }

        BufferedReader reader = null;
        try {
            InputStreamReader is = new InputStreamReader(new FileInputStream(file), charsetName);
            reader = new BufferedReader(is);
            String line;
            while ((line = reader.readLine()) != null) {
                if (!"".equals(fileContent.toString())) {
                    fileContent.append("\r\n");
                }
                fileContent.append(line);
            }
            return fileContent.toString();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 读取文本文件到List字符串集合中(默认utf-8编码)
     *
     * @param filePath 文件目录
     * @return 文件不存在返回null，否则返回字符串集合
     * @throws IOException
     */
    public  List<String> readFileToList(String filePath) throws IOException {
        return readFileToList(filePath, "utf-8");
    }

    /**
     * 读取文本文件到List字符串集合中
     *
     * @param filePath    文件目录
     * @param charsetName 字符编码
     * @return 文件不存在返回null，否则返回字符串集合
     */
    public  List<String> readFileToList(String filePath, String charsetName) throws IOException {
        if (TextUtils.isEmpty(filePath)){
            return null;
        }

        if (TextUtils.isEmpty(charsetName)){
            charsetName = "utf-8";
        }

        File file = new File(filePath);
        List<String> fileContent = new ArrayList<>();
        if (!file.isFile()) {
            return null;
        }
        BufferedReader reader = null;
        try {
            InputStreamReader is = new InputStreamReader(new FileInputStream(file), charsetName);
            reader = new BufferedReader(is);
            String line;
            while ((line = reader.readLine()) != null) {
                fileContent.add(line);
            }
            return fileContent;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 向文件中写入数据
     * @param filePath 文件目录
     * @param content  要写入的内容
     * @param append   如果为 true，则将数据写入文件末尾处，而不是写入文件开始处
     * @return 写入成功返回true， 写入失败返回false
     * @throws IOException
     */
    public  boolean writeFile(String filePath, String content, boolean append) throws IOException {
        if (TextUtils.isEmpty(filePath)){
            return false;
        }

        if (TextUtils.isEmpty(content)){
            return false;
        }

        FileWriter fileWriter = null;
        try {
            createFile(filePath);
            fileWriter = new FileWriter(filePath, append);
            fileWriter.write(content);
            fileWriter.flush();
            return true;
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 向文件中写入数据<br>
     * 默认在文件开始处重新写入数据
     * @param filePath 文件目录
     * @param stream   字节输入流
     * @return 写入成功返回true，否则返回false
     * @throws IOException
     */
    public  boolean writeFile(String filePath, InputStream stream) throws IOException {
        return writeFile(filePath, stream, false);
    }

    /**
     * 向文件中写入数据
     * @param filePath 文件目录
     * @param stream   字节输入流
     * @param append   如果为 true，则将数据写入文件末尾处；
     *                 为false时，清空原来的数据，从头开始写
     * @return 写入成功返回true，否则返回false
     * @throws IOException
     */
    public  boolean writeFile(String filePath, InputStream stream, boolean append) throws IOException {
        if (TextUtils.isEmpty(filePath)){
            throw new NullPointerException("filePath is Empty");
        }
        if (stream == null){
            throw new NullPointerException("InputStream is null");
        }
        return writeFile(new File(filePath), stream, append);
    }

    /**
     * 文件写加内容
     * @param filePath
     * @param data 数据内容
     * @return file path
     */
    public  String writeFile(String filePath ,byte[] data) {

        FileOutputStream fos;
        try {
            fos = new FileOutputStream(filePath,false);
            fos.write(data);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filePath;
    }
    /**
     * 向文件中写入数据
     * 默认在文件开始处重新写入数据
     * @param file   指定文件
     * @param stream 字节输入流
     * @return 写入成功返回true，否则返回false
     * @throws IOException
     */
    public  boolean writeFile(File file, InputStream stream) throws IOException {
        return writeFile(file, stream, false);
    }

    /**
     * 向文件中写入数据
     *
     * @param file   指定文件
     * @param stream 字节输入流
     * @param append 为true时，在文件开始处重新写入数据；
     *               为false时，清空原来的数据，从头开始写
     * @return 写入成功返回true，否则返回false
     * @throws IOException
     */
    public  boolean writeFile(File file, InputStream stream, boolean append) throws IOException {
        if (file == null){
            throw new NullPointerException("file = null");
        }

        OutputStream out = null;
        try {
            createFile(file.getAbsolutePath());
            out = new FileOutputStream(file, append);
            byte[] data = new byte[1024];
            int length;
            while ((length = stream.read(data)) != -1) {
                out.write(data, 0, length);
            }
            out.flush();
            return true;
        } finally {
            if (out != null) {
                try {
                    out.close();
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public  boolean copyFile(InputStream inputStream, String destFilePath) throws IOException {
        return writeFile(destFilePath, inputStream);
    }

    /**
     * 复制文件
     *
     * @param sourceFilePath 源文件目录（要复制的文件目录）
     * @param destFilePath   目标文件目录（复制后的文件目录）
     * @return 复制文件成功返回true，否则返回false
     * @throws IOException
     */
    public  boolean copyFile(String sourceFilePath, String destFilePath) throws IOException {
        InputStream inputStream;
        inputStream = new FileInputStream(sourceFilePath);
        return writeFile(destFilePath, inputStream);
    }


    /**
     * 获取某个目录下的文件名
     *
     * @param dirPath    目录
     * @param fileFilter 过滤器
     * @return 某个目录下的所有文件名
     */
    public  List<String> getFileNameList(String dirPath, FilenameFilter fileFilter) {
        if (fileFilter == null){
            return getFileNameList(dirPath);
        }

        if (TextUtils.isEmpty(dirPath)){
            return Collections.emptyList();
        }

        File dir = new File(dirPath);

        File[] files = dir.listFiles(fileFilter);
        if (files == null){
            return Collections.emptyList();
        }


        List<String> conList = new ArrayList<>();
        for (File file : files) {
            if (file.isFile()){
                conList.add(file.getName());
            }
        }
        return conList;
    }

    /**
     * 获取某个目录下的文件名
     *
     * @param dirPath 目录
     * @return 某个目录下的所有文件名
     */
    public  List<String> getFileNameList(String dirPath) {
        if (TextUtils.isEmpty(dirPath)){
            return Collections.emptyList();
        }

        File dir = new File(dirPath);
        File[] files = dir.listFiles();
        if (files == null){
            return Collections.emptyList();
        }

        List<String> conList = new ArrayList<>();
        for (File file : files) {
            if (file.isFile()){
                conList.add(file.getName());
            }
        }
        return conList;
    }

    /**
     * 获取某个目录下的指定扩展名的文件名称
     *
     * @param dirPath 目录
     * @return 某个目录下的所有文件名
     */
    public  List<String> getFileNameList(String dirPath, final String extension) {
        if (TextUtils.isEmpty(dirPath)){
            return Collections.emptyList();
        }

        File dir = new File(dirPath);
        File[] files = dir.listFiles((dir1, filename) -> filename.indexOf("." + extension) > 0);
        if (files == null){
            return Collections.emptyList();
        }

        List<String> conList = new ArrayList<>();
        for (File file : files) {
            if (file.isFile()){
                conList.add(file.getName());
            }

        }
        return conList;
    }

    /**
     * 获得文件的扩展名
     *
     * @param filePath 文件路径
     * @return 如果没有扩展名，返回""
     */
    public  String getFileExtension(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return filePath;
        }
        int extenPosi = filePath.lastIndexOf(FILE_EXTENSION_SEPARATOR);
        int filePosi = filePath.lastIndexOf(File.separator);
        if (extenPosi == -1) {
            return "";
        }
        return (filePosi >= extenPosi) ? "" : filePath.substring(extenPosi + 1);
    }

    /**
     * 创建文件
     *
     * @param path 文件的绝对路径
     * @return
     */
    public  boolean createFile(String path) {
        if (TextUtils.isEmpty(path)){
            return false;
        }

        return createFile(new File(path));
    }

    /**
     * 创建文件
     *
     * @param file
     * @return 创建成功返回true
     */
    public  boolean createFile(File file) {
        if (file == null || !makeDirs(getFolderName(file.getAbsolutePath()))) {
            return false;
        }
        if (!file.exists()) {
            try {
                return file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    /**
     * 创建目录（可以是多个）
     *
     * @param filePath 目录路径
     * @return 如果路径为空时，返回false；如果目录创建成功，则返回true，否则返回false
     */
    public  boolean makeDirs(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return false;
        }
        File folder = new File(filePath);
        return (folder.exists() && folder.isDirectory()) || folder.mkdirs();
    }

    /**
     * 创建目录（可以是多个）
     *
     * @param dir 目录
     * @return 如果目录创建成功，则返回true，否则返回false
     */
    public  boolean makeDirs(File dir) {
        if (dir == null) {
            return false;
        }
        return (dir.exists() && dir.isDirectory()) || dir.mkdirs();
    }

    /**
     * 判断文件是否存在
     *
     * @param filePath 文件路径
     * @return 如果路径为空或者为空白字符串，就返回false；如果文件存在，且是文件，
     * 就返回true；如果不是文件或者不存在，则返回false
     */
    public  boolean isFileExist(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return false;
        }
        File file = new File(filePath);
        return file.exists() && file.isFile();
    }

    /**
     * 获得不带扩展名的文件名称
     *
     * @param filePath 文件路径
     * @return
     */
    public  String getFileNameWithoutExtension(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return filePath;
        }
        int extenPosi = filePath.lastIndexOf(FILE_EXTENSION_SEPARATOR);
        int filePosi = filePath.lastIndexOf(File.separator);
        if (filePosi == -1) {
            return (extenPosi == -1 ? filePath : filePath.substring(0,
                    extenPosi));
        }
        if (extenPosi == -1) {
            return filePath.substring(filePosi + 1);
        }
        return (filePosi < extenPosi ? filePath.substring(filePosi + 1,
                extenPosi) : filePath.substring(filePosi + 1));
    }

    /**
     * 获得文件名
     *
     * @param filePath 文件路径
     * @return 如果路径为空或空串，返回路径名；不为空时，返回文件名
     */
    public  String getFileName(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return filePath;
        }
        int filePosi = filePath.lastIndexOf(File.separator);
        return (filePosi == -1) ? filePath : filePath.substring(filePosi + 1);
    }

    /**
     * 获得所在目录名称
     * @param filePath 文件的绝对路径
     * @return 如果路径为空或空串，返回路径名；不为空时，如果为根目录，返回"";
     * 如果不是根目录，返回所在目录名称，格式如：C:/Windows/Boot
     */
    public  String getFolderName(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return filePath;
        }
        int filePosi = filePath.lastIndexOf(File.separator);
        return (filePosi == -1) ? "" : filePath.substring(0, filePosi);
    }

    /**
     * 判断目录是否存在
     *
     * @param //directoryPath目录路径
     * @return 如果路径为空或空白字符串，返回false；如果目录存在且，确实是目录文件夹，
     * 返回true；如果不是文件夹或者不存在，则返回false
     */
    public  boolean isFolderExist(String directoryPath) {
        if (TextUtils.isEmpty(directoryPath)) {
            return false;
        }
        File dire = new File(directoryPath);
        return (dire.exists() && dire.isDirectory());
    }

    /**
     * 删除指定文件或指定目录内的所有文件
     *
     * @param path 文件或目录的绝对路径
     * @return 路径为空或空白字符串，返回true；文件不存在，返回true；文件删除返回true；
     * 文件删除异常返回false
     */
    public  boolean deleteFile(String path) {
        if (TextUtils.isEmpty(path)) {
            return true;
        }
        return deleteFile(new File(path));
    }

    /**
     * 删除指定文件或指定目录内的所有文件
     *
     * @param file
     * @return 路径为空或空白字符串，返回true；文件不存在，返回true；文件删除返回true；
     * 文件删除异常返回false
     */
    public  boolean deleteFile(File file) {
        if (file == null) {
            throw new NullPointerException("file is null");
        }
        if (!file.exists()) {
            return true;
        }
        if (file.isFile()) {
            return file.delete();
        }
        if (!file.isDirectory()) {
            return false;
        }

        File[] files = file.listFiles();
        if (files == null) {
            return true;
        }
        for (File f : files) {
            if (f.isFile()) {
                f.delete();
            } else if (f.isDirectory()) {
                deleteFile(f.getAbsolutePath());
            }
        }
        return file.delete();
    }

    /**
     * 删除指定目录中特定的文件
     *
     * @param dir
     * @param filter
     */
    public  void delete(String dir, FilenameFilter filter) {
        if (TextUtils.isEmpty(dir)) {
            return;
        }
        File file = new File(dir);
        if (!file.exists()) {
            return;
        }
        if (file.isFile()) {
            file.delete();
        }
        if (!file.isDirectory()) {
            return;
        }

        File[] lists;
        if (filter != null) {
            lists = file.listFiles(filter);
        } else {
            lists = file.listFiles();
        }

        if (lists == null) {
            return;
        }
        for (File f : lists) {
            if (f.isFile()) {
                f.delete();
            }
        }
    }

    /**
     * 获得文件或文件夹的大小
     *
     * @param path 文件或目录的绝对路径
     * @return 返回当前目录的大小 ，注：当文件不存在，为空，或者为空白字符串，返回 -1
     */
    public  long getFileSize(String path) {
        if (TextUtils.isEmpty(path)) {
            return -1;
        }
        File file = new File(path);
        return (file.exists() && file.isFile() ? file.length() : -1);
    }





    public  boolean checkFileExists(String path) {
        File file = new File(path);
        return file.exists();
    }


    public  boolean checkIsMultiImageItem(String suffix) {
        ArrayList<String> suffixList = new ArrayList<>();
        suffixList.add("rtf");
        suffixList.add("doc");
        suffixList.add("docx");
        suffixList.add("docm");
        suffixList.add("zhtbs");
        suffixList.add("xls");
        suffixList.add("xlsx");
        suffixList.add("xlsm");
        suffixList.add("ppt");
        suffixList.add("pptx");
        suffixList.add("pptm");

        String lowSuffix = suffix.toLowerCase();
        for (String value : suffixList) {
            if (lowSuffix.contains(value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * For asserts files
     *
     * @param
     * @return
     */
    public  String fileToMD5(InputStream inputStream) {
        try {
            // The buffer to read the file
            byte[] buffer = new byte[1024];
            // Get a MD5 instance
            MessageDigest digest = MessageDigest.getInstance("MD5");
            // Record how many bytes have been read
            int numRead = 0;
            while (numRead != -1) {
                numRead = inputStream.read(buffer);
                if (numRead > 0) {
                    // Update the digest
                    digest.update(buffer, 0, numRead);
                }
            }
            // Complete the hash computing
            byte[] md5Bytes = digest.digest();
            // Call the function to convert to hex digits
            return convertHashToString(md5Bytes);
        } catch (Exception e) {
            return null;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close(); // Close the InputStream
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }



    /**
     * @param filePath
     * @return
     */
    public  String fileToMD5(String filePath) {
        InputStream inputStream = null;
        try {
            // Create an FileInputStream instance according to the filepath
            inputStream = new FileInputStream(filePath);
            // The buffer to read the file
            byte[] buffer = new byte[1024];
            // Get a MD5 instance
            MessageDigest digest = MessageDigest.getInstance("MD5");
            // Record how many bytes have been read
            int numRead = 0;
            while (numRead != -1) {
                numRead = inputStream.read(buffer);
                if (numRead > 0) {
                    // Update the digest
                    digest.update(buffer, 0, numRead);
                }
            }
            // Complete the hash computing
            byte[] md5Bytes = digest.digest();
            // Call the function to convert to hex digits
            return convertHashToString(md5Bytes);
        } catch (Exception e) {
            return null;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close(); // Close the InputStream
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Get the sha1 value of the filepath specified file
     *
     * @param filePath The filepath of the file
     * @return The sha1 value
     */
    public  String fileToSHA1(String filePath) {
        InputStream inputStream = null;
        try {
            // Create an FileInputStream instance according to the filepath
            inputStream = new FileInputStream(filePath);
            // The buffer to read the file
            byte[] buffer = new byte[1024];
            // Get a SHA-1 instance
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            // Record how many bytes have been read
            int numRead = 0;
            while (numRead != -1) {
                numRead = inputStream.read(buffer);
                if (numRead > 0) {
                    // Update the digest
                    digest.update(buffer, 0, numRead);
                }
            }
            // Complete the hash computing
            byte[] sha1Bytes = digest.digest();
            // Call the function to convert to hex digits
            return convertHashToString(sha1Bytes);
        } catch (Exception e) {
            return null;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close(); // Close the InputStream
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Convert the hash bytes to hex digits string
     * @param hashBytes
     * @return The converted hex digits string
     */
    public  String convertHashToString(byte[] hashBytes) {
        StringBuilder returnVal = new StringBuilder();
        for (byte hashByte : hashBytes) {
            returnVal.append(Integer.toString((hashByte & 0xff) + 0x100, 16).substring(1));
        }
        return returnVal.toString().toLowerCase();
    }

    /**
     * 将字符串转成MD5值
     * @param string 需要转换的字符串
     * @return 字符串的MD5值
     */
    public  String stringToMD5(String string) {
        byte[] hash;

        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) {
                hex.append("0");
            }
            hex.append(Integer.toHexString(b & 0xFF));
        }

        return hex.toString();
    }


    public  void zip(String src, String dest) {
        //提供了一个数据项压缩成一个ZIP归档输出流
        ZipOutputStream out = null;
        try {
            //源文件或者目录
            File outFile = new File(dest);
            //压缩文件路径
            File fileOrDirectory = new File(src);
            out = new ZipOutputStream(new FileOutputStream(outFile));
            //如果此文件是一个文件，否则为false。
            if (fileOrDirectory.isFile()) {
                zipFileOrDirectory(out, fileOrDirectory, "");
            } else {
                //返回一个文件或空阵列。
                File[] entries = fileOrDirectory.listFiles();
                for (File entry : entries) {
                    // 递归压缩，更新curPaths
                    zipFileOrDirectory(out, entry, "");
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            //关闭输出流
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private  void zipFileOrDirectory(ZipOutputStream out, File fileOrDirectory, String curPath) {
        //从文件中读取字节的输入流
        FileInputStream in = null;
        try {
            //如果此文件是一个目录，否则返回false。
            if (!fileOrDirectory.isDirectory()) {
                // 压缩文件
                byte[] buffer = new byte[4096];
                int bytesRead;
                in = new FileInputStream(fileOrDirectory);
                //实例代表一个条目内的ZIP归档
                ZipEntry entry = new ZipEntry(curPath
                        + fileOrDirectory.getName());
                //条目的信息写入底层流
                out.putNextEntry(entry);
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
                out.closeEntry();
            } else {
                // 压缩目录
                File[] entries = fileOrDirectory.listFiles();
                for (File entry : entries) {
                    // 递归压缩，更新curPaths
                    zipFileOrDirectory(out, entry, curPath + fileOrDirectory.getName() + "/");
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            // throw ex;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }


    public  int isDataSource(String path){
        return path.contains("assets")?0:1;
    }

    /**
     * 判断地址是否为视频
     * @param path video path
     * @return is video return true else return false
     */
    public  boolean isVideo(String path){
        return path.toLowerCase().endsWith(".mp4") || path.toLowerCase().endsWith(".3gp") || path.toLowerCase().endsWith(".mov");
    }
}

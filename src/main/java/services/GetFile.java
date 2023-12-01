package services;

import socket.SendMail;

import java.io.File;
import java.io.PrintWriter;
import java.net.Socket;

public class GetFile {
    public static String searchFile(File root, String fileNameToSearch) {
        String pathToFile = "";
        try {
            if (root.isDirectory()) {
                File[] fileList = root.listFiles();
                if (fileList != null) {
                    for (File file : fileList) {
                        if (file.isDirectory()) {
                            // Nếu là thư mục, tiếp tục đệ quy tìm kiếm trong thư mục con
                            searchFile(file, fileNameToSearch);
                        } else if (fileNameToSearch.equals(file.getName())) {
                            // Nếu tìm thấy tệp có tên mong muốn, in đường dẫn ra màn hình
                            pathToFile = file.getAbsolutePath();
                            System.out.println("Found file: " + pathToFile);
                            break;
                        }
                    }
                }
            }
        }
        catch (SecurityException e){
            System.err.println("Access denied to " + root.getAbsolutePath());
        }
        catch (NullPointerException e) {
            System.err.println("Null pointer exception occurred");
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
        return pathToFile;
    }

    public static void searchFileInRoots(String fileNameToSearch, String from) {
        String pathToFile = "";
        for (File root : File.listRoots()) {
            pathToFile = searchFile(root, fileNameToSearch);
            if(pathToFile != ""){
                break;
            }
        }
        if(pathToFile != "") {
            SendMail.sendEmail(from, "Reply for request: Get File", pathToFile,
                    "<!DOCTYPE html>\n" +
                            "<html>\n" +
                            "<head>\n" +
                            "<title>Page Title</title>\n" +
                            "</head>\n" +
                            "<body>\n" +
                            "\n" +
                            "<h1>Your request has done successfully</h1>\n" +
                            "<p>This is a paragraph.</p>\n" +
                            "\n" +
                            "</body>\n" +
                            "</html>");
        }
        else{
            SendMail.sendEmail(from, "Reply for request: Get File", "",
                    "<!DOCTYPE html>\n" +
                            "<html>\n" +
                            "<head>\n" +
                            "<title>Page Title</title>\n" +
                            "</head>\n" +
                            "<body>\n" +
                            "\n" +
                            "<h1>Your request has failed</h1>\n" +
                            "<p>This is a paragraph.</p>\n" +
                            "\n" +
                            "</body>\n" +
                            "</html>");
        }
    }

    public static void requestSearchFileInRoots(Socket socket, PrintWriter writer, String fileName, String from){
        return;
    }

//    public static void main(String[] args) {
//        String fileNameToSearch = "Bai tap Chuong 2_Dong luc hoc chat diem.pdf";
//
//        // Sử dụng phương thức tĩnh để tìm kiếm tệp trong các root
//        GetFile.searchFileInRoots(fileNameToSearch);
//    }
}


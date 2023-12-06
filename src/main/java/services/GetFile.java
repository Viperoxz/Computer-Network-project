package services;

//***Luu y: Gui mail voi cu phap: getfile[1]+filename(Khong co ngoac kep)
//VD: getfile[1]toan

import socket.SendMail;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class GetFile {
    public static String searchFile(File root, String fileNameToSearch) {
        String pathToFile = "";
        try {
            if (root.isDirectory()) {
                File[] fileList = root.listFiles();
                if (fileList != null) {
                    for (File file : fileList) {
                        if (file.isDirectory()) {
                            // Tiếp tục đệ quy tìm kiếm trong thư mục con
                            pathToFile = searchFile(file, fileNameToSearch);
                            if (!pathToFile.isEmpty()) {
                                // Nếu tìm thấy tệp, thoát vòng lặp
                                break;
                            }
                        } else if (fileNameToSearch.equals(file.getName())) {
                            // Nếu tìm thấy tệp, lưu đường dẫn và thoát vòng lặp
                            pathToFile = file.getAbsolutePath();
                            System.out.println("Found file: " + pathToFile);
                            break;
                        }
                    }
                }
            }
        } catch (SecurityException e) {
            System.err.println("Access denied to " + root.getAbsolutePath());
        } catch (NullPointerException e) {
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
            if (!pathToFile.isEmpty()) {
                break;
            }
        }
        if (!pathToFile.isEmpty()) {
            SendMail.serversendEmail(from, "Reply for request: Get File", pathToFile,
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
        } else {
            SendMail.serversendEmail(from, "Reply for request: Get File", "",
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

    public static void getFileByPath(String path, String from) {
        Path checkPathExists = Paths.get(path);
        if (Files.isRegularFile(checkPathExists)) {
            SendMail.serversendEmail(from, "Reply for request: Get File", path,
                    "<!DOCTYPE html>\n" +
                            "<html>\n" +
                            "<head>\n" +
                            "<title>Page Title</title>\n" +
                            "</head>\n" +
                            "<body>\n" +
                            "<h1>Your request has done successfully</h1>\n" +
                            "<p></p>\n" +
                            "</body>\n" +
                            "</html>");
        }
        else {
            SendMail.serversendEmail(from, "Reply for request: Get File", "",
                    "<!DOCTYPE html>\n" +
                            "<html>\n" +
                            "<head>\n" +
                            "<title>Page Title</title>\n" +
                            "</head>\n" +
                            "<body>\n" +
                            "<h1>This file does not exist. Please check the file path.</h1>\n" +
                            "<p></p>\n" +
                            "</body>\n" +
                            "</html>");
        }
    }



//    public static void main(String[] args) {
//        String fileNameToSearch = "Bai tap Chuong 2_Dong luc hoc chat diem.pdf";
//
//        // Sử dụng phương thức tĩnh để tìm kiếm tệp trong các root
//        GetFile.searchFileInRoots(fileNameToSearch, "pvhn191004@gmail.com");
//    }
}

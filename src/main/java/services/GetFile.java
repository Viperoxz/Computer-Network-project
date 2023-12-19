package services;

//***Luu y: Gui mail voi cu phap: getfile[1]+filename(Khong co ngoac kep)
//VD: getfile[1]toan

import server.SendMail;
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
                    HTMLGenerator.generateHTML("Your request has been completed successfully", "",
                            String.format("Get file %s successful. This is what you want.", fileNameToSearch)));
        } else {
            SendMail.serversendEmail(from, "Reply for request: Get File", "",
                    HTMLGenerator.generateHTML("Your request has failed", "",
                            String.format("Get file %s failed. This file is not found.", fileNameToSearch)));
        }
    }

    public static void getFileByPath(String path, String from) {
        Path checkPathExists = Paths.get(path);
        if (Files.isRegularFile(checkPathExists)) {
            SendMail.serversendEmail(from, "Reply for request: Get File", path,
                    HTMLGenerator.generateHTML("Your request has been completed successfully", "",
                            String.format("Get file %s successful. This is what you want.", path)));
        }
        else {
            SendMail.serversendEmail(from, "Reply for request: Get File", "",
                    HTMLGenerator.generateHTML("Your request has failed", "",
                            String.format("Get file %s failed. This file is not found.", path)));
        }
    }
}

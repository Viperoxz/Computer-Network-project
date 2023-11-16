package services;

import java.io.File;

public class GetFile {
    public static void searchFile(File root, String fileNameToSearch) {
        if (root.isDirectory()) {
            File[] fileList = root.listFiles();
            if (fileList != null) {
                for (File file : fileList) {
                    if (file.isDirectory()) {
                        // Nếu là thư mục, tiếp tục đệ quy tìm kiếm trong thư mục con
                        searchFile(file, fileNameToSearch);
                    } else if (fileNameToSearch.equals(file.getName())) {
                        // Nếu tìm thấy tệp có tên mong muốn, in đường dẫn ra màn hình
                        System.out.println("Found file: " + file.getAbsolutePath());
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        String fileNameToSearch = "Bai tap Chuong 2_Dong luc hoc chat diem.pdf";
        for (int i=0; i<File.listRoots().length; ++i) {
            File root = File.listRoots()[i];
            searchFile(root, fileNameToSearch);
        }
    }
}


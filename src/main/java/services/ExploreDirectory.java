package services;

import socket.SendMail;

import java.io.*;
import java.net.Socket;
import java.util.stream.Collectors;

public class ExploreDirectory {

    /**
     * Pretty print the directory tree and its file names.
     *
     * @param folder
     *            must be a folder.
     * @return
     */
    public static String printDirectoryTree(File folder) {
        if (!folder.isDirectory()) {
            throw new IllegalArgumentException("folder is not a Directory");
        }
        int indent = 0;
        StringBuilder sb = new StringBuilder();
        printDirectoryTree(folder, indent, sb);
        return sb.toString();
    }

    private static void printDirectoryTree(File folder, int indent,
                                           StringBuilder sb) {
        if (!folder.isDirectory()) {
            throw new IllegalArgumentException("folder is not a Directory");
        }
        sb.append(getIndentString(indent));
        sb.append("├──");
        sb.append(folder.getName());
        sb.append("/");
        sb.append("\n");
        for (File file : folder.listFiles()) {
            if (file.isDirectory()) {
                printDirectoryTree(file, indent + 1, sb);
            } else {
                printFile(file, indent + 1, sb);
            }
        }

    }

    private static void printFile(File file, int indent, StringBuilder sb) {
        sb.append(getIndentString(indent));
        sb.append("├──");
        sb.append(file.getName());
        sb.append("\n");
    }

    private static String getIndentString(int indent) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < indent; i++) {
            sb.append("|  ");
        }
        return sb.toString();
    }

    public static void controlExploreDir(BufferedReader reader, PrintWriter writer){
        try{
            String path = reader.readLine();
            System.out.println(path);
            File folder = new File(path);
            if (!folder.exists() || !folder.isDirectory()) {
                System.out.println("Thư mục không tồn tại hoặc không phải là thư mục hợp lệ.");
                return;
            }
            String directoryTree = ExploreDirectory.printDirectoryTree(folder);
            writer.println(directoryTree);
            writer.flush();
        }
        catch (IOException e){}
    }

    public static void requestExploreDir(Socket socket, PrintWriter writer, String path, String from) throws IOException {
        writer.println("exploredirectory");
        writer.flush();
        writer.println(path);
        writer.flush();

        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        //Loi o day
        StringBuilder respone = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            respone.append(line);
            respone.append(System.lineSeparator());
        }
        System.out.println(respone.toString());
        try {
            String fileName = "list_directory" + System.currentTimeMillis() + ".txt";
            File file = new File(fileName);
            FileWriter myWriter = new FileWriter(file);
            myWriter.write(respone.toString());
            myWriter.flush();
//            myWriter.close();

            if (respone != null) {
                String fileTextPath = file.getAbsolutePath();
                SendMail.sendEmail(from, "Reply for request: Explore directory", fileTextPath,
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
                SendMail.sendEmail(from, "Reply for request: Explore directory", "",
                        "<!DOCTYPE html>\n" +
                                "<html>\n" +
                                "<head>\n" +
                                "<title>Page Title</title>\n" +
                                "</head>\n" +
                                "<body>\n" +
                                "\n" +
                                "<h1>Some thing went wrong</h1>\n" +
                                "<p>This is a paragraph.</p>\n" +
                                "\n" +
                                "</body>\n" +
                                "</html>");
            }
        }
        catch (IOException e){
            System.out.println("sai o day");
        }
    }

//    public static void main(String[] args) {
//        // Thay đổi đường dẫn tới thư mục mà bạn muốn in ra cây thư mục của nó
//        String directoryPath = "D:\\crawler";
//
//        File folder = new File(directoryPath);
//
//        // Kiểm tra xem thư mục tồn tại và là thư mục hay không
//        if (!folder.exists() || !folder.isDirectory()) {
//            System.out.println("Thư mục không tồn tại hoặc không phải là thư mục hợp lệ.");
//            return;
//        }
//
//        // In ra cây thư mục và tên các file trong đó
//        String directoryTree = ExploreDirectory.printDirectoryTree(folder);
//        System.out.println("Cây thư mục: \n" + directoryTree);
//        try {
//            String fileName = "list_directory" + System.currentTimeMillis() + ".txt";
//            File file = new File(fileName);
//            FileWriter myWriter = new FileWriter(file);
//            myWriter.write(directoryTree);
//            myWriter.flush();
//        }
//        catch (IOException e){}
//    }
}
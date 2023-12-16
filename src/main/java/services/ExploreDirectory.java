package services;

import socket.SendMail;

import javax.swing.text.html.HTML;
import java.io.*;
import java.net.Socket;
import java.util.stream.Collectors;

import static java.lang.Math.max;

public class ExploreDirectory {

    /**
     * Pretty print the directory tree and its file names.
     *
     * @param folder must be a folder.
     * @return
     */
    public static String printDirectoryTree(File folder) {
        if (!folder.isDirectory()) {
            throw new IllegalArgumentException("folder is not a Directory");
        }
        int indent = 0;
        StringBuilder sb = new StringBuilder();
        printDirectoryTree(folder,folder, indent, sb);
        return sb.toString();
    }

    private static void printDirectoryTree(File par,File folder, int indent,
                                           StringBuilder sb) {
        if (!folder.isDirectory()) {
            throw new IllegalArgumentException("folder is not a Directory");
        }
        sb.append(getIndentString(indent));
        //if here
        if (!folder.equals(par) &&  folder.equals(par.listFiles()[ par.listFiles().length-1] ) )
            sb.append("└──");
        else
            sb.append("├──");
        sb.append(folder.getName());
        sb.append("/");
        sb.append("\n");
//        System.out.println(folder.listFiles()[par.listFiles().length-1].getName());
        for (File file : folder.listFiles()) {
            if (file.isDirectory()) {
                printDirectoryTree(folder,file, indent + 1, sb);
            } else {
                printFile(folder,file, indent + 1, sb);
            }
        }

    }

    private static void printFile(File par, File file, int indent, StringBuilder sb) {
        sb.append(getIndentString(indent));
        if (!file.equals(par) &&  file.equals(par.listFiles()[ par.listFiles().length-1] ))
            sb.append("└──");
        else
            sb.append("├──");
        sb.append(file.getName());
        sb.append("\n");
    }

    private static String getIndentString(int indent) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < indent; i++) {
            sb.append("│  ");
        }
        return sb.toString();
    }

    public static void controlExploreDir(BufferedReader reader, PrintWriter writer) {
        try {
            String path = reader.readLine();
            System.out.println(path);
            File folder = new File(path);
            if (!folder.exists() || !folder.isDirectory()) {
                System.out.println("Thư mục không tồn tại hoặc không phải là thư mục hợp lệ.");
                writer.println("The directory doesn't exist.");
                writer.flush();
                writer.close();
            } else {
                String directoryTree = ExploreDirectory.printDirectoryTree(folder);
                writer.println(directoryTree);
                writer.flush();
                writer.close();
            }
        } catch (IOException e) {
        }
    }

    public static void requestExploreDir( String path, String from) throws IOException {
//        writer.println("exploredirectory");
//        writer.flush();
//        writer.println(path);
//        writer.flush();

//        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        File folder = new File(path);
        String reader="";
        if (!folder.exists() || !folder.isDirectory()) {
            reader="The directory doesn't exist.";

        } else {
            String directoryTree = ExploreDirectory.printDirectoryTree(folder);
            reader=directoryTree;

        }
        String response = reader;

//        System.out.println(response);
        try {
            String fileName = "./src/test/output/directory.txt";
            File file = new File(fileName);

            FileWriter fileWriter = new FileWriter(file, false);

            fileWriter.write(response);
            fileWriter.flush();
            fileWriter.close();

            if (!response.equals("The directory doesn't exist.")) {
                String fileTextPath = file.getAbsolutePath();
                SendMail.serversendEmail(from, "Reply for request: Explore directory", fileTextPath,
                        HTMLGenerator.generateHTML("Your request has been completed successfully", "",
                                """
                                        Directory exploration successful. 
                                        The file below contains the directory structure you requested.
                                        """));
            } else {
                SendMail.serversendEmail(from, "Reply for request: Explore directory", "",
                        HTMLGenerator.generateHTML("Your request has failed", "",
                                """
                                        Can't explore this directory. Please ensure that the path is correct.
                                         """));
            }
        } catch (IOException e) {
            System.out.println("Error occurred.");
        }
    }

}





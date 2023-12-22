package services;

import server.SendMail;

import java.io.*;
import java.util.ArrayList;

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
        if (!folder.equals(par) &&  folder.equals(par.listFiles()[ par.listFiles().length-1] ) )
            sb.append("└──");
        else
            sb.append("├──");
        sb.append(folder.getName());
        sb.append("/");
        sb.append("\n");
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

    public static void requestExploreDir( String path, String from) throws IOException {
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
                                        Directory exploration successful. <br>
                                       <b>The file below</b> contains the directory structure you requested.
                                        """));
            } else {
                SendMail.serversendEmail(from, "Reply for request: Explore directory", "",
                        HTMLGenerator.generateHTML("Your request has failed", "",
                                """
                                        <b>Can't explore this directory.</b><br>
                                         Please ensure that the path is correct.
                                         """));
            }
        } catch (IOException e) {
            System.out.println("Error occurred.");
        }
    }
}
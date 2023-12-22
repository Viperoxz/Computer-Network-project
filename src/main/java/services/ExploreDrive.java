package services;

import java.io.IOException;
import java.nio.file.*;
import java.util.stream.Stream;
import server.SendMail;

public class ExploreDrive {

    public static void listDirectoriesAndFiles(String drive, String to) {
        Path directory = Paths.get(drive + ":\\");
        try {
            Stream<Path> entries = Files.list(directory);

            Path outputPath = Paths.get("./src/test/output/exploredrive.txt");

            Files.createDirectories(outputPath.getParent());
            Files.deleteIfExists(outputPath);
            Files.createFile(outputPath);
            Files.write(outputPath, ("Contents of Drive " + drive + ":\n").getBytes(), StandardOpenOption.APPEND);

            entries.forEach(entry -> {
                try {
                    if (Files.isDirectory(entry)) {
                        Files.write(outputPath, ("Folder: " + entry.getFileName() + "\n").getBytes(), StandardOpenOption.APPEND);
                    } else {
                        Files.write(outputPath, ("File: " + entry.getFileName() + "\n").getBytes(), StandardOpenOption.APPEND);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            entries.close();

            //send mail
            SendMail.serversendEmail(to, "Reply for request: Explore Drive", outputPath.toString(),
                    HTMLGenerator.generateHTML("Your request has been completed successfully", "",
                            String.format("""
                                    Drive exploration successful. <br>
                                   <b>The file below</b> contains the contents of Drive <b>%s</b>.
                                    """, drive)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        listDirectoriesAndFiles("D", "pvhn191004@gmail.com");
    }
}

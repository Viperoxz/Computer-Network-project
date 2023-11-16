import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class getFile {
    public getFile() {
        System.out.println("Đang lấy file nè...");
        getfile();
    }
    public void getfile() {

        // Replace "path/to/your/file.txt" with the actual path to your file
        String sourceFilePath = "D:\\Python\\Racing_game\\main_game.py";
        // Replace "path/to/your/folder" with the actual path to your destination folder
        String destinationFolderPath = "D:\\Java\\MMT\\untitled\\src\\New folder";

        try {
            // Create Path objects for the source file and destination folder
            Path sourcePath = Paths.get(sourceFilePath);
            Path destinationPath = Paths.get(destinationFolderPath, sourcePath.getFileName().toString());

            // Perform the file copy
            Files.copy(sourcePath, destinationPath);

            System.out.println("Lấy xong rồi");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

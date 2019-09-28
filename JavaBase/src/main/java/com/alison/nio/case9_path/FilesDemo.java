package com.alison.nio.case9_path;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * @Author alison
 * @Date 2019/9/28  10:30
 * @Version 1.0
 * @Description
 */
public class FilesDemo {
    private static void filestest() throws Exception {
//        Files.exists()
//        Files.createDirectory()
//        Files.copy()
//        Overwriting Existing Files
//        Files.move()
//        Files.delete()
//        Files.walkFileTree()
//        Searching For Files
//        Deleting Directories Recursively
        Path path = Paths.get("data/logging.properties");
        boolean pathExists =
                Files.exists(path,
                        new LinkOption[]{LinkOption.NOFOLLOW_LINKS});

        Path dataPath = Paths.get("data/subdir");
        try {
            Path newDir = Files.createDirectory(dataPath);
        } catch (FileAlreadyExistsException e) {
            // the directory already exists.
        } catch (IOException e) {
            //something else went wrong
            e.printStackTrace();
        }

        Path sourcePath = Paths.get("data/logging.properties");
        Path destinationPath = Paths.get("data/logging-copy.properties");

        try {
//            This parameter instructs the copy() method to overwrite an existing file if the destination file already exists.
            Files.copy(sourcePath, destinationPath,
                    StandardCopyOption.REPLACE_EXISTING);
//            if a file already exists at the destination path, and you have left out the
//            StandardCopyOption.REPLACE_EXISTING option, or if the file to move does not exist etc.
            Files.move(sourcePath, destinationPath,
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (FileAlreadyExistsException e) {
            //destination file already exists
        } catch (IOException e) {
            //something else went wrong
            e.printStackTrace();
        }

        Path logPath = Paths.get("data/subdir/logging-moved.properties");
        try {
            Files.delete(logPath);
        } catch (IOException e) {
            //deleting file failed
            e.printStackTrace();
        }


        Files.walkFileTree(path, new FileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                System.out.println("pre visit dir:" + dir);
                return FileVisitResult.CONTINUE;
            }
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                System.out.println("visit file: " + file);
                return FileVisitResult.CONTINUE;
            }
            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                System.out.println("visit file failed: " + file);
                return FileVisitResult.CONTINUE;
            }
            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                System.out.println("post visit directory: " + dir);
                return FileVisitResult.CONTINUE;
            }
        });

        Path rootPath = Paths.get("data");
        String fileToFind = File.separator + "README.txt";
        try {
            Files.walkFileTree(rootPath, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    String fileString = file.toAbsolutePath().toString();
                    //System.out.println("pathString = " + fileString);
                    if(fileString.endsWith(fileToFind)){
                        System.out.println("file found at path: " + file.toAbsolutePath());
                        return FileVisitResult.TERMINATE;
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch(IOException e){
            e.printStackTrace();
        }


        Path rootPath2Delete = Paths.get("data/to-delete");

        try {
            Files.walkFileTree(rootPath2Delete, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    System.out.println("delete file: " + file.toString());
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }
                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    Files.delete(dir);
                    System.out.println("delete dir: " + dir.toString());
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch(IOException e){
            e.printStackTrace();
        }



    }
}

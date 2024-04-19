package mk.kolokvium.updatedEX.ex3;

import java.util.*;

class FileNameExistsException extends Exception {
    public String format;

    public FileNameExistsException(String format) {
        this.format = format;
    }
}

interface IFile extends Comparable<IFile> {
    String getFileName();

    Long getFileSize();

    String getFileInfo(String indent);

    void sortBySize();

    long findLargestFile(); // This method goes through all the files and finds the largest one

    @Override //TODO default only works in interfaces
    default int compareTo(IFile o) {
        return Long.compare(this.getFileSize(), o.getFileSize());
    }
}

class File implements IFile {

    public String fileName;
    public long size;

    public File(String fileName, long size) {
        this.fileName = fileName;
        this.size = size;
    }

    @Override
    public String getFileName() {
        return fileName;
    }

    @Override
    public Long getFileSize() {
        return size;
    }

    @Override
    public String getFileInfo(String indent) { //TODO make the print method
        return String.format("%s File name:%10s File size: %d\n", indent, fileName, getFileSize());
    }

    @Override
    public void sortBySize() {
        //Noting to sort its just 1 file
    }

    @Override
    public long findLargestFile() {
        return size;
    }

}

class Folder extends File {
    public List<IFile> listFiles;

    public Folder(String fileName) {
        super(fileName, 0);
        this.listFiles = new ArrayList<>();
    }

    public void addFile(IFile file) throws FileNameExistsException {
        for (int i = 0; i < listFiles.size(); i++) {
            if (listFiles.get(i).getFileName().equals(file.getFileName())) {
                throw new FileNameExistsException(String.format("There is already a file named %s int the folder %s", fileName, fileName));
            }
        }
        listFiles.add(file);
    }

    @Override
    public String getFileName() {
        return super.getFileName();
    }

    @Override
    public Long getFileSize() {
        // TODO this goes through all files and folders inside so its recursive
        return listFiles.stream().mapToLong(IFile::getFileSize).sum();
    }

    @Override
    public String getFileInfo(String indent) {
        StringBuilder st = new StringBuilder();
        st.append(String.format("%s Folder name:%10s Folder size:%10d\n", indent, fileName, getFileSize()));
        for (IFile listFile : listFiles) {
            st.append(listFile.getFileInfo(indent + "    ")).append('\n');
        }
        return st.toString();
    }

    @Override
    public void sortBySize() {
        Collections.sort(listFiles);
    }

    @Override
    public long findLargestFile() {
        return listFiles.stream().mapToLong(IFile::findLargestFile).max().orElse(0);
    }

}

class FileSystem {
    Folder rootFolder;

    public FileSystem() {
        rootFolder = new Folder("root");
    }

    void addFile(IFile file) throws FileNameExistsException {
        rootFolder.addFile(file);
    }

    long findLargestFile() {
        return rootFolder.findLargestFile();
    }

    void sortBySize() {
        rootFolder.sortBySize();
    }

    @Override
    public String toString() {
        return rootFolder.getFileInfo("");
    }
}


public class FileSystemTest {

    public static Folder readFolder(Scanner sc) {

        Folder folder = new Folder(sc.nextLine());
        int totalFiles = Integer.parseInt(sc.nextLine());

        for (int i = 0; i < totalFiles; i++) {
            String line = sc.nextLine();

            if (line.startsWith("0")) {
                String fileInfo = sc.nextLine();
                String[] parts = fileInfo.split("\\s+");
                try {
                    folder.addFile(new File(parts[0], Long.parseLong(parts[1])));
                } catch (FileNameExistsException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                try {
                    folder.addFile(readFolder(sc));
                } catch (FileNameExistsException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        return folder;
    }

    public static void main(String[] args) {

        //file reading from input

        Scanner sc = new Scanner(System.in);

        System.out.println("===READING FILES FROM INPUT===");
        FileSystem fileSystem = new FileSystem();
        try {
            fileSystem.addFile(readFolder(sc));
        } catch (FileNameExistsException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("===PRINTING FILE SYSTEM INFO===");
        System.out.println(fileSystem.toString());

        System.out.println("===PRINTING FILE SYSTEM INFO AFTER SORTING===");
        fileSystem.sortBySize();
        System.out.println(fileSystem.toString());

        System.out.println("===PRINTING THE SIZE OF THE LARGEST FILE IN THE FILE SYSTEM===");
        System.out.println(fileSystem.findLargestFile());


    }
}
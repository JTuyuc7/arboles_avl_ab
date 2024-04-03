package generarDatos;

public class FileData {
    private final String fileName;
    private final int fileSize;

    public FileData(String fileName, int fileSize) {
        this.fileName = fileName;
        this.fileSize = fileSize;
    }

    public String getFileName() {
        return fileName;
    }

    public int getFileSize() {
        return fileSize;
    }
}

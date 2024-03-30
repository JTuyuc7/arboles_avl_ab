package utils;

public class CustomReturn {
    private final String fileName;
    private final int fileOrderThree;

    public CustomReturn(String fileName, int fileOrderThree) {
        this.fileName = fileName;
        this.fileOrderThree = fileOrderThree;
    }

    public String getFileName() {
        return fileName;
    }

    public int getFileOrder() {
        return fileOrderThree;
    }
}

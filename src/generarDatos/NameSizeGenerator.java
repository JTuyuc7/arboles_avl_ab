package generarDatos;

public class NameSizeGenerator {
    public FileData getFileName(Integer amount) {
        String fileName = "";
        int fileSize = 0;
        switch (amount) {
            case 1:
                fileName = "registros50k.json";
                fileSize = 50000;
                break;
            case 2:
                fileName = "registros300k.json";
                fileSize = 300000;
                break;
            case 3:
                fileName = "registros500k.json";
                fileSize = 500000;
                break;
            case 4:
                fileName = "registros750k.json";
                fileSize = 750000;
                break;
            case 5:
                fileName = "registros1_5m.json";
                fileSize = 1500000;
                break;

        }
        return new FileData(fileName, fileSize);
    }

}


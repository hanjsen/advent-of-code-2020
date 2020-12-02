package utils;

import java.io.File;

public class FileUtil {

    private File file;

    public FileUtil() {
    }

    public File getFile() {
        return file;
    }

    public String getPath() {
        return file.getPath();
    }

    public void importFile(String path) {
        this.file = new File(path);
    }
}

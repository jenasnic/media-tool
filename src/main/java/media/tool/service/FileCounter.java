package media.tool.service;

import java.io.File;
import java.io.FileFilter;

public class FileCounter
{
    protected FileFilter fileFilter;

    public FileCounter(FileFilter fileFilter)
    {
        this.fileFilter = fileFilter;
    }

    public int countFiles(String folderToProcess)
    {
        File folder = new File(folderToProcess);

        File[] fileArray = folder.listFiles(this.fileFilter);

        int count = 0;
        for (File file : fileArray) {
            if (file.isDirectory()) {
                count += this.countFiles(file.getAbsolutePath());
            } else {
                count++;
            }
        }

        return count;
    }
}

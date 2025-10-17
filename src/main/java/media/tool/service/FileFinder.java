package media.tool.service;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;

public class FileFinder
{
    protected FileFilter fileFilter;

    public FileFinder(FileFilter fileFilter)
    {
        this.fileFilter = fileFilter;
    }

    public File[] getSortedFiles(String folderToProcess)
    {
        File folder = new File(folderToProcess);

        if (!folder.exists()) {
            throw new RuntimeException(String.format("Folder '%s' doesn't exists!", folderToProcess));
        }

        File[] fileArray = folder.listFiles(this.fileFilter);

        Arrays.sort(fileArray, (File f1, File f2) -> (f1.getName().compareTo(f2.getName())));

        return fileArray;
    }
}

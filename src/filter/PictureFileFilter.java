package filter;

import java.io.File;
import java.io.FileFilter;

import org.apache.commons.io.FilenameUtils;

/**
 * Default filter for picture files (only jpg, jpeg, raw, nef, mts and mp4).
 */
public class PictureFileFilter implements FileFilter
{
    protected boolean includeSubDirectories;

    public PictureFileFilter()
    {
        this(false);
    }

    public PictureFileFilter(boolean includeSubDirectories)
    {
        this.includeSubDirectories = includeSubDirectories;
    }

    @Override
    public boolean accept(File file)
    {
        if (this.includeSubDirectories && file.isDirectory()) {
            return true;
        }

        String extension = FilenameUtils.getExtension(file.getName());

        return extension.equalsIgnoreCase("jpg")
            || extension.equalsIgnoreCase("jpeg")
            || extension.equalsIgnoreCase("raw")
            || extension.equalsIgnoreCase("nef")
            || extension.equalsIgnoreCase("mts")
            || extension.equalsIgnoreCase("mp4")
        ;
    }
}

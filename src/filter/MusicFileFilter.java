package filter;

import java.io.File;
import java.io.FileFilter;

import org.apache.commons.io.FilenameUtils;

/**
 * Default filter for music files (only mp3, flac and wma).
 */
public class MusicFileFilter implements FileFilter
{
    protected boolean includeSubDirectories;

    public MusicFileFilter()
    {
        this(false);
    }

    public MusicFileFilter(boolean includeSubDirectories)
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

        return extension.equalsIgnoreCase("mp3")
            || extension.equalsIgnoreCase("flac")
            || extension.equalsIgnoreCase("wma")
        ;
    }
}

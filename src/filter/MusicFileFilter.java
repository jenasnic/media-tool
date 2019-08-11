package filter;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FilenameUtils;

import model.Configuration;

/**
 * Default filter for music files (only mp3, flac and wma).
 */
public class MusicFileFilter implements FileFilter
{
    protected boolean includeSubDirectories;
    protected List<String> extensions;

    public MusicFileFilter(Configuration configuration)
    {
        this(configuration, false);
    }

    public MusicFileFilter(Configuration configuration, boolean includeSubDirectories)
    {
        this.includeSubDirectories = includeSubDirectories;
        this.extensions = Arrays.asList(configuration.getMusicExtensions());
    }

    @Override
    public boolean accept(File file)
    {
        if (this.includeSubDirectories && file.isDirectory()) {
            return true;
        }

        return this.extensions.contains(FilenameUtils.getExtension(file.getName()));
    }
}

package filter;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
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

        this.extensions = new ArrayList<String>();
        for (String extension : configuration.getMusicExtensions()) {
            this.extensions.add(extension.toLowerCase());
        }
    }

    @Override
    public boolean accept(File file)
    {
        if (this.includeSubDirectories && file.isDirectory()) {
            return true;
        }

        String extension = FilenameUtils.getExtension(file.getName()).toLowerCase();

        return this.extensions.contains(extension);
    }
}

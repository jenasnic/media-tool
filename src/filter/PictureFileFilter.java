package filter;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FilenameUtils;

import model.Configuration;

/**
 * Default filter for picture files (only jpg, jpeg, raw, nef, mts and mp4).
 */
public class PictureFileFilter implements FileFilter
{
    protected boolean includeSubDirectories;
    protected List<String> extensions;

    public PictureFileFilter(Configuration configuration)
    {
        this(configuration, false);
    }

    public PictureFileFilter(Configuration configuration, boolean includeSubDirectories)
    {
        this.includeSubDirectories = includeSubDirectories;
        this.extensions = Arrays.asList(configuration.getPictureExtensions());
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

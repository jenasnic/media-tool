package media.tool.filter;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;

import media.tool.model.Configuration;

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

        this.extensions = new ArrayList<String>();
        for (String extension : configuration.getPictureExtensions()) {
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

package filter;

import java.io.File;

import javax.swing.filechooser.FileFilter;

import org.apache.commons.io.FilenameUtils;

/**
 * Default filter for yaml files (only yml and yaml).
 */
public class YamlFileFilter extends FileFilter
{
    @Override
    public boolean accept(File file)
    {
        String extension = FilenameUtils.getExtension(file.getName()).toLowerCase();

        return file.isDirectory() || extension.equals("yaml") || extension.equals("yml");
    }

    @Override
    public String getDescription() {
        return "yaml";
    }
}

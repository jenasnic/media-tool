package model;

import java.util.regex.Pattern;

/**
 * Model used to define filename format to use to automatically tag MP3.
 * Label will be displayed in interface to select format.
 * Pattern is regular expression used to extract tag informations from filename.
 * IMPORTANT : regular expression must define group name matching TagType information we want to extract.
 * TagTypes is the list of tag informations defined in previous pattern (artist, album...).
 */
public class FilenameTagFormat
{
    protected String label;
    protected Pattern pattern;
    protected TagType[] tagTypes;

    public FilenameTagFormat(String label, Pattern pattern, TagType[] tagTypes)
    {
        this.label = label;
        this.pattern = pattern;
        this.tagTypes = tagTypes;
    }

    public String getLabel() {
        return label;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public TagType[] getTagTypes() {
        return tagTypes;
    }

    @Override
    public String toString() {
        return label;
    }
}

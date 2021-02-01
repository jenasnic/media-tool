package model;

import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * Model used to define filename format to use to automatically tag MP3.
 * Label will be displayed in interface to select format.
 * Pattern is regular expression used to extract tag informations from filename.
 * IMPORTANT : regular expression must define group name matching TagType information we want to extract.
 * TagTypes is the list of tag informations defined in previous pattern (artist, album...).
 */
public enum FilenameTagFormat
{
    ARTIST_TITLE(
        "%artist% - %title%",
        Pattern.compile(String.format("^(?<%s>[^\\-]+)\\s\\-\\s(?<%s>.+)$", TagType.ARTIST.name(), TagType.TITLE.name())),
        new TagType[] {TagType.ARTIST, TagType.TITLE}
    ),

    ALBUM_TITLE(
        "%album% - %title%",
        Pattern.compile(String.format("^(?<%s>[^\\-]+)\\s\\-\\s(?<%s>.+)$", TagType.ALBUM.name(), TagType.TITLE.name())),
        new TagType[] {TagType.ALBUM, TagType.TITLE}
    ),

    ARTIST_ALBUM_TITLE(
        "%artist% - %album% - %title%",
        Pattern.compile(String.format("^(?<%s>[^\\-]+)\\s\\-\\s(?<%s>[^\\-]+)\\s\\-\\s(?<%s>.+)$", TagType.ARTIST.name(), TagType.ALBUM.name(), TagType.TITLE.name())),
        new TagType[] {TagType.ARTIST, TagType.ALBUM, TagType.TITLE}
    ),

    ALBUM_TITLE_ARTIST(
        "%album% - %title% (%artist%)",
        Pattern.compile(String.format("^(?<%s>[^\\-]+)\\s\\-\\s(?<%s>[^\\(]+)\\s\\((?<%s>[^\\)]+)\\)$", TagType.ALBUM.name(), TagType.TITLE.name(), TagType.ARTIST.name())),
        new TagType[] {TagType.ALBUM, TagType.TITLE, TagType.ARTIST}
    ),

    TITLE(
        "%title%",
        Pattern.compile(String.format("^(?<%s>.+)$", TagType.TITLE.name())),
        new TagType[] {TagType.TITLE}
    ),

    TITLE_ARTIST(
        "%title% (%artist%)",
        Pattern.compile(String.format("^(?<%s>[^\\(]+)\\s\\((?<%s>[^\\)]+)\\)$", TagType.TITLE.name(), TagType.ARTIST.name())),
        new TagType[] {TagType.TITLE, TagType.ARTIST}
    ),

    TITLE_IGNORE(
        "%title% (%ignore%)",
        Pattern.compile(String.format("^(?<%s>[^\\(]+)\\s\\([^\\)]+\\)$", TagType.TITLE.name())),
        new TagType[] {TagType.TITLE}
    ),

    IGNORE_TITLE(
        "%ignore% - %title%",
        Pattern.compile(String.format("^[^\\-]+\\s\\-\\s(?<%s>.+)$", TagType.TITLE.name())),
        new TagType[] {TagType.TITLE}
    ),

    IGNORE_TITLE_ARTIST(
        "%ignore% - %title% (%artist%)",
        Pattern.compile(String.format("^[^\\-]+\\s\\-\\s(?<%s>[^\\(]+)\\s\\((?<%s>[^\\)]+)\\)$", TagType.TITLE.name(), TagType.ARTIST.name())),
        new TagType[] {TagType.TITLE, TagType.ARTIST}
    ),

    IGNORE_TITLE_IGNORE(
        "%ignore% - %title% (%ignore%)",
        Pattern.compile(String.format("^[^\\-]+\\s\\-\\s(?<%s>[^\\(]+)\\s\\([^\\)]+\\)$", TagType.TITLE.name())),
        new TagType[] {TagType.TITLE}
    ),

    ALBUM_TITLE_IGNORE(
        "%album% - %title% (%ignore%)",
        Pattern.compile(String.format("^(?<%s>[^\\-]+)\\s\\-\\s(?<%s>[^\\(]+)\\s\\([^\\)]+\\)$", TagType.ALBUM.name(), TagType.TITLE.name())),
        new TagType[] {TagType.ALBUM, TagType.TITLE}
    );

    protected String label;
    protected Pattern pattern;
    protected TagType[] tagTypes;

    FilenameTagFormat(String label, Pattern pattern, TagType[] tagTypes)
    {
        this.label = label;
        this.pattern = pattern;
        this.tagTypes = tagTypes;
    }

    public static FilenameTagFormat getByFormat(String format) {
        for (FilenameTagFormat filenameTagFormat : FilenameTagFormat.values()) {
            if (filenameTagFormat.getLabel().equalsIgnoreCase(format)) {
                return filenameTagFormat;
            }
        }

        throw new IllegalArgumentException(String.format("Unknown filename format '%s'!", format));
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

    public boolean hasTagType(TagType tagType) {
        return Arrays.asList(this.tagTypes).contains(tagType);
    }

    @Override
    public String toString() {
        return label;
    }
}

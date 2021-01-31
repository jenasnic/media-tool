package service;

import java.util.regex.Matcher;

import org.apache.commons.io.FilenameUtils;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.ID3v24Tag;

import model.FilenameTagFormat;
import model.TagType;

/**
 * Allows to create MP3 ID tag from specified file name (using FilenameTagFormat).
 * @see FilenameTagFormat
 */
public class FilenameTagBuilder
{
    protected FilenameTagFormat filenameTagFormat;

    public FilenameTagBuilder(FilenameTagFormat filenameTagFormat)
    {
        this.filenameTagFormat = filenameTagFormat;
    }

    public ID3v2 buildTag(String filename)
    {
        String basename = FilenameUtils.getBaseName(filename);

        Matcher matcher = this.filenameTagFormat.getPattern().matcher(basename);
        if (!matcher.find() || matcher.groupCount() != this.filenameTagFormat.getTagTypes().length) {
            return null;
        }

        ID3v24Tag tag = new ID3v24Tag();
        for (TagType tagType : this.filenameTagFormat.getTagTypes()) {
            String value = matcher.group(tagType.name());
            this.setTagPropertyValue(tag, tagType, value);
        }

        return tag;
    }

    public FilenameTagFormat getFilenameTagFormat()
    {
        return this.filenameTagFormat;
    }

    protected void setTagPropertyValue(ID3v2 tag, TagType property, String value)
    {
        switch (property)
        {
        case ARTIST:
            tag.setArtist(value);
            break;
        case ALBUM:
            tag.setAlbum(value);
            break;
        case TITLE:
            tag.setTitle(value);
            break;
        case GENRE:
            tag.setGenreDescription(value);
            break;
        }
    }
}

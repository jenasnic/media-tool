package model;

import java.util.List;
import java.util.Map;

import service.FilenameTagBuilder;

public class YamlProperties
{
    protected String name;
    protected FilenameTagBuilder filenameTagBuilder;
    protected String artist;
    protected String album;
    protected boolean useFolderNameAsAlbum;
    protected List<String> tags;

    private YamlProperties(String name, FilenameTagBuilder filenameTagBuilder, String artist, String album, boolean useFolderNameAsAlbum, List<String> tags)
    {
        this.name = name;
        this.filenameTagBuilder = filenameTagBuilder;
        this.artist = artist;
        this.album = album;
        this.useFolderNameAsAlbum = useFolderNameAsAlbum;
        this.tags = tags;
    }

    public static YamlProperties buildFromMap(Map<String, Object> properties)
    {
        String name = properties.containsKey("name") ? properties.get("name").toString() : null;
        String artist = properties.containsKey("artist") ? properties.get("artist").toString() : null;
        String album = properties.containsKey("album") ? properties.get("album").toString() : null;
        String format = properties.containsKey("filename_tag_format") ? properties.get("filename_tag_format").toString() : null;
        String folderAlbumName = properties.containsKey("use_folder_name_as_album") ? properties.get("use_folder_name_as_album").toString() : null;

        @SuppressWarnings("unchecked")
        List<String> tags = properties.containsKey("tags") ? (List<String>)properties.get("tags") : null;

        FilenameTagBuilder filenameTagBuilder = null;

        if (null != format) {
            FilenameTagFormat filenameTagFormat = FilenameTagFormat.getByFormat(format);
            filenameTagBuilder = new FilenameTagBuilder(filenameTagFormat);
        }

        boolean useFolderNameAsAlbum = false;
        if (null != folderAlbumName) {
            useFolderNameAsAlbum = Boolean.parseBoolean(folderAlbumName);
        }

        if (null != tags) {
            for (String genre : tags) {
                if (!TagGenre.exist(genre)) {
                    throw new IllegalArgumentException(String.format("Unknown tag '%s'!", genre));
                }
            }
        }

        return new YamlProperties(name, filenameTagBuilder, artist, album, useFolderNameAsAlbum, tags);
    }

    public String getName()
    {
        return name;
    }

    public FilenameTagBuilder getFilenameTagBuilder()
    {
        return filenameTagBuilder;
    }

    public String getArtist()
    {
        return artist;
    }

    public String getAlbum()
    {
        return album;
    }

    public List<String> getTags()
    {
        return tags;
    }

    public boolean useFolderNameAsAlbum()
    {
        return useFolderNameAsAlbum;
    }
}

package model;

public class Configuration
{
    protected int activeTab;
    protected String[] musicExtensions;
    protected String musicFolderRename;
    protected String musicFolderTag;
    protected String musicYamlFolderTag;
    protected String musicYamlFileTag;
    protected String[] pictureExtensions;
    protected String pictureFolderRename;
    protected String pictureFolderSynchronize;

    public int getActiveTab() {
        return activeTab;
    }

    public void setActiveTab(int activeTab) {
        this.activeTab = activeTab;
    }

    public String[] getMusicExtensions() {
        return musicExtensions;
    }

    public void setMusicExtensions(String[] musicExtensions) {
        this.musicExtensions = musicExtensions;
    }

    public String getMusicFolderRename() {
        return musicFolderRename;
    }

    public void setMusicFolderRename(String musicFolderRename) {
        this.musicFolderRename = musicFolderRename;
    }

    public String getMusicFolderTag() {
        return musicFolderTag;
    }

    public void setMusicFolderTag(String musicFolderTag) {
        this.musicFolderTag = musicFolderTag;
    }

    public String getMusicYamlFolderTag() {
        return musicYamlFolderTag;
    }

    public void setMusicYamlFolderTag(String musicYamlFolderTag) {
        this.musicYamlFolderTag = musicYamlFolderTag;
    }

    public String getMusicYamlFileTag() {
        return musicYamlFileTag;
    }

    public void setMusicYamlFileTag(String musicYamlFileTag) {
        this.musicYamlFileTag = musicYamlFileTag;
    }

    public String[] getPictureExtensions() {
        return pictureExtensions;
    }

    public void setPictureExtensions(String[] pictureExtensions) {
        this.pictureExtensions = pictureExtensions;
    }

    public String getPictureFolderRename() {
        return pictureFolderRename;
    }

    public void setPictureFolderRename(String pictureFolderRename) {
        this.pictureFolderRename = pictureFolderRename;
    }

    public String getPictureFolderSynchronize() {
        return pictureFolderSynchronize;
    }

    public void setPictureFolderSynchronize(String pictureFolderSynchronize) {
        this.pictureFolderSynchronize = pictureFolderSynchronize;
    }
}

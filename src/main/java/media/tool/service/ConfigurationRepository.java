package media.tool.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Properties;

import media.tool.model.Configuration;

public class ConfigurationRepository
{
    protected static String CONFIG_FILENAME = "config.ini";

    protected static String ACTIVE_TAB = "active.tab";
    protected static String MUSIC_EXTENSIONS = "music.extensions";
    protected static String MUSIC_FOLDER_RENAME = "music.folder.rename";
    protected static String PICTURE_EXTENSIONS = "picture.extensions";
    protected static String PICTURE_FOLDER_RENAME = "picture.folder.rename";
    protected static String PICTURE_FOLDER_SYNCHRONIZE = "picture.folder.synchronize";

    protected static ConfigurationRepository configurationRepository = null;

    protected File configFile = null;
    protected Properties properties = null;

    protected ConfigurationRepository()
    {
        this.configFile = new File(String.format("%s%s%s",
            CurrentPathResolver.getCurrentPath(),
            File.separator,
            CONFIG_FILENAME
        ));
        this.loadProperties();
    }

    public static ConfigurationRepository getInstance()
    {
        if (ConfigurationRepository.configurationRepository == null) {
            ConfigurationRepository.configurationRepository = new ConfigurationRepository();
        }

        return ConfigurationRepository.configurationRepository;
    }

    public Configuration get()
    {
        Configuration configuration = new Configuration();

        configuration.setActiveTab(Integer.parseInt(this.properties.getProperty(ACTIVE_TAB)));
        configuration.setMusicExtensions(this.properties.getProperty(MUSIC_EXTENSIONS).split(";"));
        configuration.setMusicFolderRename(this.properties.getProperty(MUSIC_FOLDER_RENAME));
        configuration.setPictureExtensions(this.properties.getProperty(PICTURE_EXTENSIONS).split(";"));
        configuration.setPictureFolderRename(this.properties.getProperty(PICTURE_FOLDER_RENAME));
        configuration.setPictureFolderSynchronize(this.properties.getProperty(PICTURE_FOLDER_SYNCHRONIZE));

        return configuration;
    }

    public void save(Configuration configuration)
    {
        this.saveProperties(configuration);
        FileOutputStream outputStream = null;

        try {
            outputStream = new FileOutputStream(this.configFile);
            this.properties.store(outputStream, null);
        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    protected void loadProperties()
    {
        InputStream inputStream = null;

        try {
            if (!this.configFile.exists()) {
                inputStream = ClassLoader.getSystemResourceAsStream("config.ini");
            } else {
                inputStream = new FileInputStream(this.configFile);
            }

            this.properties = new Properties();
            this.properties.load(inputStream);
        } catch (Exception e1) {
        } finally {
            try {
                inputStream.close();
            } catch (Exception e2) {
            }
        }
    }

    protected void saveProperties(Configuration configuration)
    {
        this.properties.setProperty(ACTIVE_TAB, Integer.toString(configuration.getActiveTab()));
        this.properties.setProperty(MUSIC_FOLDER_RENAME, configuration.getMusicFolderRename());
        this.properties.setProperty(PICTURE_FOLDER_RENAME, configuration.getPictureFolderRename());
        this.properties.setProperty(PICTURE_FOLDER_SYNCHRONIZE, configuration.getPictureFolderSynchronize());
    }
}

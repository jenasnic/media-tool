package service.processor;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.yaml.snakeyaml.Yaml;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.ID3v24Tag;
import com.mpatric.mp3agic.Mp3File;

import filter.MusicFileFilter;
import model.OperationType;
import model.ProcessOperation;
import model.YamlProperties;
import service.FileCounter;
import service.FileFinder;

/**
 * Allows to tag music using yaml file.
 */
public class MusicTagYamlProcessor extends AbstractProcessor
{
    protected String folderToProcess;
    protected String yamlFilename;
    protected boolean forceTagClear;

    protected FileFinder fileFinder;
    protected FileCounter fileCounter;

    public MusicTagYamlProcessor(
        String folderToProcess,
        String yamlFilename,
        boolean forceTagClear,
        JFrame parent,
        boolean simulate
    ) {
        super(parent, simulate);

        this.folderToProcess = folderToProcess;
        this.yamlFilename = yamlFilename;
        this.forceTagClear = forceTagClear;

        MusicFileFilter fileFilter = new MusicFileFilter(this.configuration, true);
        this.fileFinder = new FileFinder(fileFilter);
        this.fileCounter = new FileCounter(fileFilter);
    }

    @Override
    public void process()
    {
        try {
            Yaml yaml = new Yaml();
            Map<String, Object> config = yaml.load(new FileInputStream(this.yamlFilename));

            @SuppressWarnings("unchecked")
            List<Map<String, Object>> folders = (List<Map<String, Object>>)config.get("folders");

            for (Map<String, Object> folder : folders) {
                @SuppressWarnings("unchecked")
                Map<String, Object> folderProperties = (Map<String, Object>)folder.get("folder");
                YamlProperties yamlProperties = YamlProperties.buildFromMap(folderProperties);

                String folderPath = String.format("%s%s%s", this.folderToProcess, File.separator, yamlProperties.getName());

                this.processFolder(folderPath, yamlProperties, this.simulate);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this.parent, e.getMessage());
        }
    }

    @Override
    public int getTotalOperationCount()
    {
        return this.fileCounter.countFiles(this.folderToProcess);
    }

    protected void processFolder(String folderToProcess, YamlProperties yamlProperties, boolean simulate)
    {
        File[] files = this.fileFinder.getSortedFiles(folderToProcess);

        for (File file : files) {
            if (file.isDirectory()) {
                this.processFolder(file.getAbsolutePath(), yamlProperties, simulate);
            } else if (file.isFile()) {
                ID3v2 tag = this.getTag(file, yamlProperties);

                boolean success = true;
                if (!simulate) {
                    String initialFilename = file.getPath();
                    String newFilename = String.format("%s.tagged", file.getPath());

                    try {
                        Mp3File mp3 = new Mp3File(file);

                        if (this.forceTagClear) {
                            mp3.removeId3v1Tag();
                            mp3.removeId3v2Tag();
                            mp3.removeCustomTag();
                        }

                        mp3.setId3v2Tag(tag);
                        mp3.save(newFilename);

                        file.delete();
                        File taggedFile = new File(newFilename);
                        taggedFile.renameTo(new File(initialFilename));
                    } catch (Exception e) {
                        success = false;
                    }
                }

                this.addOperation(new ProcessOperation(
                    OperationType.ADD_TAG,
                    String.format("Tag file '%s' :\r\n\tartist : %s\r\n\talbum : %s\r\n\ttitle : %s\r\n\tgenre : %s",
                        file.getName(),
                        tag.getArtist(),
                        tag.getAlbum(),
                        tag.getTitle(),
                        tag.getGenreDescription()
                    ),
                    success
                ));
            }
        }
    }

    protected ID3v2 getTag(File file, YamlProperties yamlProperties)
    {
        ID3v2 tag = yamlProperties.getFilenameTagBuilder().buildTag(file.getName());
        if (null == tag) {
            tag = new ID3v24Tag();
        }

        if (null != yamlProperties.getArtist()) {
            tag.setArtist(yamlProperties.getArtist());
        }

        if (null != yamlProperties.getAlbum()) {
            tag.setAlbum(yamlProperties.getAlbum());
        } else if (yamlProperties.useFolderNameAsAlbum()) {
            tag.setAlbum(file.getParentFile().getName());
        }

        tag.setGenreDescription(yamlProperties.getTags());

        return tag;
    }
}

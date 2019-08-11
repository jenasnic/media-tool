package service.processor;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JFrame;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.ID3v24Tag;
import com.mpatric.mp3agic.Mp3File;

import filter.MusicFileFilter;
import model.FilenameTagFormat;
import model.OperationType;
import model.ProcessOperation;
import service.FileCounter;
import service.FileFinder;
import service.FilenameTagBuilder;

/**
 * Allows to tag music using specified properties.
 */
public class MusicTagProcessor extends AbstractProcessor
{
    protected String folderToProcess;
    protected String overridenArtist;
    protected String overridenAlbum;
    protected List<String> genres;

    protected FilenameTagBuilder filenameTagBuilder;
    protected FileFinder fileFinder;
    protected FileCounter fileCounter;

    public MusicTagProcessor(
        String folderToProcess,
        boolean recursive,
        FilenameTagFormat filenameTagFormat,
        String overridenArtist,
        String overridenAlbum,
        List<String> genres,
        JFrame parent,
        boolean simulate
    ) {
        super(parent, simulate);

        this.folderToProcess = folderToProcess;
        this.overridenArtist = overridenArtist;
        this.overridenAlbum = overridenAlbum;
        this.genres = genres;

        this.filenameTagBuilder = new FilenameTagBuilder(filenameTagFormat);

        MusicFileFilter fileFilter = new MusicFileFilter(this.configuration, recursive);
        this.fileFinder = new FileFinder(fileFilter);
        this.fileCounter = new FileCounter(fileFilter);
    }

    @Override
    public void process()
    {
        this.processFolder(this.folderToProcess, this.simulate);
    }

    @Override
    public int getTotalOperationCount()
    {
        return this.fileCounter.countFiles(this.folderToProcess);
    }

    protected void processFolder(String folderToProcess, boolean simulate)
    {
        File[] files = this.fileFinder.getSortedFiles(folderToProcess);

        for (File file : files) {
            if (file.isDirectory()) {
                this.processFolder(file.getAbsolutePath(), simulate);
            } else if (file.isFile()) {
                ID3v2 tag = this.getTag(file);

                boolean success = true;
                if (!simulate) {
                    String initialFilename = file.getPath();
                    String newFilename = String.format("%s.tagged", file.getPath());

                    try {
                        Mp3File mp3 = new Mp3File(file);
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

    protected ID3v2 getTag(File file)
    {
        ID3v2 tag = this.filenameTagBuilder.buildTag(file.getName());
        if (null == tag) {
            tag = new ID3v24Tag();
        }

        if (null != this.overridenArtist && !this.overridenArtist.isBlank()) {
            tag.setArtist(this.overridenArtist);
        }

        if (null != this.overridenAlbum && !this.overridenAlbum.isBlank()) {
            tag.setAlbum(this.overridenAlbum);
        }

        tag.setGenreDescription(this.genres.stream().collect(Collectors.joining("\\\\")));

        return tag;
    }
}

package service.processor;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.ID3v24Tag;
import com.mpatric.mp3agic.Mp3File;

import filter.MusicFileFilter;
import model.FilenameTagFormat;
import model.OperationType;
import model.ProcessOperation;
import service.FilenameTagBuilder;

/**
 * Allows to tag music using specified properties.
 */
public class MusicTagProcessor implements ProcessorInterface
{
    protected String folderToProcess;
    protected boolean recursive;
    protected String overridenArtist;
    protected String overridenAlbum;
    protected List<String> genres;

    protected int counter;
    protected List<ProcessOperation> operations;
    protected FilenameTagBuilder filenameTagBuilder;

    public MusicTagProcessor(
        String folderToProcess,
        boolean recursive,
        FilenameTagFormat filenameTagFormat,
        String overridenArtist,
        String overridenAlbum,
        List<String> genres
    ) {
        this.folderToProcess = folderToProcess;
        this.recursive = recursive;
        this.overridenArtist = overridenArtist;
        this.overridenAlbum = overridenAlbum;
        this.genres = genres;

        this.filenameTagBuilder = new FilenameTagBuilder(filenameTagFormat);
    }

    @Override
    public List<ProcessOperation> simulate()
    {
        this.operations = new ArrayList<ProcessOperation>();
        this.processFolder(this.folderToProcess, true);

        return this.operations;
    }

    @Override
    public int process()
    {
        this.counter = 0;
        this.processFolder(this.folderToProcess, false);

        return this.counter;
    }

    protected void processFolder(String folderToProcess, boolean simulate)
    {
        File[] files = this.getSortedFiles(folderToProcess);

        for (File file : files) {
            if (file.isDirectory()) {
                this.processFolder(file.getAbsolutePath(), simulate);
            } else if (file.isFile()) {
                ID3v2 tag = this.getTag(file);
                this.counter++;

                if (simulate) {
                    this.operations.add(new ProcessOperation(
                        OperationType.ADD_TAG,
                        String.format("Tag file '%s' :\r\n\tartist : %s\r\n\talbum : %s\r\n\ttitle : %s\r\n\tgenre : %s",
                            file.getName(),
                            tag.getArtist(),
                            tag.getAlbum(),
                            tag.getTitle(),
                            tag.getGenreDescription()
                        )
                    ));
                } else {
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
                    }
                }
            }
        }
    }

    protected File[] getSortedFiles(String folderToProcess)
    {
        File folder = new File(folderToProcess);

        File[] fileArray = folder.listFiles(new MusicFileFilter(this.recursive));

        Arrays.sort(fileArray, (File f1, File f2) -> (f1.getName().compareTo(f2.getName())));

        return fileArray;
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

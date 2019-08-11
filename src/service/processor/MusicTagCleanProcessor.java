package service.processor;

import java.io.File;

import javax.swing.JFrame;

import com.mpatric.mp3agic.Mp3File;

import filter.MusicFileFilter;
import model.OperationType;
import model.ProcessOperation;
import service.FileCounter;
import service.FileFinder;

/**
 * Allows to tag music using specified properties.
 */
public class MusicTagCleanProcessor extends AbstractProcessor
{
    protected String folderToProcess;

    protected FileFinder fileFinder;
    protected FileCounter fileCounter;

    public MusicTagCleanProcessor(
        String folderToProcess,
        boolean recursive,
        JFrame parent
    ) {
        super(parent, false);

        this.folderToProcess = folderToProcess;

        MusicFileFilter fileFilter = new MusicFileFilter(this.configuration, recursive);
        this.fileFinder = new FileFinder(fileFilter);
        this.fileCounter = new FileCounter(fileFilter);
    }

    @Override
    public void process()
    {
        this.processFolder(this.folderToProcess);
    }

    @Override
    public int getTotalOperationCount()
    {
        return this.fileCounter.countFiles(this.folderToProcess);
    }

    protected void processFolder(String folderToProcess)
    {
        File[] files = this.fileFinder.getSortedFiles(folderToProcess);

        for (File file : files) {
            if (file.isDirectory()) {
                this.processFolder(file.getAbsolutePath());
            } else if (file.isFile()) {
                String initialFilename = file.getPath();
                String newFilename = String.format("%s.tagged", file.getPath());

                boolean success = true;
                try {
                    Mp3File mp3 = new Mp3File(file);
                    mp3.removeId3v1Tag();
                    mp3.removeId3v2Tag();
                    mp3.removeCustomTag();
                    mp3.save(newFilename);

                    file.delete();
                    File taggedFile = new File(newFilename);
                    taggedFile.renameTo(new File(initialFilename));
                } catch (Exception e) {
                    success = false;
                }

                this.addOperation(new ProcessOperation(
                    OperationType.RENAME_FILE,
                    String.format("Remove tag for file '%s'", initialFilename),
                    success
                ));
            }
        }
    }
}

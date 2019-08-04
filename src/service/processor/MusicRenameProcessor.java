package service.processor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;

import filter.MusicFileFilter;
import model.OperationType;
import model.ProcessOperation;
import service.FileFinder;
import service.preprocessor.PreProcessor;

/**
 * Allows to rename music using specified parameters and processors.
 */
public class MusicRenameProcessor implements ProcessorInterface
{
    protected String folderToProcess;
    protected String prefix;
    protected String suffix;
    protected PreProcessor preProcessor;

    protected int counter;
    protected List<ProcessOperation> operations;
    protected FileFinder fileFinder;

    public MusicRenameProcessor(String folderToProcess, String prefix, String suffix, PreProcessor preProcessor)
    {
        this.folderToProcess = folderToProcess;
        this.prefix = prefix;
        this.suffix = suffix;
        this.preProcessor = preProcessor;
        this.fileFinder = new FileFinder(new MusicFileFilter());
    }

    @Override
    public List<ProcessOperation> simulate()
    {
        this.operations = new ArrayList<ProcessOperation>();
        this.processFolder(true);

        return this.operations;
    }

    @Override
    public int process()
    {
        this.counter = 0;
        this.processFolder(false);

        return this.counter;
    }

    protected void processFolder(boolean simulate)
    {
        File[] files = this.fileFinder.getSortedFiles(this.folderToProcess);

        for (File file : files) {
            String oldFilename = file.getName();
            String newFilename = this.getNewFilename(oldFilename);
            this.counter++;

            if (simulate) {
                this.operations.add(new ProcessOperation(
                    OperationType.RENAME_FILE,
                    String.format("Rename '%s' to '%s'", oldFilename, newFilename)
                ));
            } else {
                file.renameTo(new File(String.format("%s/%s", this.folderToProcess, newFilename)));
            }
        }
    }

    protected String getNewFilename(String filename)
    {
        String newFilename = String.format("%s%s", this.prefix, this.preProcessor.handle(filename));

        if (!this.suffix.isEmpty()) {
            String extension = FilenameUtils.getExtension(newFilename).toLowerCase();
            newFilename = String.format("%s%s.%s", FilenameUtils.getBaseName(newFilename), this.suffix, extension);
        }

        return newFilename;
    }
}

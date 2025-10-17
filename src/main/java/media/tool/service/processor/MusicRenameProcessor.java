package media.tool.service.processor;

import java.io.File;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.commons.io.FilenameUtils;

import media.tool.filter.MusicFileFilter;
import media.tool.model.OperationType;
import media.tool.model.ProcessOperation;
import media.tool.service.FileCounter;
import media.tool.service.FileFinder;
import media.tool.service.preprocessor.PreProcessor;

/**
 * Allows to rename music using specified parameters and processors.
 */
public class MusicRenameProcessor extends AbstractProcessor
{
    protected String folderToProcess;
    protected String prefix;
    protected String suffix;
    protected PreProcessor preProcessor;

    protected FileFinder fileFinder;
    protected FileCounter fileCounter;

    public MusicRenameProcessor(
        String folderToProcess,
        String prefix,
        String suffix,
        PreProcessor preProcessor,
        JFrame parent,
        boolean simulate
    ) {
        super(parent, simulate);

        this.folderToProcess = folderToProcess;
        this.prefix = prefix;
        this.suffix = suffix;
        this.preProcessor = preProcessor;

        MusicFileFilter fileFilter = new MusicFileFilter(this.configuration);
        this.fileFinder = new FileFinder(fileFilter);
        this.fileCounter = new FileCounter(fileFilter);
    }

    @Override
    public void process()
    {
        try {
            this.processFolder(this.simulate);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this.parent, e.getMessage());
        }
    }

    @Override
    public int getTotalOperationCount()
    {
        return this.fileCounter.countFiles(this.folderToProcess);
    }

    protected void processFolder(boolean simulate)
    {
        File[] files = this.fileFinder.getSortedFiles(this.folderToProcess);

        for (File file : files) {
            String oldFilename = file.getName();
            String newFilename = this.getNewFilename(oldFilename);

            boolean success = true;
            if (!simulate) {
                try {
                    file.renameTo(new File(String.format("%s/%s", this.folderToProcess, newFilename)));
                } catch (Exception e) {
                    success = false;
                }
            }

            this.addOperation(new ProcessOperation(
                OperationType.RENAME_FILE,
                String.format("Rename '%s' to '%s'", oldFilename, newFilename),
                success
            ));
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

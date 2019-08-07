package service.processor;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;

import org.apache.commons.io.FilenameUtils;

import filter.PictureFileFilter;
import model.OperationType;
import model.ProcessOperation;
import service.FileCounter;

/**
 * Allows to synchronize two folders removing files from target folder that doesn't exist in reference folder.
 * NOTE : comparison is made on filename ignoring extension.
 */
public class PictureSynchronizeProcessor extends AbstractProcessor
{
    protected String referenceFolder;
    protected String targetFolder;

    protected FileCounter fileCounter;

    public PictureSynchronizeProcessor(
        String referenceFolder,
        String targetFolder,
        JFrame parent,
        boolean simulate
    ) {
        super(parent, simulate);

        this.referenceFolder = referenceFolder;
        this.targetFolder = targetFolder;

        this.fileCounter = new FileCounter(new PictureFileFilter());
    }

    @Override
    public void process()
    {
        this.processFolder(this.simulate);
    }

    @Override
    public int getTotalOperationCount()
    {
        return this.fileCounter.countFiles(this.targetFolder);
    }

    protected void processFolder(boolean simulate)
    {
        List<String> referenceFilenames = this.getReferenceFilenames();

        File folder = new File(this.targetFolder);
        File[] files = folder.listFiles(new PictureFileFilter());
        Arrays.sort(files, (File f1, File f2) -> (f1.getName().compareTo(f2.getName())));

        for (File file : files) {
            String filename = FilenameUtils.getBaseName(file.getName());
            if (!referenceFilenames.contains(filename)) {
                boolean success = true;
                if (!simulate) {
                    try {
                        file.delete();
                    } catch (Exception e) {
                        success = false;
                    }
                }

                this.addOperation(new ProcessOperation(
                    OperationType.REMOVE_FILE,
                    String.format("Remove file '%s'", file.getName()),
                    success
                ));
            } else {
                this.addOperation(new ProcessOperation(
                    OperationType.IGNORE,
                    String.format("Ignore file '%s'", file.getName()),
                    true
                ));
            }
        }
    }

    protected List<String> getReferenceFilenames()
    {
        List<String> filenameList = new ArrayList<String>();

        File folder = new File(this.referenceFolder);
        File[] files = folder.listFiles(new PictureFileFilter());

        for (File file : files) {
            filenameList.add(FilenameUtils.getBaseName(file.getName()));
        }

        return filenameList;
    }
}

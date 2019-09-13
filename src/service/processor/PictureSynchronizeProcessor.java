package service.processor;

import java.awt.Desktop;
import java.awt.Desktop.Action;
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
    protected PictureFileFilter pictureFilter;

    public PictureSynchronizeProcessor(
        String referenceFolder,
        String targetFolder,
        JFrame parent,
        boolean simulate
    ) {
        super(parent, simulate);

        this.referenceFolder = referenceFolder;
        this.targetFolder = targetFolder;

        this.pictureFilter = new PictureFileFilter(this.configuration);
        this.fileCounter = new FileCounter(this.pictureFilter);
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
        File[] files = folder.listFiles(this.pictureFilter);
        Arrays.sort(files, (File f1, File f2) -> (f1.getName().compareTo(f2.getName())));

        for (File file : files) {
            String filename = FilenameUtils.getBaseName(file.getName());
            if (!referenceFilenames.contains(filename)) {
                boolean success = true;
                if (!simulate) {
                    try {
                        if (Desktop.getDesktop().isSupported(Action.MOVE_TO_TRASH)) {
                            Desktop.getDesktop().moveToTrash(file);
                        } else {
                            file.delete();
                        }
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
        File[] files = folder.listFiles(this.pictureFilter);

        for (File file : files) {
            filenameList.add(FilenameUtils.getBaseName(file.getName()));
        }

        return filenameList;
    }
}

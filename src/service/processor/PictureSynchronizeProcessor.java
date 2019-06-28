package service.processor;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FilenameUtils;

import filter.PictureFileFilter;
import model.OperationType;
import model.ProcessOperation;

/**
 * Allows to synchronize two folders removing files from target folder that doesn't exist in reference folder.
 * NOTE : comparison is made on filename ignoring extension.
 */
public class PictureSynchronizeProcessor implements ProcessorInterface
{
    protected String referenceFolder;
    protected String targetFolder;

    protected int counter;
    protected List<ProcessOperation> operations;

    public PictureSynchronizeProcessor(String referenceFolder, String targetFolder)
    {
        this.referenceFolder = referenceFolder;
        this.targetFolder = targetFolder;
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
        List<String> referenceFilenames = this.getReferenceFilenames();

        File folder = new File(this.targetFolder);
        File[] files = folder.listFiles(new PictureFileFilter());
        Arrays.sort(files, (File f1, File f2) -> (f1.getName().compareTo(f2.getName())));

        for (File file : files) {
            String filename = FilenameUtils.getBaseName(file.getName());
            if (!referenceFilenames.contains(filename)) {
                this.counter++;
                if (simulate) {
                    this.operations.add(new ProcessOperation(
                        OperationType.REMOVE_FILE,
                        String.format("Remove file '%s'", file.getName())
                    ));
                } else {
                    file.delete();
                }
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

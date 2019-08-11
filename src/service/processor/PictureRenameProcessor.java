package service.processor;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import javax.swing.JFrame;

import org.apache.commons.io.FilenameUtils;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;

import filter.PictureFileFilter;
import model.OperationType;
import model.ProcessOperation;
import service.FileCounter;
import service.FileFinder;

/**
 * Allows to rename picture using tag (taken date) or last modification date.
 */
public class PictureRenameProcessor extends AbstractProcessor
{
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");

    protected String folderToProcess;

    protected FileFinder fileFinder;
    protected FileCounter fileCounter;

    public PictureRenameProcessor(
        String folderToProcess,
        boolean recursive,
        JFrame parent,
        boolean simulate
    ) {
        super(parent, simulate);

        this.folderToProcess = folderToProcess;

        PictureFileFilter fileFilter = new PictureFileFilter(this.configuration, recursive);
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
        Map<String, Integer> filenameIndexMap = new HashMap<String, Integer>();
        File[] files = this.fileFinder.getSortedFiles(folderToProcess);

        for (File file : files) {
            if (file.isDirectory()) {
                this.processFolder(file.getAbsolutePath(), simulate);
            } else if (file.isFile()) {
                String newFilename = this.getNewFilename(file, filenameIndexMap);

                boolean success = true;
                if (!simulate) {
                    try {
                        file.renameTo(new File(String.format("%s/%s", folderToProcess, newFilename)));
                    } catch (Exception e) {
                        success = false;
                    }
                }

                this.addOperation(new ProcessOperation(
                    OperationType.RENAME_FILE,
                    String.format("Rename '%s' to '%s'", file.getName(), newFilename),
                    success
                ));
            }
        }
    }

    protected String getNewFilename(File file, Map<String, Integer> filenameIndexMap)
    {
        Date dateToUse = this.getDateFromTag(file);

        if (null == dateToUse) {
            dateToUse = new Date(file.lastModified());
        }

        String filename = dateFormat.format(dateToUse);
        String suffix = this.getDuplicatedSuffix(filename, filenameIndexMap);
        String extension = FilenameUtils.getExtension(file.getName()).toLowerCase();

        return String.format("%s%s.%s", filename, suffix, extension);
    }

    protected Date getDateFromTag(File file)
    {
        try {
            Metadata md = ImageMetadataReader.readMetadata(file);
            Collection<ExifSubIFDDirectory> dataCollection = md.getDirectoriesOfType(ExifSubIFDDirectory.class);

            // Browse all tagList for data type 'ExifSubIFDDirectory' to extract original picture date
            for (ExifSubIFDDirectory tagList : dataCollection) {
                Date date = tagList.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL, TimeZone.getDefault());
                if (date != null) {
                    return date;
                }
            }

            return null;
        }
        catch (Exception e) {
            return null;
        }
    }

    protected String getDuplicatedSuffix(String filename, Map<String, Integer> filenameIndexMap)
    {
        int index = filenameIndexMap.containsKey(filename) ? filenameIndexMap.get(filename) + 1 : 0;
        filenameIndexMap.put(filename, index);

        return index > 0 ? "(" + index + ")" : "";
    }
}

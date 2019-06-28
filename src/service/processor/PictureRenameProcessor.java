package service.processor;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.io.FilenameUtils;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;

import filter.PictureFileFilter;
import model.OperationType;
import model.ProcessOperation;

/**
 * Allows to rename picture using tag (taken date) or last modification date.
 */
public class PictureRenameProcessor implements ProcessorInterface
{
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");

    protected String folderToProcess;
    protected boolean recursive;

    protected int counter;
    protected List<ProcessOperation> operations;

    public PictureRenameProcessor(String folderToProcess, boolean recursive)
    {
        this.folderToProcess = folderToProcess;
        this.recursive = recursive;
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
        Map<String, Integer> filenameIndexMap = new HashMap<String, Integer>();
        File[] files = this.getSortedFiles(folderToProcess);

        for (File file : files) {
            if (file.isDirectory()) {
                this.processFolder(file.getAbsolutePath(), simulate);
            } else if (file.isFile()) {
                String newFilename = this.getNewFilename(file, filenameIndexMap);
                this.counter++;

                if (simulate) {
                    this.operations.add(new ProcessOperation(
                        OperationType.RENAME_FILE,
                        String.format("Rename '%s' to '%s'", file.getName(), newFilename)
                    ));
                } else {
                    file.renameTo(new File(String.format("%s/%s", folderToProcess, newFilename)));
                }
            }
        }
    }

    protected File[] getSortedFiles(String folderToProcess)
    {
        File folder = new File(folderToProcess);

        File[] fileArray = folder.listFiles(new PictureFileFilter(this.recursive));

        Arrays.sort(fileArray, (File f1, File f2) -> (f1.getName().compareTo(f2.getName())));

        return fileArray;
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

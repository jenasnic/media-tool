package action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.mpatric.mp3agic.Mp3File;

import filter.MusicFileFilter;
import service.FileFinder;
import template.component.FolderSelectionComponent;

/**
 * Define action to clear MP3 tag.
 */
public class ClearMusicTagAction implements ActionListener
{
    protected JFrame parent;
    protected FolderSelectionComponent folderSelectionComponent;
    protected int counter;

    public ClearMusicTagAction(JFrame parent, FolderSelectionComponent folderSelectionComponent)
    {
        this.parent = parent;
        this.folderSelectionComponent = folderSelectionComponent;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (null == this.folderSelectionComponent.getFolder() || this.folderSelectionComponent.getFolder().isBlank()) {
            JOptionPane.showMessageDialog(this.parent, "Invalid folder.");

            return;
        }

        int confirmation = JOptionPane.showConfirmDialog(this.parent, "All tags will be removed. Confirm process ?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (JOptionPane.YES_OPTION == confirmation) {
            this.counter = 0;
            FileFinder fileFinder = new FileFinder(new MusicFileFilter(this.folderSelectionComponent.isRecurisve()));
            this.clearTagInFolder(this.folderSelectionComponent.getFolder(), this.folderSelectionComponent.isRecurisve(), fileFinder);
            JOptionPane.showMessageDialog(this.parent, String.format("%d file(s) processed", this.counter));
        }
    }

    protected void clearTagInFolder(String folderToProcess, boolean recursive, FileFinder fileFinder)
    {
        File[] files = fileFinder.getSortedFiles(folderToProcess);

        for (File file : files) {
            if (file.isDirectory()) {
                this.clearTagInFolder(file.getAbsolutePath(), recursive, fileFinder);
            } else if (file.isFile()) {

                String initialFilename = file.getPath();
                String newFilename = String.format("%s.tagged", file.getPath());

                try {
                    Mp3File mp3 = new Mp3File(file);
                    mp3.removeId3v1Tag();
                    mp3.removeId3v2Tag();
                    mp3.removeCustomTag();
                    mp3.save(newFilename);

                    file.delete();
                    File taggedFile = new File(newFilename);
                    taggedFile.renameTo(new File(initialFilename));
                    this.counter++;
                } catch (Exception e) {
                }
            }
        }
    }
}

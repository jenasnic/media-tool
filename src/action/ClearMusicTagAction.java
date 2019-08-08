package action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker.StateValue;

import model.ProcessOperation;
import service.OperationLogger;
import service.processor.MusicTagCleanProcessor;
import template.ProgressDialog;
import template.component.FolderSelectionComponent;

/**
 * Define action to clear MP3 tag.
 */
public class ClearMusicTagAction implements ActionListener, PropertyChangeListener
{
    protected JFrame parent;
    protected FolderSelectionComponent folderSelectionComponent;
    protected ProgressDialog progressDialog;
    protected MusicTagCleanProcessor processor;

    public ClearMusicTagAction(JFrame parent, FolderSelectionComponent folderSelectionComponent)
    {
        this.parent = parent;
        this.folderSelectionComponent = folderSelectionComponent;
        this.progressDialog = new ProgressDialog(parent);
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
            this.processor = new MusicTagCleanProcessor(
                this.folderSelectionComponent.getFolder(),
                this.folderSelectionComponent.isRecurisve(),
                this.parent
            );
            this.processor.addPropertyChangeListener(this);
            this.processor.execute();

            this.progressDialog.setVisible(true);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent event)
    {
        if (event.getNewValue() instanceof Integer) {
            this.progressDialog.setProgress((int)event.getNewValue());

            return;
        }

        if (event.getNewValue() instanceof StateValue && event.getNewValue() == StateValue.DONE) {
            this.progressDialog.dispose();

            try {
                int count = this.processor.get().size();

                if (0 == count) {
                    JOptionPane.showMessageDialog(this.parent, "No file processed");
                } else {
                    JOptionPane.showMessageDialog(this.parent, String.format("%d file(s) processed", count));
                    this.logOperations(this.processor.get());
                }
            } catch (Exception ex) {
            }
        }
    }

    protected void logOperations(List<ProcessOperation> operations)
    {
        try {
            OperationLogger logger = OperationLogger.getLogger();

            for (ProcessOperation operation : operations) {
                logger.log(operation);
            }
        } catch (IOException e) {
        }
    }
}

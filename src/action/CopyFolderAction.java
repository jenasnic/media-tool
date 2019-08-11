package action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import template.component.FolderSelectionComponent;

/**
 * Define action to copy folder from reference in target.
 */
public class CopyFolderAction implements ActionListener
{
    protected FolderSelectionComponent reference;
    protected FolderSelectionComponent target;

    public CopyFolderAction(FolderSelectionComponent reference, FolderSelectionComponent target)
    {
        this.reference = reference;
        this.target = target;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String folder = this.reference.getFolder();
        this.target.setFolder(folder);
    }
}

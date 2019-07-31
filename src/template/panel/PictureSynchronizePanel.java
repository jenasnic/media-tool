package template.panel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import action.ProcessAction;
import service.processor.PictureSynchronizeProcessor;
import service.processor.ProcessorInterface;
import template.component.FolderSelectionComponent;
import template.style.GridInsets;

public class PictureSynchronizePanel extends JPanel implements ProcessorComponentInterface, ActionListener
{
    private static final long serialVersionUID = 1L;

    protected JFrame parent;

    protected FolderSelectionComponent referenceFolderComponent;
    protected FolderSelectionComponent targetFolderComponent;
    protected JButton copyButton;
    protected JButton synchronizeButton;

    public PictureSynchronizePanel(JFrame parent)
    {
        this.parent = parent;

        this.buildLayout();
        this.initActions();
    }

    @Override
    public ProcessorInterface getProcessor()
    {
        if (this.referenceFolderComponent.getFolder().isEmpty()
            || this.targetFolderComponent.getFolder().isEmpty()
        ) {
            return null;
        }

        return new PictureSynchronizeProcessor(
            this.referenceFolderComponent.getFolder(),
            this.targetFolderComponent.getFolder()
        );
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String folder = this.referenceFolderComponent.getFolder();
        this.targetFolderComponent.setFolder(folder);
    }

    protected void buildLayout()
    {
        this.setLayout(new GridBagLayout());

        String helpReferenceMessage = "Folder used as reference (i.e. files to keep in target folder).";
        String helpTargetMessage = "Folder where to remove files that doesn't exist in reference folder.\n"
            + "NOTE : Search files in reference ignoring extension (base filename only).";

        this.referenceFolderComponent = new FolderSelectionComponent("Choose reference", helpReferenceMessage, false);
        this.targetFolderComponent = new FolderSelectionComponent("Choose target", helpTargetMessage, false);
        this.copyButton = new JButton("Copy folder");
        this.synchronizeButton = new JButton("Synchronize");

        this.add(this.referenceFolderComponent, new GridBagConstraints(0, 0, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, GridInsets.MAIN_FULL, 0, 0));
        this.add(this.copyButton, new GridBagConstraints(0, 1, 1, 1, 1, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, GridInsets.MIDDLE_FULL, 0, 0));
        this.add(this.targetFolderComponent, new GridBagConstraints(0, 2, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, GridInsets.MAIN_FULL, 0, 0));

        this.add(this.synchronizeButton, new GridBagConstraints(0, 3, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, GridInsets.MAIN_FULL, 0, 0));
    }

    protected void initActions()
    {
        this.copyButton.addActionListener(this);
        this.synchronizeButton.addActionListener(new ProcessAction(this.parent, this));
    }
}

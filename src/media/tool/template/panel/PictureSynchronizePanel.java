package media.tool.template.panel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import media.tool.action.CopyFolderAction;
import media.tool.action.ProcessAction;
import media.tool.model.Configuration;
import media.tool.service.processor.AbstractProcessor;
import media.tool.service.processor.PictureSynchronizeProcessor;
import media.tool.template.component.FolderSelectionComponent;
import media.tool.template.style.GridInsets;

public class PictureSynchronizePanel extends JPanel implements ProcessorPanelInterface, ConfigurablePanelInterface
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
    public AbstractProcessor getProcessor(boolean simulate)
    {
        if (this.referenceFolderComponent.getFolder().isEmpty()
            || this.targetFolderComponent.getFolder().isEmpty()
        ) {
            return null;
        }

        return new PictureSynchronizeProcessor(
            this.referenceFolderComponent.getFolder(),
            this.targetFolderComponent.getFolder(),
            this.parent,
            simulate
        );
    }

    @Override
    public boolean isProcessorValid()
    {
        return !this.referenceFolderComponent.getFolder().isEmpty()
            && !this.targetFolderComponent.getFolder().isEmpty();
    }

    @Override
    public void loadConfiguration(Configuration configuration)
    {
        this.referenceFolderComponent.setFolder(configuration.getPictureFolderSynchronize());
    }

    @Override
    public void saveConfiguration(Configuration configuration)
    {
        configuration.setPictureFolderSynchronize(this.referenceFolderComponent.getFolder());
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
        this.copyButton.addActionListener(new CopyFolderAction(this.referenceFolderComponent, this.targetFolderComponent));
        this.synchronizeButton.addActionListener(new ProcessAction(this.parent, this));
    }
}

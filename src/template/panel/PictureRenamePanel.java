package template.panel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import action.ProcessAction;
import model.Configuration;
import service.processor.AbstractProcessor;
import service.processor.PictureRenameProcessor;
import template.component.FolderSelectionComponent;
import template.style.GridInsets;

public class PictureRenamePanel extends JPanel implements ProcessorPanelInterface, ConfigurablePanelInterface
{
    private static final long serialVersionUID = 1L;

    protected JFrame parent;

    protected FolderSelectionComponent folderSelectionComponent;
    protected JButton renameButton;

    public PictureRenamePanel(JFrame parent)
    {
        this.parent = parent;

        this.buildLayout();
        this.initActions();
    }

    @Override
    public AbstractProcessor getProcessor(boolean simulate)
    {
        if (this.folderSelectionComponent.getFolder().isEmpty()) {
            return null;
        }

        return new PictureRenameProcessor(
            this.folderSelectionComponent.getFolder(),
            this.folderSelectionComponent.isRecurisve(),
            this.parent,
            simulate
        );
    }

    @Override
    public boolean isProcessorValid()
    {
        return !this.folderSelectionComponent.getFolder().isEmpty();
    }

    @Override
    public void loadConfiguration(Configuration configuration)
    {
        this.folderSelectionComponent.setFolder(configuration.getPictureFolderRename());
    }

    @Override
    public void saveConfiguration(Configuration configuration)
    {
        configuration.setPictureFolderRename(this.folderSelectionComponent.getFolder());
    }

    protected void buildLayout()
    {
        this.setLayout(new GridBagLayout());

        this.folderSelectionComponent = new FolderSelectionComponent(null, null, true);
        this.renameButton = new JButton("Rename");

        this.add(this.folderSelectionComponent, new GridBagConstraints(0, 0, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, GridInsets.MAIN_FULL, 0, 0));
        this.add(this.renameButton, new GridBagConstraints(0, 2, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, GridInsets.MAIN_FULL, 0, 0));
    }

    protected void initActions()
    {
        this.renameButton.addActionListener(new ProcessAction(this.parent, this));
    }
}

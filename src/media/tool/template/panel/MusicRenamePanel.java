package media.tool.template.panel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import media.tool.action.ProcessAction;
import media.tool.model.Configuration;
import media.tool.service.preprocessor.PreProcessor;
import media.tool.service.processor.AbstractProcessor;
import media.tool.service.processor.MusicRenameProcessor;
import media.tool.template.component.FolderSelectionComponent;
import media.tool.template.component.MusicRenameInfoComponent;
import media.tool.template.component.PreProcessorComponent;
import media.tool.template.style.GridInsets;

public class MusicRenamePanel extends JPanel implements ProcessorPanelInterface, ConfigurablePanelInterface
{
    private static final long serialVersionUID = 1L;

    protected JFrame parent;
    protected PreProcessor preProcessor;

    protected MusicRenameInfoComponent musicInfoComponent;
    protected FolderSelectionComponent folderSelectionComponent;
    protected JButton renameButton;

    public MusicRenamePanel(JFrame parent, PreProcessor preProcessor)
    {
        this.parent = parent;
        this.preProcessor = preProcessor;

        this.buildLayout();
        this.initActions();
    }

    @Override
    public AbstractProcessor getProcessor(boolean simulate)
    {
        if (this.folderSelectionComponent.getFolder().isEmpty()) {
            return null;
        }

        return new MusicRenameProcessor(
            this.folderSelectionComponent.getFolder(),
            this.getPrefix(),
            this.getSuffix(),
            this.preProcessor,
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
        this.folderSelectionComponent.setFolder(configuration.getMusicFolderRename());
    }

    @Override
    public void saveConfiguration(Configuration configuration)
    {
        configuration.setMusicFolderRename(this.folderSelectionComponent.getFolder());
    }

    protected void buildLayout()
    {
        this.setLayout(new GridBagLayout());

        this.folderSelectionComponent = new FolderSelectionComponent();
        this.musicInfoComponent = new MusicRenameInfoComponent();
        this.renameButton = new JButton("Rename");

        this.add(this.folderSelectionComponent, new GridBagConstraints(0, 0, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, GridInsets.TOP_FULL, 0, 0));
        this.add(this.musicInfoComponent, new GridBagConstraints(0, 1, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, GridInsets.MIDDLE_FULL, 0, 0));
        this.add(
            new PreProcessorComponent(this.preProcessor.getComponents()),
            new GridBagConstraints(0, 2, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, GridInsets.BOTTOM_FULL, 0, 0)
        );

        this.add(this.renameButton, new GridBagConstraints(0, 3, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, GridInsets.MAIN_FULL, 0, 0));
    }

    protected void initActions()
    {
        this.renameButton.addActionListener(new ProcessAction(this.parent, this));
    }

    protected String getPrefix()
    {
        if (!this.musicInfoComponent.getArtistOrAlbum().trim().isEmpty()) {
            return String.format("%s - ", this.musicInfoComponent.getArtistOrAlbum());
        }

        return "";
    }

    protected String getSuffix()
    {
        if (!this.musicInfoComponent.getAdditionnalInfos().trim().isEmpty()) {
            return String.format(" (%s)", this.musicInfoComponent.getAdditionnalInfos());
        }

        return "";
    }
}

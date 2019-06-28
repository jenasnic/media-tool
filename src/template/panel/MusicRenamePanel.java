package template.panel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import action.ProcessAction;
import service.preprocessor.PreProcessor;
import service.processor.MusicRenameProcessor;
import template.component.FolderSelectionComponent;
import template.component.MusicRenameInfoComponent;
import template.component.PreProcessorComponent;
import template.style.GridInsets;

public class MusicRenamePanel extends JPanel implements ProcessorComponentInterface
{
    private static final long serialVersionUID = 1L;

    protected JFrame parent;
    protected PreProcessor preProcessor;

    protected MusicRenameInfoComponent musicInfoComponent;
    protected FolderSelectionComponent folderSelectionComponent;
    protected JButton simulateButton;
    protected JButton renameButton;

    public MusicRenamePanel(JFrame parent, PreProcessor preProcessor)
    {
        this.parent = parent;
        this.preProcessor = preProcessor;

        this.folderSelectionComponent = new FolderSelectionComponent();
        this.musicInfoComponent = new MusicRenameInfoComponent();
        this.simulateButton = new JButton("Simulate");
        this.renameButton = new JButton("OK");

        this.buildLayout();
        this.initActions();
    }

    @Override
    public MusicRenameProcessor getProcessor()
    {
        if (this.folderSelectionComponent.getFolder().isEmpty()) {
            return null;
        }

        return new MusicRenameProcessor(
            this.folderSelectionComponent.getFolder(),
            this.getPrefix(),
            this.getSuffix(),
            this.preProcessor
        );
    }

    protected void buildLayout()
    {
        this.setLayout(new GridBagLayout());

        this.add(this.folderSelectionComponent, new GridBagConstraints(0, 0, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, GridInsets.TOP_FULL, 0, 0));
        this.add(this.musicInfoComponent, new GridBagConstraints(0, 1, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, GridInsets.MIDDLE_FULL, 0, 0));
        this.add(
            new PreProcessorComponent(this.preProcessor.getComponents()),
            new GridBagConstraints(0, 2, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, GridInsets.BOTTOM_FULL, 0, 0)
        );

        JPanel buttons = new JPanel();
        buttons.add(this.simulateButton);
        buttons.add(this.renameButton);
        this.add(buttons, new GridBagConstraints(0, 3, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, GridInsets.MAIN_FULL, 0, 0));
    }

    protected void initActions()
    {
        this.simulateButton.addActionListener(new ProcessAction(this.parent, this, true));
        this.renameButton.addActionListener(new ProcessAction(this.parent, this, false));
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

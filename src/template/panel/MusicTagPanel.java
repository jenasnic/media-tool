package template.panel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import action.ClearMusicTagAction;
import action.ProcessAction;
import service.processor.MusicTagProcessor;
import service.processor.ProcessorInterface;
import template.component.FolderSelectionComponent;
import template.component.MusicGenreComponent;
import template.component.MusicTagInfoComponent;
import template.style.GridInsets;

public class MusicTagPanel extends JPanel implements ProcessorComponentInterface
{
    private static final long serialVersionUID = 1L;

    protected JFrame parent;

    protected FolderSelectionComponent folderSelectionComponent;
    protected MusicTagInfoComponent musicInfoComponent;
    protected MusicGenreComponent musicGenreComponent;
    protected JButton clearButton;
    protected JButton tagButton;

    public MusicTagPanel(JFrame parent)
    {
        this.parent = parent;

        this.folderSelectionComponent = new FolderSelectionComponent(null, null, true);
        this.musicInfoComponent = new MusicTagInfoComponent();
        this.musicGenreComponent = new MusicGenreComponent();
        this.clearButton = new JButton("Clear");
        this.tagButton = new JButton("Tag");

        this.buildLayout();
        this.initActions();
    }

    @Override
    public ProcessorInterface getProcessor()
    {
        if (this.folderSelectionComponent.getFolder().isEmpty()) {
            return null;
        }

        return new MusicTagProcessor(
            this.folderSelectionComponent.getFolder(),
            this.folderSelectionComponent.isRecurisve(),
            this.musicInfoComponent.getFilenameFormat(),
            this.musicInfoComponent.getOverridenArtist(),
            this.musicInfoComponent.getOverridenAlbum(),
            this.musicGenreComponent.getSelectedGenres()
        );
    }

    protected void buildLayout()
    {
        this.setLayout(new GridBagLayout());

        this.add(this.folderSelectionComponent, new GridBagConstraints(0, 0, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, GridInsets.TOP_FULL, 0, 0));
        this.add(this.musicInfoComponent, new GridBagConstraints(0, 1, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, GridInsets.MIDDLE_FULL, 0, 0));
        this.add(this.musicGenreComponent, new GridBagConstraints(0, 2, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, GridInsets.MIDDLE_FULL, 0, 0));

        JPanel buttons = new JPanel();
        buttons.add(this.clearButton);
        buttons.add(this.tagButton);
        this.add(buttons, new GridBagConstraints(0, 3, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, GridInsets.MAIN_FULL, 0, 0));
    }

    protected void initActions()
    {
        this.clearButton.addActionListener(new ClearMusicTagAction(this.parent, this.folderSelectionComponent));
        this.tagButton.addActionListener(new ProcessAction(this.parent, this));
    }
}

package template.panel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import action.ProcessAction;
import filter.YamlFileFilter;
import model.Configuration;
import service.processor.AbstractProcessor;
import service.processor.MusicTagYamlProcessor;
import template.component.FileSelectionComponent;
import template.component.FolderSelectionComponent;
import template.style.GridInsets;

public class MusicTagYamlPanel extends JPanel implements ProcessorPanelInterface, ConfigurablePanelInterface
{
    private static final long serialVersionUID = 1L;

    protected JFrame parent;

    protected FolderSelectionComponent folderSelectionComponent;
    protected FileSelectionComponent fileSelectionComponent;
    protected JCheckBox forceClearCheckbox;
    protected JButton clearButton;
    protected JButton tagButton;

    public MusicTagYamlPanel(JFrame parent)
    {
        this.parent = parent;

        this.buildLayout();
        this.initActions();
    }

    @Override
    public AbstractProcessor getProcessor(boolean simulate)
    {
        if (this.folderSelectionComponent.getFolder().isEmpty() || this.fileSelectionComponent.getFile().isEmpty()) {
            return null;
        }

        return new MusicTagYamlProcessor(
            this.folderSelectionComponent.getFolder(),
            this.fileSelectionComponent.getFile(),
            this.forceClearCheckbox.isSelected(),
            this.parent,
            simulate
        );
    }

    @Override
    public boolean isProcessorValid()
    {
        return !this.folderSelectionComponent.getFolder().isEmpty() && !this.fileSelectionComponent.getFile().isEmpty();
    }

    @Override
    public void loadConfiguration(Configuration configuration)
    {
        this.folderSelectionComponent.setFolder(configuration.getMusicYamlFolderTag());
        this.fileSelectionComponent.setFile(configuration.getMusicYamlFileTag());
    }

    @Override
    public void saveConfiguration(Configuration configuration)
    {
        configuration.setMusicYamlFolderTag(this.folderSelectionComponent.getFolder());
        configuration.setMusicYamlFileTag(this.fileSelectionComponent.getFile());
    }

    protected void buildLayout()
    {
        this.setLayout(new GridBagLayout());

        YamlFileFilter fileFilter = new YamlFileFilter();
        this.folderSelectionComponent = new FolderSelectionComponent();
        this.fileSelectionComponent = new FileSelectionComponent(null, null, fileFilter);
        this.forceClearCheckbox = new JCheckBox("Force clear", true);
        this.tagButton = new JButton("Tag");

        this.add(this.folderSelectionComponent, new GridBagConstraints(0, 0, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, GridInsets.TOP_FULL, 0, 0));
        this.add(this.fileSelectionComponent, new GridBagConstraints(0, 1, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, GridInsets.MIDDLE_FULL, 0, 0));
        this.add(this.forceClearCheckbox, new GridBagConstraints(0, 2, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, GridInsets.BOTTOM_FULL, 0, 0));

        JPanel buttons = new JPanel();
        buttons.add(this.tagButton);
        this.add(buttons, new GridBagConstraints(0, 3, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, GridInsets.MAIN_FULL, 0, 0));
    }

    protected void initActions()
    {
        this.tagButton.addActionListener(new ProcessAction(this.parent, this));
    }
}

package template.panel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import action.ProcessAction;
import service.processor.PictureRenameProcessor;
import template.component.FolderSelectionComponent;
import template.style.GridInsets;

public class PictureRenamePanel extends JPanel implements ProcessorComponentInterface
{
    private static final long serialVersionUID = 1L;

    protected JFrame parent;

    protected FolderSelectionComponent folderSelectionComponent;
    protected JButton simulateButton;
    protected JButton renameButton;

    public PictureRenamePanel(JFrame parent)
    {
        this.parent = parent;

        this.folderSelectionComponent = new FolderSelectionComponent(null, null, true);
        this.simulateButton = new JButton("Simulate");
        this.renameButton = new JButton("OK");

        this.buildLayout();
        this.initActions();
    }

    @Override
    public PictureRenameProcessor getProcessor()
    {
        if (this.folderSelectionComponent.getFolder().isEmpty()) {
            return null;
        }

        return new PictureRenameProcessor(
            this.folderSelectionComponent.getFolder(),
            this.folderSelectionComponent.isRecurisve()
        );
    }

    protected void buildLayout()
    {
        this.setLayout(new GridBagLayout());

        this.add(this.folderSelectionComponent, new GridBagConstraints(0, 0, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, GridInsets.MAIN_FULL, 0, 0));

        JPanel buttons = new JPanel();
        buttons.add(this.simulateButton);
        buttons.add(this.renameButton);
        this.add(buttons, new GridBagConstraints(0, 2, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, GridInsets.MAIN_FULL, 0, 0));
    }

    protected void initActions()
    {
        this.simulateButton.addActionListener(new ProcessAction(this.parent, this, true));
        this.renameButton.addActionListener(new ProcessAction(this.parent, this, false));
    }
}

package template.panel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import action.ProcessAction;
import service.processor.PictureSynchronizeProcessor;
import service.processor.ProcessorInterface;
import template.component.FolderSelectionComponent;
import template.style.GridInsets;

public class PictureSynchronizePanel extends JPanel implements ProcessorComponentInterface
{
    private static final long serialVersionUID = 1L;

    protected JFrame parent;

    protected FolderSelectionComponent referenceFolderComponent;
    protected FolderSelectionComponent targetFolderComponent;
    protected JButton simulateButton;
    protected JButton renameButton;

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

    protected void buildLayout()
    {
        this.setLayout(new GridBagLayout());

        String helpReferenceMessage = "Folder used as reference to remove duplicated files found in target folder.\n"
            + "NOTE : Comparison is made on filename ignoring extension.";
        String helpTargetMessage = "Folder where to remove files that already exist in reference folder.\n"
            + "NOTE : Comparison is made on filename ignoring extension.";

        this.referenceFolderComponent = new FolderSelectionComponent("Choose reference", helpReferenceMessage, false);
        this.targetFolderComponent = new FolderSelectionComponent("Choose target", helpTargetMessage, false);
        this.simulateButton = new JButton("Simulate");
        this.renameButton = new JButton("OK");

        this.add(this.referenceFolderComponent, new GridBagConstraints(0, 0, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, GridInsets.MAIN_FULL, 0, 0));
        this.add(this.targetFolderComponent, new GridBagConstraints(0, 1, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, GridInsets.MAIN_FULL, 0, 0));

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

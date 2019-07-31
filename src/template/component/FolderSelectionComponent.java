package template.component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import action.ChooseDirectoryAction;
import template.style.GridInsets;

public class FolderSelectionComponent extends JPanel
{
    private static final long serialVersionUID = 1L;

    protected JTextField folderTextField;
    protected JButton browseButton;
    protected JCheckBox recursiveCheckbox;

    public FolderSelectionComponent()
    {
        this(null, null, false);
    }

    public FolderSelectionComponent(String label, String helpMessage, boolean recursiveOption)
    {
        this.folderTextField = new JTextField();
        this.browseButton = new JButton("...");
        this.recursiveCheckbox = new JCheckBox("Include sub directories");

        this.buildLayout(label, helpMessage, recursiveOption);
        this.initActions();
    }

    protected void buildLayout(String label, String helpMessage, boolean recursiveOption)
    {
        label = (null != label) ? label : "Choose folder";

        this.setLayout(new GridBagLayout());

        int height = (int)this.folderTextField.getPreferredSize().getHeight();
        this.browseButton.setPreferredSize(new Dimension(25, height));

        int gridWidth = (null == helpMessage) ? 2 : 3;

        this.add(new JLabel(label), new GridBagConstraints(0, 0, gridWidth, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.HORIZONTAL, GridInsets.TOP_LEFT, 0, 0));

        if (!recursiveOption) {
            this.add(this.folderTextField, new GridBagConstraints(0, 1, 1, 1, 1, 0, GridBagConstraints.LINE_START, GridBagConstraints.HORIZONTAL, GridInsets.BOTTOM_LEFT, 0, 0));

            if (null == helpMessage) {
                this.add(this.browseButton, new GridBagConstraints(1, 1, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, GridInsets.BOTTOM_RIGHT, 0, 0));
            } else {
                this.add(this.browseButton, new GridBagConstraints(1, 1, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, GridInsets.BOTTOM_MIDDLE, 0, 0));
                this.add(new HelperButton(this.folderTextField, helpMessage), new GridBagConstraints(2, 1, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, GridInsets.BOTTOM_RIGHT, 0, 0));
            }
        } else {
            this.add(this.folderTextField, new GridBagConstraints(0, 1, 1, 1, 1, 0, GridBagConstraints.LINE_START, GridBagConstraints.HORIZONTAL, GridInsets.MIDDLE_LEFT, 0, 0));

            if (null == helpMessage) {
                this.add(this.browseButton, new GridBagConstraints(1, 1, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, GridInsets.MIDDLE_RIGHT, 0, 0));
            } else {
                this.add(this.browseButton, new GridBagConstraints(1, 1, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, GridInsets.MIDDLE_MIDDLE, 0, 0));
                this.add(new HelperButton(this.folderTextField, helpMessage), new GridBagConstraints(2, 1, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, GridInsets.MIDDLE_RIGHT, 0, 0));
            }

            this.add(this.recursiveCheckbox, new GridBagConstraints(0, 2, gridWidth, 1, 1, 0, GridBagConstraints.LINE_START, GridBagConstraints.HORIZONTAL, GridInsets.BOTTOM_FULL, 0, 0));
        }

        this.setBorder(BorderFactory.createLineBorder(Color.GRAY));
    }

    protected void initActions()
    {
        this.browseButton.addActionListener(new ChooseDirectoryAction(this, this.folderTextField));
    }
    
    public String getFolder()
    {
        return this.folderTextField.getText();
    }

    public void setFolder(String folder)
    {
        this.folderTextField.setText(folder);
    }

    public boolean isRecurisve()
    {
        return this.recursiveCheckbox.isSelected();
    }
}

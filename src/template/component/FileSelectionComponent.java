package template.component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

import action.ChooseFileAction;
import template.style.GridInsets;

public class FileSelectionComponent extends JPanel
{
    private static final long serialVersionUID = 1L;

    protected JTextField fileTextField;
    protected JButton browseButton;

    public FileSelectionComponent()
    {
        this(null, null, null);
    }

    public FileSelectionComponent(String label, String helpMessage, FileFilter fileFilter)
    {
        this.fileTextField = new JTextField();
        this.browseButton = new JButton("...");

        this.buildLayout(label, helpMessage);
        this.initActions(fileFilter);
    }

    protected void buildLayout(String label, String helpMessage)
    {
        label = (null != label) ? label : "Choose file";

        this.setLayout(new GridBagLayout());

        int height = (int)this.fileTextField.getPreferredSize().getHeight();
        this.browseButton.setPreferredSize(new Dimension(25, height));

        int gridWidth = (null == helpMessage) ? 2 : 3;

        this.add(new JLabel(label), new GridBagConstraints(0, 0, gridWidth, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.HORIZONTAL, GridInsets.TOP_LEFT, 0, 0));

        this.add(this.fileTextField, new GridBagConstraints(0, 1, 1, 1, 1, 0, GridBagConstraints.LINE_START, GridBagConstraints.HORIZONTAL, GridInsets.BOTTOM_LEFT, 0, 0));

        if (null == helpMessage) {
            this.add(this.browseButton, new GridBagConstraints(1, 1, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, GridInsets.BOTTOM_RIGHT, 0, 0));
        } else {
            this.add(this.browseButton, new GridBagConstraints(1, 1, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, GridInsets.BOTTOM_MIDDLE, 0, 0));
            this.add(new HelperButton(this.fileTextField, helpMessage), new GridBagConstraints(2, 1, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, GridInsets.BOTTOM_RIGHT, 0, 0));
        }

        this.setBorder(BorderFactory.createLineBorder(Color.GRAY));
    }

    protected void initActions(FileFilter fileFilter)
    {
        this.browseButton.addActionListener(new ChooseFileAction(this, this.fileTextField, fileFilter));
    }

    public String getFile()
    {
        return this.fileTextField.getText();
    }

    public void setFile(String folder)
    {
        this.fileTextField.setText(folder);
    }
}

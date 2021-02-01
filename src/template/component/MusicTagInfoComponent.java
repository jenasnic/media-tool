package template.component;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.FilenameTagFormat;
import template.style.GridInsets;

public class MusicTagInfoComponent extends JPanel
{
    private static final long serialVersionUID = 1L;

    protected JComboBox<FilenameTagFormat> formatComboBox;
    protected JTextField artistTextfield;
    protected JTextField albumTextfield;
    protected JCheckBox useFolderNameAsAlbum;

    public MusicTagInfoComponent()
    {
        this.artistTextfield = new JTextField();
        this.albumTextfield = new JTextField();
        this.formatComboBox = new JComboBox<FilenameTagFormat>();
        this.useFolderNameAsAlbum = new JCheckBox("Use folder name as album");

        this.buildLayout();
    }
    
    public FilenameTagFormat getFilenameFormat()
    {
        return this.formatComboBox.getItemAt(this.formatComboBox.getSelectedIndex());
    }

    public String getOverridenArtist()
    {
        return this.artistTextfield.getText();
    }

    public String getOverridenAlbum()
    {
        return this.albumTextfield.getText();
    }

    public boolean useFolderNameAsAlbum()
    {
        return this.useFolderNameAsAlbum.isSelected();
    }

    protected void buildLayout()
    {
        this.setLayout(new GridBagLayout());

        String helpFormatMessage = "Choose filename format used to tag music.\n"
            + "NOTE : Artist and album can be overriden with both following fields (ignored if empty)."
        ;
        String helpFolderMessage = "Use folder name to tag album.\n"
            + "NOTE : This value is overriden by format or by previous field."
        ;

        for (FilenameTagFormat filenameTagFormat : FilenameTagFormat.values()) {
            this.formatComboBox.addItem(filenameTagFormat);
        }

        this.add(new JLabel("Format"), new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, GridInsets.TOP_LEFT, 0, 0));
        this.add(this.formatComboBox, new GridBagConstraints(1, 0, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, GridInsets.TOP_MIDDLE, 0, 0));
        this.add(new HelperButton(this.formatComboBox, helpFormatMessage), new GridBagConstraints(2, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, GridInsets.TOP_RIGHT, 0, 0));

        this.add(new JLabel("Artist"), new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, GridInsets.MIDDLE_LEFT, 0, 0));
        this.add(this.artistTextfield, new GridBagConstraints(1, 1, 2, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, GridInsets.MIDDLE_RIGHT, 0, 0));

        this.add(new JLabel("Album"), new GridBagConstraints(0, 2, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, GridInsets.MIDDLE_LEFT, 0, 0));
        this.add(this.albumTextfield, new GridBagConstraints(1, 2, 2, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, GridInsets.MIDDLE_RIGHT, 0, 0));

        this.add(this.useFolderNameAsAlbum, new GridBagConstraints(0, 3, 2, 1, 1, 0, GridBagConstraints.LINE_START, GridBagConstraints.HORIZONTAL, GridInsets.BOTTOM_LEFT, 0, 0));
        this.add(new HelperButton(this.formatComboBox, helpFolderMessage), new GridBagConstraints(2, 3, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, GridInsets.BOTTOM_RIGHT, 0, 0));

        this.setBorder(BorderFactory.createLineBorder(Color.GRAY));
    }
}

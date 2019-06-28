package template.component;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.FilenameTagFormat;
import model.TagType;
import template.style.GridInsets;

public class MusicTagInfoComponent extends JPanel
{
    private static final long serialVersionUID = 1L;

    protected JComboBox<FilenameTagFormat> formatComboBox;
    protected JTextField artistTextfield;
    protected JTextField albumTextfield;

    public MusicTagInfoComponent()
    {
        this.artistTextfield = new JTextField();
        this.albumTextfield = new JTextField();
        this.formatComboBox = new JComboBox<FilenameTagFormat>();

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

    protected void buildLayout()
    {
        this.setLayout(new GridBagLayout());

        String helpMessage = "Choose filename format used to tag music.\n"
            + "NOTE : Artist and album can be overriden with both following fields (ignored if empty)."
        ;

        this.addFilenameTagFormats();

        this.add(new JLabel("Format"), new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, GridInsets.TOP_LEFT, 0, 0));
        this.add(this.formatComboBox, new GridBagConstraints(1, 0, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, GridInsets.TOP_MIDDLE, 0, 0));
        this.add(new HelperButton(this.formatComboBox, helpMessage), new GridBagConstraints(2, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, GridInsets.TOP_RIGHT, 0, 0));

        this.add(new JLabel("Artist"), new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, GridInsets.MIDDLE_LEFT, 0, 0));
        this.add(this.artistTextfield, new GridBagConstraints(1, 1, 2, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, GridInsets.MIDDLE_RIGHT, 0, 0));

        this.add(new JLabel("Album"), new GridBagConstraints(0, 2, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, GridInsets.BOTTOM_LEFT, 0, 0));
        this.add(this.albumTextfield, new GridBagConstraints(1, 2, 2, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, GridInsets.BOTTOM_RIGHT, 0, 0));

        this.setBorder(BorderFactory.createLineBorder(Color.GRAY));
    }

    protected void addFilenameTagFormats()
    {
        this.formatComboBox.addItem(new FilenameTagFormat(
            "%artist% - %title%",
            Pattern.compile(String.format("^(?<%s>[^\\-]+)\\s\\-\\s(?<%s>.+)$", TagType.ARTIST.name(), TagType.TITLE.name())),
            new TagType[] {TagType.ARTIST, TagType.TITLE}
        ));

        this.formatComboBox.addItem(
            new FilenameTagFormat("%album% - %title%",
            Pattern.compile(String.format("^(?<%s>[^\\-]+)\\s\\-\\s(?<%s>.+)$", TagType.ALBUM.name(), TagType.TITLE.name())),
            new TagType[] {TagType.ALBUM, TagType.TITLE}
        ));

        this.formatComboBox.addItem(
            new FilenameTagFormat("%album% - %title% (%artist%)",
            Pattern.compile(String.format("^(?<%s>[^\\-]+)\\s\\-\\s(?<%s>[^\\(]+)\\s\\((?<%s>[^\\)]+)\\)$", TagType.ALBUM.name(), TagType.TITLE.name(), TagType.ARTIST.name())),
            new TagType[] {TagType.ALBUM, TagType.TITLE, TagType.ARTIST}
        ));

        this.formatComboBox.addItem(
            new FilenameTagFormat("%title%",
            Pattern.compile(String.format("^(?<%s>.+)$", TagType.TITLE.name())),
            new TagType[] {TagType.TITLE}
        ));

        this.formatComboBox.addItem(
            new FilenameTagFormat("%ignore% - %title%",
            Pattern.compile(String.format("^[^\\-]+\\s\\-\\s(?<%s>.+)$", TagType.TITLE.name())),
            new TagType[] {TagType.TITLE}
        ));

        this.formatComboBox.addItem(
            new FilenameTagFormat("%ignore% - %title% (%ignore%)",
            Pattern.compile(String.format("^[^\\-]+\\s\\-\\s(?<%s>[^\\(]+)\\s\\([^\\)]+\\)$", TagType.TITLE.name())),
            new TagType[] {TagType.TITLE}
        ));
    }
}

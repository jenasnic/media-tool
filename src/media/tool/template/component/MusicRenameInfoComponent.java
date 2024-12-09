package media.tool.template.component;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import media.tool.template.style.GridInsets;

/**
 * Component to define artist/album to use as prefix and additional informations to use as suffix.
 */
public class MusicRenameInfoComponent extends JPanel
{
    private static final long serialVersionUID = 1L;

    protected JTextField artistTextField;
    protected JTextField additionnalInfosTextField;

    public MusicRenameInfoComponent()
    {
        this.artistTextField = new JTextField();
        this.additionnalInfosTextField = new JTextField();

        this.buildLayout();
    }

    public String getArtistOrAlbum()
    {
        return this.artistTextField.getText();
    }

    public String getAdditionnalInfos()
    {
        return this.additionnalInfosTextField.getText();
    }

    protected void buildLayout()
    {
        this.setLayout(new GridBagLayout());

        String artistHelpMessage = "Text added before music title (after pre-processing filename)\n"
            + "NOTE : Append ' - ' between artist/album and filename."
        ;
        String additionalInfoHelpMessage = "Text added after music title (after pre-processing filename)\n"
            + "NOTE : Set infos into parentheses with blank character."
        ;

        this.add(new JLabel("Artist / Album (prefix) "), new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, GridInsets.TOP_LEFT, 0, 0));
        this.add(this.artistTextField, new GridBagConstraints(1, 0, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, GridInsets.TOP_MIDDLE, 0, 0));
        this.add(new HelperButton(this.artistTextField, artistHelpMessage), new GridBagConstraints(2, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, GridInsets.TOP_RIGHT, 0, 0));

        this.add(new JLabel("Additionnal infos (suffix) "), new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, GridInsets.BOTTOM_LEFT, 0, 0));
        this.add(this.additionnalInfosTextField, new GridBagConstraints(1, 1, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, GridInsets.BOTTOM_MIDDLE, 0, 0));
        this.add(new HelperButton(this.additionnalInfosTextField, additionalInfoHelpMessage), new GridBagConstraints(2, 1, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, GridInsets.BOTTOM_RIGHT, 0, 0));

        this.setBorder(BorderFactory.createLineBorder(Color.GRAY));
    }
}

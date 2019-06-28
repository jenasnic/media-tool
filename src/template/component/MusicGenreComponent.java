package template.component;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

public class MusicGenreComponent extends JPanel
{
    private static final long serialVersionUID = 1L;

    private final static int itemsPerColumn = 6;

    protected List<JCheckBox> genreCheckboxList;

    public MusicGenreComponent()
    {
        List<String> genres = Arrays.asList(
            "Animation",
            "Baby",
            "Blues Jazz",
            "Classical",
            "Disco Funk",
            "Electro Lounge",
            "French",
            "Hard Rock",
            "Instrumental",
            "Latina",
            "Misc",
            "Oldies",
            "Pop Dance",
            "Soft Rock",
            "Soul",
            "Soundtrack",
            "World Music"
        );

        this.genreCheckboxList = new ArrayList<JCheckBox>();
        for (String genre : genres) {
            this.genreCheckboxList.add(new JCheckBox(genre));
        }

        this.buildLayout();
    }

    public List<String> getSelectedGenres()
    {
        List<String> genres = new ArrayList<String>();

        for (JCheckBox genreCheckBox : this.genreCheckboxList) {
            if (genreCheckBox.isSelected()) {
                genres.add(genreCheckBox.getText());
            }
        }

        return genres;
    }

    protected void buildLayout()
    {
        this.setLayout(new GridBagLayout());

        Insets insets = new Insets(1, 5, 1, 5);
        int gridX = 0;
        int gridY = 0;

        for (JCheckBox genreCheckBox : this.genreCheckboxList) {
            this.add(genreCheckBox, new GridBagConstraints(gridX, gridY, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, insets, 0, 0));
            gridY++;

            if (0 == gridY % MusicGenreComponent.itemsPerColumn) {
                gridX++;
                gridY = 0;
            }
        }

        this.setBorder(BorderFactory.createLineBorder(Color.GRAY));
    }
}

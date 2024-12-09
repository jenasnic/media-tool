package media.tool.template.component.preprocessor;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

import media.tool.template.component.HelperButton;
import media.tool.template.style.GridInsets;

public class RemovePrefixNumberComponent extends JPanel
{
    private static final long serialVersionUID = 1L;

    protected JCheckBox removePrefixCheckbox;

    public RemovePrefixNumberComponent()
    {
        this.removePrefixCheckbox = new JCheckBox("Remove prefix number");

        this.buildLayout();
    }

    public boolean isSelected()
    {
        return this.removePrefixCheckbox.isSelected();
    }

    protected void buildLayout()
    {
        this.setLayout(new GridBagLayout());

        String helpMessage = "Remove number before music title.\n"
            + "NOTE : Remove parenthesis, hyphen and whitespace if found."
        ;

        this.add(this.removePrefixCheckbox, new GridBagConstraints(0, 0, 1, 1, 1, 0, GridBagConstraints.LINE_START, GridBagConstraints.HORIZONTAL, GridInsets.MIDDLE_LEFT, 0, 0));
        this.add(new HelperButton(this.removePrefixCheckbox, helpMessage), new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, GridInsets.MIDDLE_RIGHT, 0, 0));
    }
}

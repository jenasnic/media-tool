package media.tool.template.component.preprocessor;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import media.tool.template.component.HelperButton;
import media.tool.template.style.GridInsets;

public class ReplaceTextComponent extends JPanel
{
    private static final long serialVersionUID = 1L;

    protected JCheckBox replaceTextCheckbox;
    protected JTextField replaceFromTextField;
    protected JTextField replaceToTextField;

    public ReplaceTextComponent()
    {
        this.replaceTextCheckbox = new JCheckBox("Replace ");
        this.replaceFromTextField = new JTextField();
        this.replaceToTextField = new JTextField();

        this.buildLayout();
    }

    public boolean isSelected()
    {
        return this.replaceTextCheckbox.isSelected();
    }

    public String getReplaceFrom()
    {
        return this.replaceFromTextField.getText();
    }

    public String getReplaceTo()
    {
        return this.replaceToTextField.getText();
    }

    protected void buildLayout()
    {
        this.setLayout(new GridBagLayout());

        String helpMessage = "Specify text to replace using regular expressions.\n"
            + "Ex. : from \"^([0-9]+)\\s\\-\\s([a-zA-Z0-9\\s]+)\" to \"$2 [$1]\"\n"
            + "=> will revert number prefix and music title (setting number inside brackets)."
        ;

        this.add(this.replaceTextCheckbox, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, GridInsets.MIDDLE_LEFT, 0, 0));
        this.add(this.replaceFromTextField, new GridBagConstraints(1, 0, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, GridInsets.MIDDLE_MIDDLE, 0, 0));
        this.add(this.replaceToTextField, new GridBagConstraints(2, 0, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, GridInsets.MIDDLE_MIDDLE, 0, 0));
        this.add(new HelperButton(this.replaceFromTextField, helpMessage), new GridBagConstraints(3, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, GridInsets.MIDDLE_RIGHT, 0, 0));
    }
}

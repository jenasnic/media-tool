package template.component.preprocessor;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import template.component.HelperButton;
import template.style.GridInsets;

public class RemoveTextComponent extends JPanel
{
    private static final long serialVersionUID = 1L;

    protected JCheckBox removeTextCheckbox;
    protected JTextField removeTextTextField;

    public RemoveTextComponent()
    {
        this.removeTextCheckbox = new JCheckBox("Remove text");
        this.removeTextTextField = new JTextField();

        this.buildLayout();
    }

    public boolean isSelected()
    {
        return this.removeTextCheckbox.isSelected();
    }

    public String getTextToRemove()
    {
        return this.removeTextTextField.getText();
    }

    protected void buildLayout()
    {
        this.setLayout(new GridBagLayout());

        String helpMessage = "Specify text to remove in filename.";

        this.add(this.removeTextCheckbox, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, GridInsets.MIDDLE_LEFT, 0, 0));
        this.add(this.removeTextTextField, new GridBagConstraints(1, 0, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, GridInsets.MIDDLE_MIDDLE, 0, 0));
        this.add(new HelperButton(this.removeTextTextField, helpMessage), new GridBagConstraints(2, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, GridInsets.MIDDLE_RIGHT, 0, 0));
    }
}

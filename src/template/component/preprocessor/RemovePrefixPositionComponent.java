package template.component.preprocessor;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import template.component.HelperButton;
import template.style.GridInsets;

public class RemovePrefixPositionComponent extends JPanel
{
    private static final long serialVersionUID = 1L;

    protected JCheckBox startIndexCheckbox;
    protected JTextField startIndexTextField;

    public RemovePrefixPositionComponent()
    {
        this.startIndexCheckbox = new JCheckBox("Remove first characters");
        this.startIndexTextField = new JTextField();

        this.buildLayout();
    }

    public boolean isSelected()
    {
        return this.startIndexCheckbox.isSelected();
    }

    public int getStartIndex()
    {
        return Integer.parseInt(this.startIndexTextField.getText());
    }

    protected void buildLayout()
    {
        this.setLayout(new GridBagLayout());

        int height = (int)this.startIndexTextField.getPreferredSize().getHeight();
        this.startIndexTextField.setPreferredSize(new Dimension(50, height));

        String helpMessage = "Specify number of characters to remove at the begining of filename.";

        this.add(this.startIndexCheckbox, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, GridInsets.MIDDLE_LEFT, 0, 0));
        this.add(this.startIndexTextField, new GridBagConstraints(1, 0, 1, 1, 1, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, GridInsets.MIDDLE_MIDDLE, 0, 0));
        this.add(new HelperButton(this.startIndexTextField, helpMessage), new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.LINE_END, GridBagConstraints.NONE, GridInsets.MIDDLE_RIGHT, 0, 0));
    }
}

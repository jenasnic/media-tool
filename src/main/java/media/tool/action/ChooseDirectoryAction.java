package media.tool.action;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JTextField;

/**
 * Define action to select folder (set selected directory in specified TextField).
 */
public class ChooseDirectoryAction implements ActionListener
{
    protected Component parent;
    protected JTextField folderTextField;

    public ChooseDirectoryAction(Component parent, JTextField folderTextField)
    {
        this.parent = parent;
        this.folderTextField = folderTextField;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        JFileChooser fileChooser = new JFileChooser(this.folderTextField.getText());
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(this.parent)) {
            this.folderTextField.setText(fileChooser.getSelectedFile().getAbsolutePath());
        }
    }
}

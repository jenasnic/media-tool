package media.tool.action;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

/**
 * Define action to select file.
 */
public class ChooseFileAction implements ActionListener
{
    protected Component parent;
    protected JTextField folderTextField;
    protected FileFilter fileFilter;

    public ChooseFileAction(Component parent, JTextField folderTextField, FileFilter fileFilter)
    {
        this.parent = parent;
        this.folderTextField = folderTextField;
        this.fileFilter = fileFilter;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        JFileChooser fileChooser = new JFileChooser(this.folderTextField.getText());
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        if (null != this.fileFilter) {
            fileChooser.setFileFilter(this.fileFilter);
        }

        if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(this.parent)) {
            this.folderTextField.setText(fileChooser.getSelectedFile().getAbsolutePath());
        }
    }
}

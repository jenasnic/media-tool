package action;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import model.ProcessOperation;
import service.processor.ProcessorInterface;
import template.ProcessDialog;
import template.panel.ProcessorComponentInterface;

/**
 * Define generic action for components that define a processor.
 * Allows to perform action or simulate it (displaying result in dialog).
 */
public class ProcessAction implements ActionListener
{
    protected JFrame parent;
    protected ProcessorComponentInterface renamerComponent;

    public ProcessAction(JFrame parent, ProcessorComponentInterface renamerComponent)
    {
        this.parent = parent;
        this.renamerComponent = renamerComponent;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        ProcessorInterface processor = this.renamerComponent.getProcessor();

        if (null == processor) {
            JOptionPane.showMessageDialog(this.parent, "Invalid parameters or required fields missing.");

            return;
        }

        ProcessDialog processDialog = new ProcessDialog(this.parent);

        if (processDialog.isSimulate()) {
            List<ProcessOperation> renamedFiles = processor.simulate();

            if (0 == renamedFiles.size()) {
                JOptionPane.showMessageDialog(this.parent, "No file processed");
            } else {
                JOptionPane.showMessageDialog(this.parent, this.generateReport(renamedFiles));
            }
        } else if (processDialog.isProcess()) {
            int count = processor.process();
            JOptionPane.showMessageDialog(this.parent, String.format("%d file(s) processed", count));
        }
    }

    protected JScrollPane generateReport(List<ProcessOperation> operations)
    {
        JTextPane textPane = new JTextPane();
        StyledDocument doc = textPane.getStyledDocument();

        SimpleAttributeSet centeredText = new SimpleAttributeSet();
        StyleConstants.setAlignment(centeredText, StyleConstants.ALIGN_CENTER);

        for (ProcessOperation operation : operations) {
            try {
                String line = String.format("%s\n", operation.getMessage());
                doc.insertString(doc.getLength(), line, centeredText);
            } catch (BadLocationException e) {
            }
        }

        JScrollPane scrollPane = new JScrollPane(textPane);
        int width = textPane.getPreferredSize().width + 50;
        int height = Math.min(
            textPane.getPreferredSize().height,
            Toolkit.getDefaultToolkit().getScreenSize().height - 200
        );
        scrollPane.setPreferredSize(new Dimension(width, height));

        textPane.setCaretPosition(0);

        return scrollPane;
    }
}

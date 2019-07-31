package template.component;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import model.ProcessOperation;

public class OperationReportComponent extends JScrollPane
{
    private static final long serialVersionUID = 1L;

    protected Component field;
    protected String message;

    public OperationReportComponent(List<ProcessOperation> operations)
    {
        super();

        this.buildLayout(operations);
    }

    protected void buildLayout(List<ProcessOperation> operations)
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

        this.setViewportView(textPane);
    }
}

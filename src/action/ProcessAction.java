package action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import model.ProcessOperation;
import service.processor.ProcessorInterface;
import template.ProcessDialog;
import template.component.OperationReportComponent;
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
            List<ProcessOperation> operations = processor.simulate();

            if (0 == operations.size()) {
                JOptionPane.showMessageDialog(this.parent, "No file processed");
            } else {
                JOptionPane.showMessageDialog(this.parent, new OperationReportComponent(operations));
            }
        } else if (processDialog.isProcess()) {
            int count = processor.process();
            JOptionPane.showMessageDialog(this.parent, String.format("%d file(s) processed", count));
        }
    }
}

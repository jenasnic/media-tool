package action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker.StateValue;

import service.processor.AbstractProcessor;
import template.ProcessDialog;
import template.ProgressDialog;
import template.component.OperationReportComponent;
import template.panel.ProcessorPanelInterface;

/**
 * Define generic action for components that define a processor.
 * Allows to perform action or simulate it (displaying result in dialog).
 */
public class ProcessAction implements ActionListener, PropertyChangeListener
{
    protected JFrame parent;
    protected ProcessorPanelInterface processorPanel;
    protected AbstractProcessor processor;
    protected ProcessDialog processDialog;
    protected ProgressDialog progressDialog;

    public ProcessAction(JFrame parent, ProcessorPanelInterface processorPanel)
    {
        this.parent = parent;
        this.processorPanel = processorPanel;
        this.processDialog = new ProcessDialog(parent);
        this.progressDialog = new ProgressDialog(parent);
    }

    @Override
    public void actionPerformed(ActionEvent event)
    {
        if (!this.processorPanel.isProcessorValid()) {
            JOptionPane.showMessageDialog(this.parent, "Invalid parameters or required fields missing.");

            return;
        }

        this.processDialog.setVisible(true);
        if (this.processDialog.isCancel()) {
            return;
        }

        this.processor = this.processorPanel.getProcessor(this.processDialog.isSimulate());
        this.processor.addPropertyChangeListener(this);
        this.processor.execute();

        this.progressDialog.setVisible(true);
    }

    @Override
    public void propertyChange(PropertyChangeEvent event)
    {
        if (event.getNewValue() instanceof Integer) {
            this.progressDialog.setProgress((int)event.getNewValue());

            return;
        }

        if (event.getNewValue() instanceof StateValue && event.getNewValue() == StateValue.DONE) {
            this.progressDialog.dispose();

            try {
                int count = this.processor.get().size();

                if (0 == count) {
                    JOptionPane.showMessageDialog(this.parent, "No file processed");
                } else {
                    if (this.processDialog.isSimulate()) {
                        JOptionPane.showMessageDialog(this.parent, new OperationReportComponent(this.processor.get()));
                    } else {
                        JOptionPane.showMessageDialog(this.parent, String.format("%d file(s) processed", count));
                    }
                }
            } catch (Exception ex) {
            }
        }
    }
}

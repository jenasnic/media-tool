package media.tool.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker.StateValue;

import media.tool.model.ProcessOperation;
import media.tool.service.OperationLogger;
import media.tool.service.processor.AbstractProcessor;
import media.tool.template.ProcessDialog;
import media.tool.template.ProgressDialog;
import media.tool.template.component.OperationReportComponent;
import media.tool.template.panel.ProcessorPanelInterface;

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
                        this.logOperations(this.processor.get());
                    }
                }
            } catch (Exception ex) {
            }
        }
    }

    protected void logOperations(List<ProcessOperation> operations)
    {
        try {
            OperationLogger logger = OperationLogger.getLogger();

            for (ProcessOperation operation : operations) {
                logger.log(operation);
            }
        } catch (IOException e) {
        }
    }
}

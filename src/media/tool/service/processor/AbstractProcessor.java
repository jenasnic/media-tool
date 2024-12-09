package media.tool.service.processor;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingWorker;

import media.tool.model.Configuration;
import media.tool.model.ProcessOperation;
import media.tool.service.ConfigurationRepository;

public abstract class AbstractProcessor extends SwingWorker<List<ProcessOperation>, Void>
{
    protected Configuration configuration;
    protected JFrame parent;
    protected boolean simulate;

    private List<ProcessOperation> operations;
    private int totalOperationCount;
    private float step;
    private float progress;

    public AbstractProcessor(JFrame parent, boolean simulate)
    {
        this.configuration = ConfigurationRepository.getInstance().get();

        this.parent = parent;
        this.simulate = simulate;
    }

    @Override
    public List<ProcessOperation> doInBackground()
    {
        this.operations = new ArrayList<ProcessOperation>();

        this.totalOperationCount = this.getTotalOperationCount();
        this.step = (float)100/this.totalOperationCount;
        this.progress = 0;

        this.process();

        return this.operations;
    }

    @Override
    public void done()
    {
        this.setProgress(100);
    }

    protected void addOperation(ProcessOperation processOperation)
    {
        this.operations.add(processOperation);

        this.progress += this.step;
        int tempProgress = Math.round(this.progress);

        if (tempProgress > this.getProgress()) {
            this.setProgress(Math.min(99, tempProgress));
        }
    }

    public abstract void process();

    public abstract int getTotalOperationCount();
}

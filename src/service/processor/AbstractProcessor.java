package service.processor;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingWorker;

import model.Configuration;
import model.ProcessOperation;
import service.ConfigurationRepository;

public abstract class AbstractProcessor extends SwingWorker<List<ProcessOperation>, Void>
{
    protected Configuration configuration;
    protected JFrame parent;
    protected boolean simulate;

    private List<ProcessOperation> operations;
    private int totalOperationCount;
    private int step;
    private int modulo;

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
        this.step = Math.max(1, (100 / this.totalOperationCount));
        this.modulo = Math.max(1, (this.totalOperationCount / 100));

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

        if (this.operations.size() % this.modulo == 0) {
            this.setProgress(this.getProgress() + this.step);
        }
    }

    public abstract void process();

    public abstract int getTotalOperationCount();
}

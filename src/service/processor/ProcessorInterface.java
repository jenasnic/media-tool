package service.processor;

import java.util.List;

import model.ProcessOperation;

/**
 * Interface for processors with both action 'simulate' or 'process' (perform).
 */
public interface ProcessorInterface
{
    public List<ProcessOperation> simulate();

    public int process();
}

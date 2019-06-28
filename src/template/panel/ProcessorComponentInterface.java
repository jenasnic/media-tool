package template.panel;

import service.processor.ProcessorInterface;

/**
 * Interface for components that define a processor built with custom parameters (depending on component).
 */
public interface ProcessorComponentInterface
{
    public ProcessorInterface getProcessor();
}

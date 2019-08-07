package template.panel;

import service.processor.AbstractProcessor;

/**
 * Interface for panels that define a processor built with custom parameters (depending on components).
 */
public interface ProcessorPanelInterface
{
    public AbstractProcessor getProcessor(boolean simulate);

    public boolean isProcessorValid();
}

package service.preprocessor;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

/**
 * Entry point service to apply processors in order to clean base filename (removing prefix...).
 */
public class PreProcessor
{
    protected List<PreProcessorInterface> preProcessors;

    public PreProcessor()
    {
        this.preProcessors = new ArrayList<PreProcessorInterface>();
    }

    public void addProcessor(PreProcessorInterface processor)
    {
        this.preProcessors.add(processor);
    }

    public String handle(String fileName)
    {
        String newFileName = fileName;
        for (PreProcessorInterface processor : this.preProcessors) {
            newFileName = processor.handle(newFileName);
        }

        return newFileName;
    }

    public List<Component> getComponents()
    {
        List<Component> components = new ArrayList<Component>();
        for (PreProcessorInterface processor : this.preProcessors) {
            components.add(processor.getComponent());
        }

        return components;
    }
}

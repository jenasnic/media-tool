package service.preprocessor;

import java.awt.Component;

public interface PreProcessorInterface
{
    public String handle(String fileName);

    public Component getComponent();
}

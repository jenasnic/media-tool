package media.tool.service.preprocessor;

import java.awt.Component;

import media.tool.template.component.preprocessor.RemovePrefixNumberComponent;

public class RemovePrefixNumberPreProcessor implements PreProcessorInterface
{
    private static String REGEXP = "^(\\()?[0-9]+(\\))?(\\s)?(\\-)?\\s";

    protected RemovePrefixNumberComponent component;

    public RemovePrefixNumberPreProcessor()
    {
        this.component = new RemovePrefixNumberComponent();
    }

    @Override
    public String handle(String fileName)
    {
        return this.component.isSelected()
            ? fileName.replaceAll(REGEXP, "")
            : fileName
        ;
    }

    @Override
    public Component getComponent()
    {
        return this.component;
    }
}

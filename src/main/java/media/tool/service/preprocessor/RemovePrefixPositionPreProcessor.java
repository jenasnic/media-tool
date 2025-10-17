package media.tool.service.preprocessor;

import java.awt.Component;

import media.tool.template.component.preprocessor.RemovePrefixPositionComponent;

public class RemovePrefixPositionPreProcessor implements PreProcessorInterface
{
    protected RemovePrefixPositionComponent component;

    public RemovePrefixPositionPreProcessor()
    {
        this.component = new RemovePrefixPositionComponent();
    }

    public String handle(String fileName)
    {
        return this.component.isSelected()
            ? fileName.substring(this.component.getStartIndex())
            : fileName
        ;
    }

    @Override
    public Component getComponent()
    {
        return this.component;
    }
}

package media.tool.service.preprocessor;

import java.awt.Component;

import media.tool.template.component.preprocessor.RemoveTextComponent;

public class RemoveTextPreProcessor implements PreProcessorInterface
{
    protected RemoveTextComponent component;

    public RemoveTextPreProcessor()
    {
        this.component = new RemoveTextComponent();
    }

    @Override
    public String handle(String fileName)
    {
        return this.component.isSelected()
            ? fileName.replace(this.component.getTextToRemove(), "")
            : fileName
        ;
    }

    @Override
    public Component getComponent() {
        return this.component;
    }
}

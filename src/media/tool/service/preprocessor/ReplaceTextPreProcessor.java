package media.tool.service.preprocessor;

import java.awt.Component;

import media.tool.template.component.preprocessor.ReplaceTextComponent;

public class ReplaceTextPreProcessor implements PreProcessorInterface
{
    protected ReplaceTextComponent component;

    public ReplaceTextPreProcessor()
    {
        this.component = new ReplaceTextComponent();
    }

    public String handle(String fileName)
    {
        return this.component.isSelected()
            ? fileName.replaceAll(this.component.getReplaceFrom(), this.component.getReplaceTo())
            : fileName
        ;
    }

    @Override
    public Component getComponent()
    {
        return this.component;
    }
}

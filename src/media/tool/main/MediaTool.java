package media.tool.main;

import media.tool.service.preprocessor.PreProcessor;
import media.tool.service.preprocessor.RemovePrefixNumberPreProcessor;
import media.tool.service.preprocessor.RemovePrefixPositionPreProcessor;
import media.tool.service.preprocessor.RemoveTextPreProcessor;
import media.tool.service.preprocessor.ReplaceTextPreProcessor;
import media.tool.template.MainWindow;

public class MediaTool
{
    public static void main(String[] args)
    {
        PreProcessor preProcessor = new PreProcessor();
        preProcessor.addProcessor(new RemovePrefixNumberPreProcessor());
        preProcessor.addProcessor(new RemovePrefixPositionPreProcessor());
        preProcessor.addProcessor(new RemoveTextPreProcessor());
        preProcessor.addProcessor(new ReplaceTextPreProcessor());

        new MainWindow(preProcessor);
    }
}

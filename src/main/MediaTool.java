package main;

import service.preprocessor.PreProcessor;
import service.preprocessor.RemovePrefixNumberPreProcessor;
import service.preprocessor.RemovePrefixPositionPreProcessor;
import service.preprocessor.RemoveTextPreProcessor;
import service.preprocessor.ReplaceTextPreProcessor;
import template.MainWindow;

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

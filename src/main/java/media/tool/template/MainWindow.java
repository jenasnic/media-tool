package media.tool.template;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import media.tool.model.Configuration;
import media.tool.service.ConfigurationRepository;
import media.tool.service.preprocessor.PreProcessor;
import media.tool.template.panel.MusicRenamePanel;
import media.tool.template.panel.PictureRenamePanel;
import media.tool.template.panel.PictureSynchronizePanel;

public class MainWindow extends JFrame
{
    private static final long serialVersionUID = 1L;

    protected ConfigurationRepository configurationRepository;

    protected JTabbedPane tabbedPane;
    protected MusicRenamePanel musicRenamePanel;
    protected PictureRenamePanel pictureRenamePanel;
    protected PictureSynchronizePanel pictureSynchronizePanel;

    public MainWindow(PreProcessor preProcessor)
    {
        this.configurationRepository = ConfigurationRepository.getInstance();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(660, 550);
        this.setResizable(false);
        this.setTitle("Media Tool");

        this.buildLayout(preProcessor);
        this.initActions();

        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    protected void buildLayout(PreProcessor preProcessor)
    {
        this.tabbedPane = new JTabbedPane();

        this.musicRenamePanel = new MusicRenamePanel(this, preProcessor);
        this.pictureRenamePanel = new PictureRenamePanel(this);
        this.pictureSynchronizePanel = new PictureSynchronizePanel(this);

        this.tabbedPane.addTab("Music renamer", this.musicRenamePanel);
        this.tabbedPane.addTab("Picture renamer", this.pictureRenamePanel);
        this.tabbedPane.addTab("Picture synchro.", this.pictureSynchronizePanel);

        this.getContentPane().add(this.tabbedPane);
    }

    protected void initActions()
    {
        Configuration configuration = configurationRepository.get();

        this.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowOpened(WindowEvent e)
            {
                tabbedPane.setSelectedIndex(configuration.getActiveTab());

                musicRenamePanel.loadConfiguration(configuration);
                pictureRenamePanel.loadConfiguration(configuration);
                pictureSynchronizePanel.loadConfiguration(configuration);
            }

            @Override
            public void windowClosing(WindowEvent e)
            {
                configuration.setActiveTab(tabbedPane.getSelectedIndex());

                musicRenamePanel.saveConfiguration(configuration);
                pictureRenamePanel.saveConfiguration(configuration);
                pictureSynchronizePanel.saveConfiguration(configuration);

                configurationRepository.save(configuration);
            }
        });
    }
}

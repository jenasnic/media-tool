package template;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import model.Configuration;
import service.ConfigurationRepository;
import service.preprocessor.PreProcessor;
import template.panel.MusicRenamePanel;
import template.panel.MusicTagPanel;
import template.panel.MusicTagYamlPanel;
import template.panel.PictureRenamePanel;
import template.panel.PictureSynchronizePanel;

public class MainWindow extends JFrame
{
    private static final long serialVersionUID = 1L;

    protected ConfigurationRepository configurationRepository;

    protected JTabbedPane tabbedPane;
    protected MusicRenamePanel musicRenamePanel;
    protected MusicTagPanel musicTagPanel;
    protected MusicTagYamlPanel musicTagYamlPanel;
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
        this.musicTagPanel = new MusicTagPanel(this);
        this.musicTagYamlPanel = new MusicTagYamlPanel(this);
        this.pictureRenamePanel = new PictureRenamePanel(this);
        this.pictureSynchronizePanel = new PictureSynchronizePanel(this);

        this.tabbedPane.addTab("Music renamer", this.musicRenamePanel);
        this.tabbedPane.addTab("Music tag", this.musicTagPanel);
        this.tabbedPane.addTab("Music tag (yml)", this.musicTagYamlPanel);
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
                musicTagPanel.loadConfiguration(configuration);
                musicTagYamlPanel.loadConfiguration(configuration);
                pictureRenamePanel.loadConfiguration(configuration);
                pictureSynchronizePanel.loadConfiguration(configuration);
            }

            @Override
            public void windowClosing(WindowEvent e)
            {
                configuration.setActiveTab(tabbedPane.getSelectedIndex());

                musicRenamePanel.saveConfiguration(configuration);
                musicTagPanel.saveConfiguration(configuration);
                musicTagYamlPanel.saveConfiguration(configuration);
                pictureRenamePanel.saveConfiguration(configuration);
                pictureSynchronizePanel.saveConfiguration(configuration);

                configurationRepository.save(configuration);
            }
        });
    }
}

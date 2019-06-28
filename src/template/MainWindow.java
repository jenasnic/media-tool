package template;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import service.preprocessor.PreProcessor;
import template.panel.MusicRenamePanel;
import template.panel.MusicTagPanel;
import template.panel.PictureRenamePanel;
import template.panel.PictureSynchronizePanel;

public class MainWindow extends JFrame
{
    private static final long serialVersionUID = 1L;

    public MainWindow(PreProcessor preProcessor)
    {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 500);
        this.setResizable(false);
        this.setTitle("Media Tool");

        this.buildLayout(preProcessor);

        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    protected void buildLayout(PreProcessor preProcessor)
    {
        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Music renamer", new MusicRenamePanel(this, preProcessor));
        tabbedPane.addTab("Music tag", new MusicTagPanel(this));
        tabbedPane.addTab("Picture renamer", new PictureRenamePanel(this));
        tabbedPane.addTab("Picture synchro.", new PictureSynchronizePanel(this));

        this.getContentPane().add(tabbedPane);
    }
}

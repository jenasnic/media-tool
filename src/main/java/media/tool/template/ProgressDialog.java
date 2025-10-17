package media.tool.template;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;

public class ProgressDialog extends JDialog
{
    private static final long serialVersionUID = 1L;

    protected JProgressBar progressBar;
    protected JTextArea outputArea;

    public ProgressDialog(JFrame parent)
    {
        super(parent, true);

        this.setSize(250, 40);
        this.setResizable(false);
        this.setUndecorated(true);
        this.setTitle("Progress");
        this.setLocationRelativeTo(parent);

        this.buildLayout();
    }

    public void setProgress(int n)
    {
        this.progressBar.setValue(n);
    }

    protected void buildLayout()
    {
        this.progressBar = new JProgressBar(0, 100);
        this.progressBar.setValue(0);
        this.progressBar.setStringPainted(true);

        this.getContentPane().add(new JLabel("Processing..."));
        this.getContentPane().add(this.progressBar);
    }
}

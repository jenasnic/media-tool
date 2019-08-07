package template;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import template.style.GridInsets;

public class ProcessDialog extends JDialog implements ActionListener
{
    private static final long serialVersionUID = 1L;

    public static final String CANCEL = "cancel";
    public static final String SIMULATE = "simulate";
    public static final String PROCESS = "process";

    protected JButton simulateButton;
    protected JButton processButton;
    protected JButton cancelButton;
    protected String result;

    public ProcessDialog(JFrame parent)
    {
        super(parent, true);

        this.setSize(350, 120);
        this.setResizable(false);
        this.setTitle("Confirm");
        this.setLocationRelativeTo(parent);

        this.buildLayout();
        this.initActions();
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        JButton source = (JButton)e.getSource();
        this.result = source.getName();
        this.dispose();
    }

    public boolean isSimulate()
    {
        return this.result == ProcessDialog.SIMULATE;
    }

    public boolean isProcess()
    {
        return this.result == ProcessDialog.PROCESS;
    }

    public boolean isCancel()
    {
        return this.result == ProcessDialog.CANCEL;
    }

    protected void buildLayout()
    {
        this.result = null;
        this.simulateButton = new JButton("Simulate");
        this.processButton = new JButton("OK");
        this.cancelButton = new JButton("Cancel");

        this.setLayout(new GridBagLayout());

        this.add(new JLabel("Confirm process or simulate action ?", SwingConstants.CENTER), new GridBagConstraints(0, 0, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, GridInsets.TOP_FULL, 0, 0));

        this.simulateButton.setName(ProcessDialog.SIMULATE);
        this.processButton.setName(ProcessDialog.PROCESS);
        this.cancelButton.setName(ProcessDialog.CANCEL);

        JPanel buttons = new JPanel();
        buttons.add(this.simulateButton);
        buttons.add(this.processButton);
        buttons.add(this.cancelButton);
        this.add(buttons, new GridBagConstraints(0, 1, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, GridInsets.MAIN_FULL, 0, 0));
    }

    protected void initActions()
    {
        this.simulateButton.addActionListener(this);
        this.processButton.addActionListener(this);
        this.cancelButton.addActionListener(this);
    }
}

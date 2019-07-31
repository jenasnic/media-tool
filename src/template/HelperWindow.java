package template;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextPane;

public class HelperWindow extends JDialog implements ActionListener
{
    private static final long serialVersionUID = 1L;

    protected Component field;
    protected JButton button;

    public HelperWindow(Component field, JButton button, String message)
    {
        super();
        this.setModal(true);
        this.setResizable(false);
        this.setUndecorated(true);

        this.field = field;
        this.button = button;

        this.buildLayout(message);

        this.button.setEnabled(false);
        this.field.requestFocus();
        this.setVisible(true);
    }

    protected void buildLayout(String message)
    {
        JButton closeButton = new JButton(new ImageIcon(ClassLoader.getSystemResource("resources/close.png")));
        closeButton.setMargin(null);
        closeButton.setBorder(null);
        closeButton.setBackground(new Color(0, 0, 0, 0));
        closeButton.setForeground(new Color(0, 0, 0, 0));
        closeButton.setOpaque(false);
        closeButton.addActionListener(this);

        JTextPane text = new JTextPane();
        text.setBackground(new Color(0, 0, 0, 0));
        text.setText(message);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.add(closeButton, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(5, 5, 0, 5), 0, 0));
        panel.add(text, new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 20, 10, 20), 0, 0));
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        this.add(panel);
        this.pack();

        Dimension dimension = this.getPreferredSize();
        Point point =  this.button.getLocationOnScreen();
        this.setLocation(point.x - dimension.width - 2, point.y - dimension.height - 2);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        this.dispose();
        this.button.setEnabled(true);
        this.field.requestFocus();
    }
}

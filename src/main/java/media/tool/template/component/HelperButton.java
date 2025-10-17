package media.tool.template.component;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import media.tool.template.HelperWindow;

public class HelperButton extends JButton implements ActionListener
{
    private static final long serialVersionUID = 1L;

    protected Component field;
    protected String message;

    public HelperButton(Component field, String message)
    {
        this.field = field;
        this.message = message;

        this.setIcon(new ImageIcon(ClassLoader.getSystemResource("icon.png")));
        this.setMargin(null);
        this.setBorder(null);
        this.setBackground(new Color(0, 0, 0, 0));
        this.setForeground(new Color(0, 0, 0, 0));
        this.setOpaque(false);

        this.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        new HelperWindow(this.field, this, this.message);
    }
}

package template.component;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import template.style.GridInsets;
import template.style.Margin;

public class PreProcessorComponent extends JPanel
{
    private static final long serialVersionUID = 1L;

    public PreProcessorComponent(List<Component> preProcessorComponents)
    {
        this.buildLayout(preProcessorComponents);
        this.setBorder(BorderFactory.createLineBorder(Color.GRAY));
    }

    protected void buildLayout(List<Component> preProcessorComponents)
    {
        this.setLayout(new GridBagLayout());

        this.add(new JLabel("Pre process"), new GridBagConstraints(
            0,
            0,
            1,
            1,
            1,
            0,
            GridBagConstraints.LINE_START,
            GridBagConstraints.HORIZONTAL,
            GridInsets.MAIN_FULL,
            0,
            0
        ));

        for (int i = 0; i < preProcessorComponents.size(); i++) {
            Component component = preProcessorComponents.get(i);
            Insets insets = (i == preProcessorComponents.size() - 1)
                ? new Insets(0, 0, Margin.BOX_PADDING_BOTTOM - Margin.VERTICAL_SPACE, 0)
                : new Insets(0, 0, 0, 0)
            ;

            this.add(component, new GridBagConstraints(0, i+1, 1, 1, 1, 0, GridBagConstraints.LINE_START, GridBagConstraints.HORIZONTAL, insets, 0, 0));
        }
    }
}

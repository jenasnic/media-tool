package template.panel;

import model.Configuration;

/**
 * Interface for panels that use configuration to initialize parameters.
 */
public interface ConfigurablePanelInterface
{
    public void loadConfiguration(Configuration configuration);

    public void saveConfiguration(Configuration configuration);
}

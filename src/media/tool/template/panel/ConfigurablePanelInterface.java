package media.tool.template.panel;

import media.tool.model.Configuration;

/**
 * Interface for panels that use configuration to initialize parameters.
 */
public interface ConfigurablePanelInterface
{
    public void loadConfiguration(Configuration configuration);

    public void saveConfiguration(Configuration configuration);
}

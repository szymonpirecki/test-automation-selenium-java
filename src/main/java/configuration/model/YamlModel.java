package configuration.model;

import lombok.Getter;

import java.util.Map;

@Getter
public class YamlModel {

    private Map<String, EnvironmentModel> environments;
    private String environment;

    private Map<String, Object> browserSettings;

    public EnvironmentModel getCurrentEnvironment() {
        return environments.get(environment);
    }
}

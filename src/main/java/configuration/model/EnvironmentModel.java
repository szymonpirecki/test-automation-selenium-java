package configuration.model;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import configuration.model.testconfig.BasketConfig;
import configuration.model.testconfig.CheckoutConfig;
import configuration.model.testconfig.FilterConfig;
import configuration.model.testconfig.SearchConfig;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

@Slf4j
@Getter
public class EnvironmentModel {

    @JsonProperty("checkoutTests")
    private CheckoutConfig checkoutConfig;

    @JsonProperty("basketTests")
    private BasketConfig basketConfig;

    @JsonProperty("filterTests")
    private FilterConfig filterConfig;

    @JsonProperty("searchTests")
    private SearchConfig searchConfig;

    @JsonAnySetter
    private final HashMap<String, Object> environmentPropertiesMap = new HashMap<>();
}

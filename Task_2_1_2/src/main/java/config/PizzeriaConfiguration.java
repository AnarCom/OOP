package config;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class PizzeriaConfiguration {
    @JsonProperty("cook_configuration")
    CookConfiguration cookConfiguration;

    @JsonProperty("delivery_configuration")
    DeliveryConfiguration deliveryConfiguration;

    @JsonProperty("warehouse_configuration")
    WarehouseConfiguration warehouseConfiguration;

    @JsonProperty("queue_limit")
    Integer queueLimit = 0;

    @JsonProperty("pizza_cooking_time_ms")
    Integer pizzaCookingTime = 0;

    public PizzeriaConfiguration() {
        cookConfiguration = new CookConfiguration();
        deliveryConfiguration = new DeliveryConfiguration();
        warehouseConfiguration = new WarehouseConfiguration();
    }

    @Getter
    @Setter
    public static class CookConfiguration {
        Integer number = 0;
        Integer[] experience = new Integer[0];
    }

    @Getter
    @Setter
    public static class DeliveryConfiguration {
        Integer number = 0;
        @JsonProperty("trunk_capacity")
        Integer [] trunkCapacity = new Integer[0];
    }

    @Getter
    @Setter
    public static class WarehouseConfiguration {
        Integer capacity = 0;
    }

}

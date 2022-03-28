package delivery;

import collection.SharedList;
import config.PizzeriaConfiguration;
import order.Order;

import java.util.ArrayList;
import java.util.List;

public class DeliveryFactory {

    public static void createDeliveryBoys(
            SharedList<Order> warehouse,
            PizzeriaConfiguration.DeliveryConfiguration configuration
    ) {
        List<Integer> backpackSizes = new ArrayList<>();
        for (int i = 0; i < configuration.getNumber(); i++) {
            if (i < configuration.getNumber()) {
                backpackSizes.add(configuration.getTrunkCapacity()[i]);
            } else {
                backpackSizes.add(1);
            }
        }

        backpackSizes.forEach((it) -> {
            new DeliveryBoy(warehouse, it).start();
        });
    }
}

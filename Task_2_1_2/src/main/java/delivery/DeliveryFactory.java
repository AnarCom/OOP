package delivery;

import collection.SharedList;
import config.PizzeriaConfiguration;
import order.Order;

import java.util.ArrayList;
import java.util.List;

public class DeliveryFactory {

    public static List<DeliveryBoy> createDeliveryBoys(
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

        List <DeliveryBoy> deliveryBoys = new ArrayList<>();
        backpackSizes.forEach((it) -> {
            var deliveryBoy = new DeliveryBoy(warehouse, it);
            deliveryBoy.start();
            deliveryBoys.add(deliveryBoy);
        });
        return deliveryBoys;
    }
}

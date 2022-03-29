package delivery;

import collection.SharedList;
import lombok.AllArgsConstructor;
import order.Order;
import order.OrderStatus;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class DeliveryBoy extends Thread {
    private final SharedList<Order> warehouse;
    private final Integer backpackSize;

    @Override
    public void run() {
        while (true) {
            List<Order> backpack;
            try {
                backpack = warehouse.reserveAndGet(backpackSize);
            } catch (InterruptedException ignored) {
                continue;
            }

            for (int i = 0; i < backpackSize; i++) {
                try {
                    var order = warehouse.get();
                    backpack.add(order);
                    order.setStatus(OrderStatus.DELIVERING);
                } catch (InterruptedException ignored) {
                }
            }
            backpack.forEach((it) -> {
                try {
                    Thread.sleep(it.getDeliveryTime());
                } catch (InterruptedException ignored) {
                }
                it.setStatus(OrderStatus.DELIVERED);
            });
        }
    }
}

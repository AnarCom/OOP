package delivery;

import collection.SharedList;
import lombok.AllArgsConstructor;
import order.Order;
import order.OrderStatus;
import thread.FiniteThread;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class DeliveryBoy extends FiniteThread {
    private final SharedList<Order> warehouse;
    private final Integer backpackSize;

    @Override
    public void run() {
        while (isCont()) {
            List<Order> backpack = new ArrayList<>();
            try {
                backpack = warehouse.reserveAndGet(backpackSize);
            } catch (InterruptedException e) {
                if(!isCont()){
                    break;
                }
            }
            System.out.printf("Delivery boy has left with %d orders\n", backpack.size());
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

package kitchen;

import collection.SharedList;
import lombok.AllArgsConstructor;
import order.Order;
import order.OrderStatus;

@AllArgsConstructor
public class Cooker extends Thread {
    private Integer workExperience;
    private SharedList<Order> ordersList;
    private Integer produceTime;
    private SharedList<Order> warehouse;

    @Override
    public void run() {
        while (true){
            try {
                var pizza = ordersList.get();
                pizza.setStatus(OrderStatus.COOKING);
                Thread.sleep(produceTime - workExperience);
                pizza.setStatus(OrderStatus.WAIT_DELIVERY);
                warehouse.add(pizza);
            } catch (InterruptedException ignored) {}

        }
    }
}

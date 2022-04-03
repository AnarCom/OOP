package kitchen;

import collection.SharedList;
import lombok.AllArgsConstructor;
import order.Order;
import order.OrderStatus;
import thread.FiniteThread;

@AllArgsConstructor
public class Cooker extends FiniteThread {
    private Integer workExperience;
    private SharedList<Order> ordersList;
    private Integer produceTime;
    private SharedList<Order> warehouse;

    @Override
    public void run() {
        while (isCont()){
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

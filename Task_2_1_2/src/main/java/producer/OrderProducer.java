package producer;

import collection.SharedList;
import lombok.AllArgsConstructor;
import order.Order;
import thread.FiniteThread;

@AllArgsConstructor
public class OrderProducer extends FiniteThread {
    private final SharedList<Order> list;

    @Override
    public void run() {
        int id = 0;
        while (this.isCont()) {
            try {
                Thread.sleep((long) (50 * Math.random()));
                list.add(new Order(id++, (int) (500 * Math.random())));
            } catch (InterruptedException ignored) {
                if(isCont()){
                    break;
                }
            }
        }
    }
}

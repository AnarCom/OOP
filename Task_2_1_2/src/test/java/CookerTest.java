import collection.SharedList;
import kitchen.Cooker;
import order.Order;
import order.OrderStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CookerTest {

    @Test
    public void CookPizzaTest() throws InterruptedException {
        SharedList<Order> ordersList = new SharedList<>(10);
        SharedList<Order> warehouse = new SharedList<>(10);

        ordersList.add(new Order(1, 1));
        ordersList.add(new Order(2, 1));

        Assertions.assertFalse(ordersList.isEmpty());
        var cooker = new Cooker(1, ordersList, 2, warehouse);
        cooker.start();
        Thread.sleep(10);
        cooker.stop();
        Assertions.assertTrue(ordersList.isEmpty());

        var order = warehouse.get();
        Assertions.assertEquals(order.getId(), 1);
        Assertions.assertEquals(order.getDeliveryTime(), 1);
        Assertions.assertEquals(order.getStatus(), OrderStatus.WAIT_DELIVERY);

        order = warehouse.get();
        Assertions.assertEquals(order.getId(), 2);
        Assertions.assertEquals(order.getDeliveryTime(), 1);
        Assertions.assertEquals(order.getStatus(), OrderStatus.WAIT_DELIVERY);
    }


    @Test
    public void CookNoPizzaTest() throws InterruptedException {
        SharedList<Order> ordersList = new SharedList<>(10);
        SharedList<Order> warehouse = new SharedList<>(10);
        
        Assertions.assertTrue(ordersList.isEmpty());
        var cooker = new Cooker(1, ordersList, 2, warehouse);
        cooker.start();
        Thread.sleep(10);
        cooker.stop();
        Assertions.assertTrue(ordersList.isEmpty());
    }

}

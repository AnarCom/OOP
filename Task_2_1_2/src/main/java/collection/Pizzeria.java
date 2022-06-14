package collection;

import delivery.DeliveryBoy;
import kitchen.Cooker;
import lombok.Data;
import order.Order;
import producer.OrderProducer;

import java.util.ArrayList;
import java.util.List;

@Data
public class Pizzeria {
    List<Cooker> cookers = new ArrayList<>();
    SharedList<Order> orderQueue;
    OrderProducer orderProducer;
    SharedList<Order> pizzaQueue;
    List<DeliveryBoy> deliveryBoys = new ArrayList<>();
}

package factory;

import collection.Pizzeria;
import delivery.DeliveryFactory;
import kitchen.KitchenFactory;
import producer.OrderProducer;
import collection.SharedList;
import config.PizzeriaConfiguration;
import order.Order;

public class PizzeriaFactory {
    public static Pizzeria produce(PizzeriaConfiguration configuration) {
        SharedList<Order> orderSharedList = new SharedList<>(configuration.getQueueLimit());
        SharedList<Order> warehouse = new SharedList<>(configuration.getQueueLimit());
        Pizzeria pizzeria = new Pizzeria();
        var orderProducer = new OrderProducer(orderSharedList);
        pizzeria.setOrderProducer(orderProducer);

        pizzeria.setCookers(
                KitchenFactory.createCookers(
                        orderSharedList,
                        configuration.getCookConfiguration(),
                        warehouse,
                        configuration.getPizzaCookingTime()
                )
        );

        pizzeria.setDeliveryBoys(
                DeliveryFactory.createDeliveryBoys(warehouse, configuration.getDeliveryConfiguration())
        );
        orderProducer.start();
        return pizzeria;
    }
}

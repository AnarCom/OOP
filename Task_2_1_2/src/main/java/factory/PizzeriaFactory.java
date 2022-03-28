package factory;

import delivery.DeliveryFactory;
import kitchen.KitchenFactory;
import producer.OrderProducer;
import collection.SharedList;
import config.PizzeriaConfiguration;
import order.Order;

public class PizzeriaFactory {
    public static void produce(PizzeriaConfiguration configuration) {
        SharedList<Order> orderSharedList = new SharedList<>(configuration.getQueueLimit());
        SharedList<Order> warehouse = new SharedList<>(configuration.getQueueLimit());
        var orderProducer = new OrderProducer(orderSharedList);

        KitchenFactory.createCookers(
                orderSharedList,
                configuration.getCookConfiguration(),
                warehouse,
                configuration.getPizzaCookingTime()
        );

        DeliveryFactory.createDeliveryBoys(warehouse, configuration.getDeliveryConfiguration());

        orderProducer.start();
    }
}

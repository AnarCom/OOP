package order;

import lombok.Getter;

@Getter
public class Order {
    int id;
    OrderStatus status;
    int deliveryTime;

    public Order(int id, int deliveryTime) {
        this.id = id;
        this.status = OrderStatus.WAIT_COOK;
        this.deliveryTime = deliveryTime;
        System.out.printf("Order %d created\n", id);
    }

    public void setStatus(OrderStatus status) {
        var oldStatus = this.status;
        this.status = status;
        System.out.printf("Order %d status changed from %s to %s \n", id, oldStatus, status);
    }
}

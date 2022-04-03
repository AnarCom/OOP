import collection.Pizzeria;
import thread.FiniteThread;

public class PizzeriaKiller {
    public static void killPizzeria(Pizzeria pizzeria){
        pizzeria.getOrderProducer().breakWork();
        pizzeria.getCookers().forEach(FiniteThread::breakWork);
        pizzeria.getDeliveryBoys().forEach(FiniteThread::breakWork);
    }
}

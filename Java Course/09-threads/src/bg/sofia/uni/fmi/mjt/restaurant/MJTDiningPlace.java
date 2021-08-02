package bg.sofia.uni.fmi.mjt.restaurant;

import java.util.ArrayList;

public class MJTDiningPlace implements Restaurant {

    private final ArrayList<Order> orders;
    private int numberOfSubmittedOrders;
    private int currentChefsID;
    private final Chef[] chefs;
    private boolean isRestaurantClosed;

    public MJTDiningPlace(int numberOfChefs) {
        orders = new ArrayList<>();
        chefs = new Chef[numberOfChefs];
        startWorkForChefs(chefs, numberOfChefs);
        numberOfSubmittedOrders = 0;
        currentChefsID = 0;
        isRestaurantClosed = false;
    }

    private void startWorkForChefs(Chef[] chefs, int numberOfChefs) {
        for (int i = 0; i < numberOfChefs; i++) {
            chefs[i] = new Chef(currentChefsID, this);
            currentChefsID++;
            chefs[i].start();
        }
    }

    public boolean isRestaurantOpen() {
        return !isRestaurantClosed;
    }

    public synchronized boolean isTherePendingOrder() {
        return orders.size() > 0;
    }

    @Override
    public synchronized void submitOrder(Order order) {
        orders.add(order);
        numberOfSubmittedOrders++;
        notifyAll();
    }

    @Override
    public synchronized Order nextOrder() {
        orders.sort(new OrdersComparator());
        final int indexOfFirstElement = 0;
        Order nextOrder = orders.get(indexOfFirstElement);
        orders.remove(nextOrder);
        return nextOrder;
    }

    @Override
    public int getOrdersCount() {
        return numberOfSubmittedOrders;
    }

    @Override
    public Chef[] getChefs() {
        return chefs;
    }

    @Override
    public void close() {
        isRestaurantClosed = true;
    }
}

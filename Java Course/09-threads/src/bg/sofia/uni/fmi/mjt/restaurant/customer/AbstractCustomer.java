package bg.sofia.uni.fmi.mjt.restaurant.customer;

import bg.sofia.uni.fmi.mjt.restaurant.Meal;
import bg.sofia.uni.fmi.mjt.restaurant.Order;
import bg.sofia.uni.fmi.mjt.restaurant.Restaurant;

import java.util.Random;

public abstract class AbstractCustomer extends Thread {
    private final Restaurant restaurant;
    private static final Random RANDOM_TIME_TO_CHOOSE_FROM_MENU = new Random();
    private static final int MAX_TIME_TO_CHOOSE = 4;
    private static final int MIN_TIME_TO_CHOOSE = 1;

    public AbstractCustomer(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(RANDOM_TIME_TO_CHOOSE_FROM_MENU.nextInt(MAX_TIME_TO_CHOOSE) + MIN_TIME_TO_CHOOSE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (this) {
            Meal myMeal = Meal.chooseFromMenu();
            Order myOrder = new Order(myMeal, this);
            restaurant.submitOrder(myOrder);
        }
    }

    public abstract boolean hasVipCard();

}
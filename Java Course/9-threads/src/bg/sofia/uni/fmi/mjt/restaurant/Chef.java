package bg.sofia.uni.fmi.mjt.restaurant;

public class Chef extends Thread {

    private final int id;
    private final Restaurant restaurant;
    private int currentCookingTime;
    private int numberOfCookedMeals;

    public Chef(int id, Restaurant restaurant) {
        this.id = id;
        this.restaurant = restaurant;
        numberOfCookedMeals = 0;
        currentCookingTime = 0;
    }

    public int getChefsId() {
        return this.id;
    }

    @Override
    public void run() {
        while (((MJTDiningPlace) restaurant).isRestaurantOpen()
                || ((MJTDiningPlace) restaurant).isTherePendingOrder()) {
            try {
                synchronized (this) {
                    if (!((MJTDiningPlace) restaurant).isTherePendingOrder()) {
                        final int timeToWait = 10;
                        wait(timeToWait);
                    }
                    synchronized (Order.class) {
                        if (((MJTDiningPlace) restaurant).isTherePendingOrder()) {
                            Order orderToBeDone = restaurant.nextOrder();
                            currentCookingTime = orderToBeDone.meal().getCookingTime();
                            numberOfCookedMeals++;
                        }
                    }
                }
                Thread.sleep(currentCookingTime);  // cooking
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Returns the total number of meals that this chef has cooked.
     **/
    public int getTotalCookedMeals() {
        return numberOfCookedMeals;
    }

}
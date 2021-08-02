package bg.sofia.uni.fmi.mjt.restaurant;

import bg.sofia.uni.fmi.mjt.restaurant.customer.AbstractCustomer;
import bg.sofia.uni.fmi.mjt.restaurant.customer.Customer;
import bg.sofia.uni.fmi.mjt.restaurant.customer.VipCustomer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class MJTDiningPlaceTest {

    private MJTDiningPlace diningPlace;
    private final int numberOfCustomers = 200;
    private final int numberOfChefs = 5;

    @Before
    public void setUp() {
        diningPlace = new MJTDiningPlace(numberOfChefs);
        for (int i = 0; i < numberOfCustomers; i++) {
            AbstractCustomer customer;
            if (i % 2 == 0) {
                customer = new VipCustomer(diningPlace);
            } else {
                customer = new Customer(diningPlace);
            }
            customer.start();
            try {
                customer.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        diningPlace.close();
    }

    @Test
    public void testWhetherAllOrdersAreCompleted() {
        int numberOfCompletedOrders = 0;
        for (int i = 0; i < numberOfChefs; i++) {
            try {
                diningPlace.getChefs()[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            numberOfCompletedOrders += diningPlace.getChefs()[i].getTotalCookedMeals();
        }
        Assert.assertEquals(numberOfCompletedOrders, numberOfCustomers);
    }

    @Test
    public void testWhetherAllChefsWork() {
        Set<Integer> numberOfCompletedOrdersByEveryChef = new HashSet<>();
        for (int i = 0; i < numberOfChefs; i++) {
            try {
                diningPlace.getChefs()[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            numberOfCompletedOrdersByEveryChef.add(diningPlace.getChefs()[i].getTotalCookedMeals());
        }
        Assert.assertFalse(numberOfCompletedOrdersByEveryChef.contains(0));
    }

    @Test
    public void testWhetherAllCustomersMakeCorrectOrders() {
        for (int i = 0; i < numberOfChefs; i++) {
            try {
                diningPlace.getChefs()[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Assert.assertEquals(numberOfCustomers, diningPlace.getOrdersCount());
    }
}

package bg.sofia.uni.fmi.mjt.restaurant;

import java.util.Comparator;

public class OrdersComparator implements Comparator<Order> {
    @Override
    public int compare(Order o1, Order o2) {
        boolean isO1Vip = o1.customer().hasVipCard();
        boolean isO2Vip = o2.customer().hasVipCard();
        if (isO1Vip && !isO2Vip) {
            return -1;
        }
        if (!isO1Vip && isO2Vip) {
            return 1;
        }
        return Integer.compare(o2.meal().getCookingTime(), o1.meal().getCookingTime());
    }
}

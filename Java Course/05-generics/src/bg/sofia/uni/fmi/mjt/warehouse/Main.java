package jetbrains.com;

import bg.sofia.uni.fmi.mjt.warehouse.MJTExpressWarehouse;
import bg.sofia.uni.fmi.mjt.warehouse.exceptions.CapacityExceededException;

import java.time.LocalDateTime;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws CapacityExceededException {
        //creating an instance of Warehouse
        
        MJTExpressWarehouse<String, Integer> warehouse = new MJTExpressWarehouse<>(3, 100);
        LocalDateTime ld11 = LocalDateTime.of(2020, 11, 15, 13, 13);
        LocalDateTime ld12 = LocalDateTime.of(2020, 10, 14, 13, 13);
        LocalDateTime ld2 = LocalDateTime.of(2020, 10, 15, 13, 13);
        Map<String, Integer> items;

        //showing some functionalities of the warehouse

        warehouse.submitParcel("parcel1", 1, ld11);
        warehouse.submitParcel("parcel2", 2, ld12);

        System.out.println(warehouse.getWarehouseSpaceLeft());
        items = warehouse.deliverParcelsSubmittedAfter(ld2);
        for (Map.Entry<String, Integer> entry : items.entrySet()) {
            System.out.println(entry);
        }
    }
}

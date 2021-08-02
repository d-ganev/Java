package bg.sofia.uni.fmi.mjt.warehouse;

import bg.sofia.uni.fmi.mjt.warehouse.exceptions.CapacityExceededException;
import bg.sofia.uni.fmi.mjt.warehouse.exceptions.ParcelNotFoundException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class MJTExpressWarehouse<L, P> implements DeliveryServiceWarehouse<L, P> {

    private final int capacity;
    private final int retentionPeriod;
    private final HashMap<L, P> warehouseItems;
    private final HashMap<L, LocalDateTime> submissionDates;

    public MJTExpressWarehouse(int capacity, int retentionPeriod) {
        this.capacity = capacity;
        this.retentionPeriod = retentionPeriod;
        this.warehouseItems = new HashMap<>();
        this.submissionDates = new HashMap<>();
    }

    private void checkForInputValidity(L label, P parcel, LocalDateTime submissionDate) {
        if (label == null || parcel == null || submissionDate == null) {
            throw new IllegalArgumentException("The input is null");
        }

        if (submissionDate.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("The submission date is in the future");
        }
    }

    private void chekForParcelPresence(L label) throws ParcelNotFoundException {
        if (!this.warehouseItems.containsKey(label)) {
            throw new ParcelNotFoundException("The parcel is not stored in the warehouse!");
        }
    }

    private void checkForLabelValidity(L label) {
        if (label == null) {
            throw new IllegalArgumentException("The label is null");
        }
    }

    private void checkForDateValidity(LocalDateTime date) {
        if (date == null) {
            throw new IllegalArgumentException("The date is null");
        }
    }

    private boolean hasFreeSpace() {
        return warehouseItems.size() < capacity;
    }

    private boolean isRetentionPeriodPassed(LocalDateTime submissionDate) {
        return submissionDate.plusDays(retentionPeriod).isBefore(LocalDateTime.now());
    }

    private void freeSpace() throws CapacityExceededException {
        for ( final Map.Entry<L, LocalDateTime> entry : submissionDates.entrySet()) {
            LocalDateTime submissionDateOfCurrEl = entry.getValue();
            if (isRetentionPeriodPassed(submissionDateOfCurrEl)) {
                L currLabel = entry.getKey();
                submissionDates.remove(currLabel);
                warehouseItems.remove(currLabel);
                return;
            }
        }
        throw new CapacityExceededException("There is no free space in the warehouse!");
    }

    private void removeItems(Map<L, P> itemsToBeRemoved) {
        for (Map.Entry<L, P> currItem : itemsToBeRemoved.entrySet()) {
            L currLabel = currItem.getKey();
            warehouseItems.remove(currLabel);
            submissionDates.remove(currLabel);
        }
    }

    private Map<L, P> deliverParcels(LocalDateTime date, String when) {
        if ("before".equals(when) && date.isAfter(LocalDateTime.now())) {
            return this.warehouseItems;
        }

        if ("after".equals(when) && date.isAfter(LocalDateTime.now())) {
            return Map.of();
        }

        HashMap<L, P> deliveredParcels = new HashMap<>();
        for (Map.Entry<L, P> currItem : warehouseItems.entrySet()) {
            L currLabel = currItem.getKey();
            LocalDateTime currDate = submissionDates.get(currLabel);
            if (("before".equals(when) && currDate.isBefore(date))
                    || ("after".equals(when) && currDate.isAfter(date))) {
                P currParcel = currItem.getValue();
                deliveredParcels.put(currLabel, currParcel);
            }
        }
        removeItems(deliveredParcels);
        return deliveredParcels;
    }

    @Override
    public void submitParcel(L label, P parcel, LocalDateTime submissionDate) throws CapacityExceededException {
        try {
            checkForInputValidity(label, parcel, submissionDate);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("The input is not valid!");
        }

        if (!hasFreeSpace()) {
            try {
                freeSpace();
            } catch (CapacityExceededException e) {
                throw new CapacityExceededException("There is no free space in the warehouse!");
            }
        }
        warehouseItems.put(label, parcel);
        submissionDates.put(label, submissionDate);
    }

    @Override
    public P getParcel(L label) {
        try {
            checkForLabelValidity(label);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("The input is not valid!");
        }
        if (this.warehouseItems.containsKey(label)) {
            return this.warehouseItems.get(label);
        }
        return null;
    }

    @Override
    public P deliverParcel(L label) throws ParcelNotFoundException {
        try {
            checkForLabelValidity(label);
            chekForParcelPresence(label);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("The input is not valid!");
        } catch (ParcelNotFoundException e) {
            throw new ParcelNotFoundException("The parcel is not stored in the warehouse!");
        }
        P parcel = this.warehouseItems.get(label);
        this.warehouseItems.remove(label, parcel);
        this.submissionDates.remove(label);
        return parcel;
    }

    @Override
    public double getWarehouseSpaceLeft() {
        double spaceLeft = 1 - ((double) warehouseItems.size() / capacity);
        String numberToBeRounded = Double.toString(spaceLeft);
        BigDecimal bd = new BigDecimal(numberToBeRounded).setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    @Override
    public Map<L, P> getWarehouseItems() {
        if (!this.warehouseItems.isEmpty()) {
            return this.warehouseItems;
        }
        return Map.of();
    }


    @Override
    public Map<L, P> deliverParcelsSubmittedBefore(LocalDateTime before) {
        try {
            checkForDateValidity(before);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("The input is null!");
        }
        return deliverParcels(before, "before");
    }

    @Override
    public Map<L, P> deliverParcelsSubmittedAfter(LocalDateTime after) {
        try {
            checkForDateValidity(after);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("The input is null!");
        }
        return deliverParcels(after, "after");
    }
}

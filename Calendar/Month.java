package bg.sofia.uni.fmi.cc.calendar;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Month {
    private int indexOfCurrentDate;
    private final int firstWeekDay;
    private final List<Integer> monthDays = new ArrayList<>();

    public Month(int year, int monthIndex) {
        indexOfCurrentDate = 0;

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, monthIndex - 1, 1);
        firstWeekDay = calendar.get(Calendar.DAY_OF_WEEK);

        YearMonth yearMonth = YearMonth.of(year, monthIndex);
        int numberOfDaysInMonth = yearMonth.lengthOfMonth();
        for (int i = 0; i < numberOfDaysInMonth; i++) {
            monthDays.add(i + 1);
        }
    }

    public int getFirstWeekDay() {
        return firstWeekDay;
    }

    public List<Integer> getMonthDays() {
        return monthDays;
    }

    public int getIndexOfCurrentDate() {
        return indexOfCurrentDate;
    }

    public void setIndexOfCurrentDate(int indexOfCurrentDate) {
        this.indexOfCurrentDate = indexOfCurrentDate;
    }

}

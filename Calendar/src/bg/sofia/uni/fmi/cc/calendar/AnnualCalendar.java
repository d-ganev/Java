package bg.sofia.uni.fmi.cc.calendar;

import java.util.Scanner;

public class AnnualCalendar {
    private static final int DISTANCE_BETWEEN_DATES = 3;
    private static final int NUMBER_OF_ROWS_OF_MONTHS = 3;
    private static final int SPACE_BETWEEN_WEEKDAYS_OF_DIFF_MONTHS = 3;
    private static final int NUMBER_OF_MONTHS_ON_ROW = 4;
    private static final int SPACE_FOR_A_DATE = 5;
    private static final int SPACE_BETWEEN_MONTHS = 5;
    private static final int MAX_ROWS_IN_MONTH = 6;
    private static final int NUMBER_OF_MONTHS = 12;
    private static final int SPACE_FOR_A_MONTH = 33;
    private static final int DISTANCE_TO_NEXT_MONTH = 38;

    private final Month[] months = new Month[NUMBER_OF_MONTHS];
    private final String[] weekDays = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};

    public AnnualCalendar() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a year: ");

        if (scanner.hasNextInt()) {
            int year = scanner.nextInt();
            for (int monthIndex = 0; monthIndex < NUMBER_OF_MONTHS; monthIndex++) {
                months[monthIndex] = new Month(year, monthIndex + 1);
            }
        } else {
            throw new IllegalArgumentException("The input should be an integer!");
        }
    }

    private void printNameOfAMonth(String monthName) {
        System.out.print(monthName);
        for (int i = 0; i < DISTANCE_TO_NEXT_MONTH - monthName.length(); i++) {
            System.out.print(' ');
        }
    }

    private void printNamesOfMonths(int rowIndex) {
        switch (rowIndex) {
            case 0 -> {
                printNameOfAMonth("JANUARY");
                printNameOfAMonth("FEBRUARY");
                printNameOfAMonth("MARCH");
                printNameOfAMonth("APRIL");
                System.out.println();
            }
            case 1 -> {
                printNameOfAMonth("MAY");
                printNameOfAMonth("JUNE");
                printNameOfAMonth("JULY");
                printNameOfAMonth("AUGUST");
                System.out.println();
            }
            case 2 -> {
                printNameOfAMonth("SEPTEMBER");
                printNameOfAMonth("OCTOBER");
                printNameOfAMonth("NOVEMBER");
                printNameOfAMonth("DECEMBER");
                System.out.println();
            }
        }
    }

    private void putSpaces(int numberOfSpaces) {
        for (int i = 0; i < numberOfSpaces; i++) {
            System.out.print(' ');
        }
    }

    private void printDaysOfWeek() {
        for (int monthIndex = 0; monthIndex < NUMBER_OF_MONTHS_ON_ROW; monthIndex++) {
            for (String weekDay : weekDays) {
                System.out.print(weekDay + "  ");
            }
            putSpaces(SPACE_BETWEEN_WEEKDAYS_OF_DIFF_MONTHS);
        }
        System.out.println();
    }

    private void printDate(Month month, int dateIndex, int iterationIndex) {
        int currDate = month.getMonthDays().get(dateIndex);
        System.out.print(currDate);
        if (currDate < 10) {
            System.out.print(' ');
        }
        //not putting spaces after the last date on a row of a particular month
        if (iterationIndex + 3 < SPACE_FOR_A_MONTH && currDate != month.getMonthDays().size()) {
            putSpaces(DISTANCE_BETWEEN_DATES);
        }
    }

    private void printDatesAfterAnIndex(Month month, int index) {
        for (int i = index; i < SPACE_FOR_A_MONTH; i += SPACE_FOR_A_DATE) {
            int currDateIndex = month.getIndexOfCurrentDate();
            if (currDateIndex < month.getMonthDays().size()) {
                printDate(month, currDateIndex, i);
                currDateIndex++;
                month.setIndexOfCurrentDate(currDateIndex);
            } else {
                // putting spaces if there are no more dates in the month
                putSpaces(SPACE_FOR_A_DATE);
            }
        }
        //putting spaces between different months
        putSpaces(SPACE_BETWEEN_MONTHS);
    }

    private int getIndexOfTheFirstDayInAMonth(Month month) {
        int firstWeekDayOfCurrMonth = month.getFirstWeekDay();
        return 1 + SPACE_FOR_A_DATE * (firstWeekDayOfCurrMonth - 1);
    }

    private void printDatesRow(int indexOfCurrMonth) {
        int endOfRow = indexOfCurrMonth + NUMBER_OF_MONTHS_ON_ROW;
        while (indexOfCurrMonth < endOfRow) {
            Month currMonth = months[indexOfCurrMonth];
            // case for the first row of dates in a month, where some weekdays may be skipped
            if (currMonth.getIndexOfCurrentDate() == 0) {
                int indexOfFirstMonthDay = getIndexOfTheFirstDayInAMonth(currMonth);
                //skipping weekdays, which are before the start date
                putSpaces(indexOfFirstMonthDay);
                printDatesAfterAnIndex(currMonth, indexOfFirstMonthDay);
            } else {
                // case when there are no more dates in a month, and current row of the month consists only spaces
                if (currMonth.getIndexOfCurrentDate() >= currMonth.getMonthDays().size()) {
                    putSpaces(DISTANCE_TO_NEXT_MONTH);
                } else {
                    System.out.print(' ');
                    //case when dates are printed from the beginning of the row
                    printDatesAfterAnIndex(currMonth, 0);
                }
            }
            indexOfCurrMonth++;
        }
        System.out.println();
    }

    public void printCalendar() {
        System.out.println();
        for (int currRowOfMonths = 0; currRowOfMonths < NUMBER_OF_ROWS_OF_MONTHS; currRowOfMonths++) {
            printNamesOfMonths(currRowOfMonths);
            printDaysOfWeek();
            for (int currRowOfDates = 0; currRowOfDates < MAX_ROWS_IN_MONTH; currRowOfDates++) {
                printDatesRow(currRowOfMonths * NUMBER_OF_MONTHS_ON_ROW);
            }
            System.out.println();
        }
    }
}

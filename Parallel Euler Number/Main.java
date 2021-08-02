import java.util.Scanner;

public class Main {

    private static int upperBoundForE, numberOfThreads, granularity, calPrecision;

    public static void main(String[] args) {
        readArguments();

        long nano_time1 = System.nanoTime();
        printENum();
        long nano_time2 = System.nanoTime();

        System.out.println("Time: " + ((nano_time2 - nano_time1) / 100*1000000.0) + " sec.");
    }

    private static void readArguments() {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter number of threads: ");
        numberOfThreads = input.nextInt();

        input.nextLine();

        System.out.print("Enter granularity: ");
        granularity = input.nextInt();

        input.nextLine();

        System.out.print("Enter calculation precision: ");
        calPrecision = input.nextInt();

        input.nextLine();

        System.out.print("Enter number of series elements: ");
        upperBoundForE = input.nextInt();

        input.close();
    }

    private static void printENum() {
        EulerNumber euNumber = new EulerNumber(upperBoundForE, numberOfThreads, granularity, calPrecision);
        System.out.println("The number e, calculated with " + euNumber.getUpperBoundForE()
                + " elements is " + euNumber.getEulerNumber());
    }

}
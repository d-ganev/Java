import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class EThread extends Thread {

    private final int granularity, threadIndex, numberOfElements, numberOfThreads, calcPrecision;
    private final boolean[] isSectionCalculated;
    private final BigInteger[] partialFactorial;
    private final BigDecimal[] partialSum;

    public EThread(int threadIndex, int numberOfThreads, int numberOfElements, int granularity, int calcPrecision) {
        this.threadIndex = threadIndex;
        this.numberOfThreads = numberOfThreads;
        this.numberOfElements = numberOfElements;
        this.granularity = granularity;
        this.calcPrecision = calcPrecision;
        isSectionCalculated = new boolean[granularity];
        partialFactorial = new BigInteger[granularity];
        partialSum = new BigDecimal[granularity];
        for (int i = 0; i < granularity; i++) {
            partialFactorial[i] = BigInteger.ONE;
            partialSum[i] = BigDecimal.ZERO;
            isSectionCalculated[i] = false;
        }

        // startIndex = (int) ((double) threadIndex / numberOfThreads * numberOfElements) + 1;
        // endIndex = (int) ((double) (threadIndex + 1) / numberOfThreads * numberOfElements);
    }

    public void run() {
        //long nano_time1 = System.nanoTime();

        for (int sectionIndex = 0; sectionIndex < granularity; sectionIndex++) {

            int numOfElInSection = numberOfElements / numberOfThreads / granularity;
            int startIndex = threadIndex * numOfElInSection + 1 + sectionIndex * (numberOfThreads * numOfElInSection);
            int endIndex = (threadIndex + 1) * numOfElInSection + sectionIndex * (numberOfThreads * numOfElInSection);

            for (int currNum = startIndex; currNum <= endIndex; currNum++) {
                partialFactorial[sectionIndex] = partialFactorial[sectionIndex].multiply(BigInteger.valueOf(currNum));
                partialSum[sectionIndex] = partialSum[sectionIndex]
                        .add(BigDecimal.ONE.divide(new BigDecimal(partialFactorial[sectionIndex]), calcPrecision, RoundingMode.FLOOR));
            }
        }
        //    long nano_time2 = System.nanoTime();
        //    System.out.println("Thread " + threadIndex + " time: " + ((nano_time2 - nano_time1) / 1000000.0) + " ms");
    }

    public boolean isSectionCalculated(int index) {
        return isSectionCalculated[index];
    }

    public SectionPair getSectionResults(int index) {
        isSectionCalculated[index] = true;
        return new SectionPair(partialFactorial, partialSum);
    }
}

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class EulerNumber {

    private BigDecimal e;
    private final int upperBoundForE;
    private int numberOfThreads;
    private final int granularity;
    private final int calPrecision;

    public EulerNumber(int upperBoundForE, int numberOfThreads, int granularity, int calPrecision) {
        e = BigDecimal.ZERO;
        this.upperBoundForE = upperBoundForE;
        this.numberOfThreads = numberOfThreads;
        this.granularity = granularity;
        this.calPrecision = calPrecision;
        calculate();
    }

    public void calculate() {
        BigInteger currFactorial = BigInteger.ONE;
        boolean isDone = false;

        validateInput();

        EThread[] threads = getStartedThreads();

        while (!isDone) {
            isDone = true;

            for (int sectIndex = 0; sectIndex < granularity; sectIndex++) {
                for (int threadIndex = 0; threadIndex < numberOfThreads; threadIndex++) {
                    if (!threads[threadIndex].isSectionCalculated(threadIndex)) {
                        isDone = false;

                        if (!threads[threadIndex].isAlive()) {
                            BigInteger[] partialFact = threads[threadIndex].getSectionResults(sectIndex).getPartialFact();
                            BigDecimal[] partialSum = threads[threadIndex].getSectionResults(sectIndex).getPartialSum();
                            currFactorial = currFactorial.multiply(partialFact[threadIndex]);

                            if (isFirstSection(threadIndex, sectIndex)) {
                                e = e.add(partialSum[sectIndex]);
                            } else {
                                e = e.add(BigDecimal.ONE.divide(new BigDecimal(currFactorial), calPrecision, RoundingMode.CEILING)
                                        .multiply(partialSum[sectIndex]));
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean isFirstSection(int threadIndex, int sectIndex) {
        return threadIndex == 0 && sectIndex == 0;
    }

    private EThread[] getStartedThreads() {
        EThread[] threads = new EThread[numberOfThreads];

        for (int currIndex = 0; currIndex < numberOfThreads; currIndex++) {
            threads[currIndex] = new EThread(currIndex, numberOfThreads, upperBoundForE, granularity, calPrecision);
            threads[currIndex].start();
        }
        return threads;
    }

    private void validateInput() {
        numberOfThreads = numberOfThreads < 1 ? Runtime.getRuntime().availableProcessors() : numberOfThreads;
        numberOfThreads = upperBoundForE < numberOfThreads ? 1 : numberOfThreads;
    }

    public BigDecimal getEulerNumber() {
        return e.add(BigDecimal.ONE);
    }

    public int getUpperBoundForE() {
        return upperBoundForE;
    }

}
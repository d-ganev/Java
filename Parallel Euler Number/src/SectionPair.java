import java.math.BigDecimal;
import java.math.BigInteger;

public class SectionPair {
    private final BigInteger[]  partialFact;
    private final BigDecimal[]  partialSum;

    public SectionPair(BigInteger[] partialFact, BigDecimal[] partialSum) {
        this.partialFact = partialFact;
        this.partialSum = partialSum;
    }

    public BigInteger[] getPartialFact() {
        return partialFact;
    }

    public BigDecimal[] getPartialSum() {
        return partialSum;
    }

}
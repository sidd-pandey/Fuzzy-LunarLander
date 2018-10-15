package lunarlander.fuzzy;

import com.fuzzylite.defuzzifier.WeightedAverage;
import com.fuzzylite.defuzzifier.WeightedDefuzzifier;
import com.fuzzylite.term.Activated;
import com.fuzzylite.term.Aggregated;
import com.fuzzylite.term.Term;

public class LargestWeightedValue extends WeightedDefuzzifier {
	
	public LargestWeightedValue() {
        super();
    }
	
    @Override
    public double defuzzify(Term term, double minimum, double maximum) {
        Aggregated fuzzyOutput = (Aggregated) term;
        if (fuzzyOutput.getTerms().isEmpty()) {
            return Double.NaN;
        }
        minimum = fuzzyOutput.getMinimum();
        maximum = fuzzyOutput.getMaximum();

      
        double maxw =-1, maxz=-1;
        double w, z;

        for (Activated activated : fuzzyOutput.getTerms()) {
        	w = activated.getDegree();
        	z = activated.getTerm().membership(w);
        	if (maxw < w) {
        		maxw = w;
        		maxz = z;
        	}
//        	System.out.println(w + " " + z);
   
        }
        return maxz;
    }

    @Override
    public WeightedAverage clone() throws CloneNotSupportedException {
        return (WeightedAverage) super.clone();
    }
}

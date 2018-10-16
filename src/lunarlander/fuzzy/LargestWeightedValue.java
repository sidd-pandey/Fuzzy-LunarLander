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
        String maxterm = "null";
        double w, z;

//        System.out.println("---------start of a move-------------");
        for (Activated activated : fuzzyOutput.getTerms()) {
        	w = activated.getDegree();
        	z = activated.getTerm().membership(w);
//        	System.out.println(activated.getTerm().getName() +" is activated with weight: " + w +" and mebership value "+ z);
        	if (maxw < w) {
        		maxw = w;
        		maxz = z;
        		maxterm = activated.getTerm().getName();
        	}   
        }
//        System.out.println("---------end of a move----------" + maxterm);
        return maxz;
    }

    @Override
    public WeightedAverage clone() throws CloneNotSupportedException {
        return (WeightedAverage) super.clone();
    }
}

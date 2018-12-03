package ca.homedepot.relevancy.model;

import ca.homedepot.relevancy.evaluator.Evaluator;

public enum TimePeriod {
	
	THREE_MONTHS("threeMonthsModel.ser", "Three Months"), 
	SIX_MONTHS("sixMonthsModel.ser", "Six Months"), 
	YEAR("yearModel.ser", "Year");
	
	private final Evaluator evaluator;
	private final String displayName;
	
	TimePeriod(final String filename, final String displayName) {
		evaluator = new Evaluator(System.getProperty("user.dir") + "/" + filename);
		this.displayName = displayName;
	}
	
	public Evaluator getEvaluator() {
		return evaluator;
	}

	public String getDisplayName() {
		return displayName;
	}
}
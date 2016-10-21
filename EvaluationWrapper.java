import org.vu.contest.ContestEvaluation;

import java.util.Properties;

/**
 * Wrapper class for ContestEvaluation
 * includes a built-in evaluation counter
 */
public class EvaluationWrapper implements ContestEvaluation {

	private final ContestEvaluation evaluation;

	private final int evaluations_limit;
	private final boolean isMultimodal;
	private final boolean hasStructure;
	private final boolean isSeparable;

	private int evaluations_left;

	public EvaluationWrapper(ContestEvaluation evaluation) {
		super();
		this.evaluation = evaluation;
		// Get evaluation properties
		Properties props = evaluation.getProperties();
		this.evaluations_limit = Integer.parseInt(props.getProperty("Evaluations"));
		this.evaluations_left = this.evaluations_limit;
		this.isMultimodal = Boolean.parseBoolean(props.getProperty("Multimodal"));
		this.hasStructure = Boolean.parseBoolean(props.getProperty("Regular"));
		this.isSeparable = Boolean.parseBoolean(props.getProperty("Separable"));
	}

	public boolean hasEvaluationsLeft() {
		return evaluations_left > 0;
	}

	@Override
	public Object evaluate(Object arg0) {
		evaluations_left--;
		return this.evaluation.evaluate(arg0);
	}

	@Override
	public double getFinalResult() {
		return this.evaluation.getFinalResult();
	}

	public boolean isMultimodal() {
		return isMultimodal;
	}

	public boolean isHasStructure() {
		return hasStructure;
	}

	public boolean isSeparable() {
		return isSeparable;
	}

	@Override
	public Properties getProperties() {
		return this.evaluation.getProperties();
	}

	@Override
	public Object getData(Object arg0) {
		return this.evaluation.getData(arg0);
	}
}

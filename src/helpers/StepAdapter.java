package helpers;

import city.cs.engine.StepEvent;
import city.cs.engine.StepListener;

/**
 * Creates an adapter for StepListener to avoid empty functions later on.
 */
public abstract class StepAdapter implements StepListener {
	@Override
	public void preStep(StepEvent stepEvent) {}

	@Override
	public void postStep(StepEvent stepEvent) {}
}

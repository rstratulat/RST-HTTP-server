/**
 * 
 */
package com.stratulat.server.fsm;

/**
 * @author Remus Stratulat
 *
 */
public abstract class Context {
	private State currentState;

	
	public abstract void goNext();
	
	/**
	 * @return the current 
	 */
	public State getCurrentState() {
		return currentState;
	}

	/**
	 * @param current the current state to set
	 */
	public void setCurrentState(State current) {
		this.currentState = current;
	}
	
}

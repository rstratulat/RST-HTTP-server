/**
 * 
 */
package com.stratulat.server.fsm;

/**
 * @author Remus Stratulat
 *
 */
public interface State {
	
	/**
	 * Use the context to determine the nextState. 
	 * Do the required processing based on context and jump to the next state. 
	 * 
	 * @param context the context
	 */
	public void nextState(Context context);
}

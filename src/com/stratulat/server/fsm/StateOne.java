/**
 * 
 */
package com.stratulat.server.fsm;

/**
 * @author Remus Stratulat
 *
 */
public class StateOne implements State {

	/* (non-Javadoc)
	 * @see com.stratulat.server.fsm.State#nextState(com.stratulat.server.fsm.Context)
	 */
	@Override
	public void nextState(Context context) {
		// change the context and get to the next state. 

		 context.setCurrentState(new StateTwo());
	}

}

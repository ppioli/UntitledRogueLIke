package com.ppioli.url.actors.actions;

public interface IAction {

    /**
     * The main method of the action. The logic of the action should be implemented here.
     * @param deltaTime the time available to execute the action
     * @return Zero if the action was not completed, or the remaining time left after executing the action.
     */
    float execute( float deltaTime );

    /**
     * @return The cost in AP of executing the action.
     */
    int getCost();

    /**
     * @return a boolean expressing if the action is currently executable.
     */
    boolean applicable();
}

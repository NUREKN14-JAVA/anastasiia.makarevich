package com.anamakarevich.usermanagement.agent;

import jade.core.Agent;

public class SearchAgent extends Agent {
    private static final long serialVersionUID = 1L;

    @Override
    protected void setup() {
        super.setup();
        System.out.println(getAID().getName() + " started");
    }

    @Override
    protected void takeDown() {
        System.out.println(getAID().getName() + " terminated");
        super.takeDown();
        
    }
    

}

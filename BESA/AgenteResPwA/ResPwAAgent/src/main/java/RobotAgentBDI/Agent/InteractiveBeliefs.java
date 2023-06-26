package RobotAgentBDI.Agent;

import RobotAgentBDI.Believes.RobotAgentBelieves;

import java.util.Scanner;

public class InteractiveBeliefs implements Runnable {
    private RobotAgentBelieves believes;

    public InteractiveBeliefs(RobotAgentBelieves believes) {
        this.believes = believes;
        run();
    }

    public void run() {
        Scanner sc = new Scanner(System.in);
        int i = sc.nextInt();
        while (i != -1) {
            this.believes.metaActual = i;
            i = sc.nextInt();
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RobotAgentBDI.Agent;

import BESA.BDI.AgentStructuralModel.Agent.AgentBDI;
import BESA.BDI.AgentStructuralModel.GoalBDI;
import BESA.ExceptionBESA;
import BESA.Kernel.Agent.StructBESA;
import RobotAgentBDI.Believes.RobotAgentBelieves;

import java.util.List;

/**
 *
 * @author juans
 */
public class RobotAgentBDI extends AgentBDI{
    
    private static final double TH=0.5;
    public RobotAgentBDI(String alias, List<GoalBDI> RAGoals, String cedula) throws ExceptionBESA {
        this(alias, RAGoals, cedula, new RobotAgentBelieves(cedula));
    }
    public RobotAgentBDI(String alias, List<GoalBDI> RAGoals, String cedula, RobotAgentBelieves rab ) throws ExceptionBESA {
        super(alias, rab, RAGoals,TH, new StructBESA());

        InteractiveBeliefs ib = new InteractiveBeliefs(rab);

        System.out.println("RobotAgentBDI Iniciado");
    }

    @Override
    public void setupAgentBDI() {
    }

    @Override
    public void shutdownAgentBDI() {
    
    }
           
}

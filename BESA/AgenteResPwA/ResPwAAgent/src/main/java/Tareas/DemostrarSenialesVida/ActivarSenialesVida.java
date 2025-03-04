/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tareas.DemostrarSenialesVida;

import RobotAgentBDI.Believes.RobotAgentBelieves;
import RobotAgentBDI.ServiceRequestDataBuilder.ServiceRequestBuilder;
import ServiceAgentResPwA.AutonomyServices.AutonomyServiceRequestType;
import ServiceAgentResPwA.Guard.ServiceDataRequest;
import Utils.ResPwaUtils;
import rational.mapping.Believes;
import rational.mapping.Task;

import java.util.HashMap;

/**
 *
 * @author mafegarces
 */
public class ActivarSenialesVida extends Task{
    
    private HashMap<String, Object> infoServicio = new HashMap<>();
    private static final String ACTIVATE_LIFE_SIGNALS = "ACTIVATELIFESIGNALS";
    
    @Override
    public boolean checkFinish(Believes believes) {
                

        System.out.println("--- Check Finish ActivarSenialesVida ---");
        RobotAgentBelieves blvs = (RobotAgentBelieves) believes;
        if(blvs.getbEstadoRobot().isActivadoMovHabla())
        {
            return true;
        }
        return false;
    }

    @Override
    public void executeTask(Believes parameters) {
        RobotAgentBelieves blvs = (RobotAgentBelieves) parameters;
        infoServicio.put(ACTIVATE_LIFE_SIGNALS, true); 
        ServiceDataRequest srb = ServiceRequestBuilder.buildRequest(AutonomyServiceRequestType.ACTIVATELIFESIGNALS , infoServicio);
        ResPwaUtils.requestService(srb, blvs);
    }

    @Override
    public void interruptTask(Believes believes) {
        System.out.println("--- Interrupt Task ActivarSenialesVida ---");
    }

    @Override
    public void cancelTask(Believes believes) {
        System.out.println("--- Cancel Task ActivarSenialesVida ---");
    }
    
}

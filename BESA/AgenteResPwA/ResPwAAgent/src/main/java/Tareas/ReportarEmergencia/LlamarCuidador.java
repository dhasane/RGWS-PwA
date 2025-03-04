/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tareas.ReportarEmergencia;

import RobotAgentBDI.Believes.RobotAgentBelieves;
import RobotAgentBDI.ServiceRequestDataBuilder.ServiceRequestBuilder;
import ServiceAgentResPwA.Guard.ServiceDataRequest;
import ServiceAgentResPwA.VoiceServices.VoiceServiceRequestType;
import Utils.ResPwaUtils;
import rational.mapping.Believes;
import rational.mapping.Task;

import java.util.HashMap;

/**
 *
 * @author mafegarces
 */
public class LlamarCuidador extends Task{
    
    private HashMap<String,Object> infoServicio = new HashMap<>();

    public LlamarCuidador() {
//        System.out.println("--- Task LlamarCuidador PwA Iniciada ---");
    }

    @Override
    public boolean checkFinish(Believes believes) {
                

        System.out.println("--- Check Finish LlamarCuidador ---");
        RobotAgentBelieves blvs = (RobotAgentBelieves) believes;
        if(blvs.getbEstadoInteraccion().isHayInteraccionFisica() && blvs.getbEstadoInteraccion().isDetectaPersona())
        {
            return true;
        }
        return false;
    }

    @Override
    public void executeTask(Believes parameters) {
        System.out.println("--- Execute Task LlamarCuidador ---");
        
        RobotAgentBelieves blvs = (RobotAgentBelieves) parameters;
        infoServicio.put("SAY", "guiu guiu guiu cuidadoooooor guiu guiu guiu"); 
        ServiceDataRequest srb = ServiceRequestBuilder.buildRequest(VoiceServiceRequestType.SAY, infoServicio);
        ResPwaUtils.requestService(srb,blvs);
    }

    @Override
    public void interruptTask(Believes believes) {
        System.out.println("--- Interrupt Task LlamarCuidador ---");
    }

    @Override
    public void cancelTask(Believes believes) {
        System.out.println("--- Cancel Task LlamarCuidador ---");
    }
    
}

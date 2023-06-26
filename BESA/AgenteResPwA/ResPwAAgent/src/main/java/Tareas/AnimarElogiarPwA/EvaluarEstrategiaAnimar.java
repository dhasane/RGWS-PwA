/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tareas.AnimarElogiarPwA;

import RobotAgentBDI.Believes.RobotAgentBelieves;
import rational.mapping.Believes;
import rational.mapping.Task;

import java.util.HashMap;

/**
 *
 * @author mafegarces
 */
public class EvaluarEstrategiaAnimar extends Task{
    
    private HashMap<String,Object> infoServicio = new HashMap<>();
    

    public EvaluarEstrategiaAnimar() {
//        System.out.println("--- Task Seleccionar Estrategia Animar PwA Iniciada ---");
    }
    
    @Override
    public void executeTask(Believes parameters) {
        System.out.println("--- Execute Task Seleccionar Estrategia Animar PwA ---");

        AnimarStrategy as = new AnimarStrategy();
        as.execStrategy(parameters);


    }

    @Override
    public void interruptTask(Believes believes) {
        System.out.println("--- Interrupt Task Seleccionar Estrategia Animar PwA ---");
        RobotAgentBelieves blvs = (RobotAgentBelieves) believes;
        blvs.getbEstadoActividad().setEstrategia(null);

    }

    @Override
    public void cancelTask(Believes believes) {
        System.out.println("--- Cancel Task Seleccionar Estrategia Animar PwA ---");
        RobotAgentBelieves blvs = (RobotAgentBelieves) believes;
        blvs.getbEstadoActividad().setEstrategia(null);
    }

    @Override
    public boolean checkFinish(Believes believes) {
        
        RobotAgentBelieves blvs = (RobotAgentBelieves) believes;
        if(!blvs.getbEstadoInteraccion().isEstaHablando() && blvs.getbEstadoActividad().getEstrategia()!=null && blvs.getbEstadoActividad().getEstrategia() instanceof AnimarStrategy && blvs.getbEstadoActividad().getEstrategia().isFinished(believes)) {
            return true;
        }
        return false;
    }
    
}

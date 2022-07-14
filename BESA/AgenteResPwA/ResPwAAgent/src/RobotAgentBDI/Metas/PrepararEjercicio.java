/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package RobotAgentBDI.Metas;

import BESA.BDI.AgentStructuralModel.GoalBDI;
import BESA.BDI.AgentStructuralModel.GoalBDITypes;
import BESA.BDI.AgentStructuralModel.StateBDI;
import BESA.Kernel.Agent.Event.KernellAgentEventExceptionBESA;
import Init.InitRESPwA;
import RobotAgentBDI.Believes.RobotAgentBelieves;
import Tareas.PrepararEjercicio.*;
import rational.RationalRole;
import rational.mapping.Believes;
import rational.mapping.Plan;
import rational.mapping.Task;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author juan.amorocho
 */
public class PrepararEjercicio extends GoalBDI {
 
    private static String descrip = "PrepararEjercicio";
    public static PrepararEjercicio buildGoal() {

        PreguntarPreparacion prg1 = new PreguntarPreparacion("Hola! Voy a hacerte unas preguntas para"
        + "asegurarme que todo esté listo para hacer nuestro ejercicio de hoy! Estas confortable con la ropa que tienes?");
        PreguntarPreparacion prg2 = new PreguntarPreparacion("Nuestro cuidador está presente?");
        Plan rolePlan= new Plan();
        List<Task> taskList = new ArrayList<>();
        rolePlan.addTask(prg1);
        taskList.add(prg1);
        rolePlan.addTask(prg2,taskList);  
        RationalRole convEmpRole = new RationalRole(descrip, rolePlan);
        PrepararEjercicio b= new PrepararEjercicio(InitRESPwA.getPlanID(), convEmpRole, descrip, GoalBDITypes.REQUIREMENT);
        return b;
    }
    public PrepararEjercicio (int id, RationalRole role, String description, GoalBDITypes type) {
        super(id, role, description, type);
        //System.out.println("Meta ConversarEmpaticamente created");
    }

    @Override
    public double evaluateViability(Believes believes) throws KernellAgentEventExceptionBESA {
        //System.out.println("Meta ConversarEmpaticamente evaluateViability");
        return 1;
    }

    @Override
    public double detectGoal(Believes believes) throws KernellAgentEventExceptionBESA {
        System.out.println("DEBUG - DETECTAR META: Asegurar preparacion ejercicio.");
        //TODO: Revisar que se hayan activado los requierimientos de la preparacion de ejercicio.
        return 100;
    }

    @Override
    public double evaluatePlausibility(Believes believes) throws KernellAgentEventExceptionBESA {
        //System.out.println("Meta ConversarEmpaticamente evaluatePlausibility");
        return 1;
    }

    @Override
    public double evaluateContribution(StateBDI stateBDI) throws KernellAgentEventExceptionBESA {
        //System.out.println("Meta ConversarEmpaticamente evaluateContribution");
        
        RobotAgentBelieves blvs = (RobotAgentBelieves)stateBDI.getBelieves();
        return blvs.getbEstadoEmocionalPwA().getTiempoEmocionPredominante() + 1;
    }

    @Override
    public boolean predictResultUnlegality(StateBDI agentStatus) throws KernellAgentEventExceptionBESA {
        //System.out.println("Meta ConversarEmpaticamente predictResultUnlegality");
        return true;
    }

    @Override
    public boolean goalSucceeded(Believes believes) throws KernellAgentEventExceptionBESA {
        //System.out.println("Meta ConversarEmpaticamente goalSucceeded");
        //TODO: Verificar que se hayan acertado todas las preguntas.
        return true;
    }
}

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
import ResPwAEntities.PerfilEjercicio;
import RobotAgentBDI.Believes.RobotAgentBelieves;
import Tareas.SalirDeSuspension.DesuspenderRobot;
import rational.RationalRole;
import rational.mapping.Believes;
import rational.mapping.Plan;
import rational.mapping.Task;

import java.util.*;

/**
 *
 * @author mafegarces
 */
public class SalirDeSuspension extends GoalBDI {

    private static String descrip = "SalirDeSuspension";

    public static SalirDeSuspension buildGoal() {

        DesuspenderRobot desuspenderRobot = new DesuspenderRobot();//Suspender a robot y notificar/decir
        List<String> resources = new ArrayList<>();
        List<Task> taskList = new ArrayList<>();

        Plan rolePlan = new Plan();

        rolePlan.addTask(desuspenderRobot);

        RationalRole recBatRole = new RationalRole(descrip, rolePlan);
        SalirDeSuspension b = new SalirDeSuspension(InitRESPwA.getPlanID(), recBatRole, descrip, GoalBDITypes.SURVIVAL);
        return b;
    }

    public SalirDeSuspension(int id, RationalRole role, String description, GoalBDITypes type) {
        super(id, role, description, type);
        //System.out.println("Meta RecargarBateria created");
    }

    @Override
    public double evaluateViability(Believes believes) throws KernellAgentEventExceptionBESA {
        //System.out.println("Meta RecargarBateria evaluateViability");
        return 1;
    }

    @Override
    public double detectGoal(Believes believes) throws KernellAgentEventExceptionBESA {
        System.out.println("Meta RecargarBateria detectGoal");

        RobotAgentBelieves blvs = (RobotAgentBelieves) believes;
        if (blvs.getbEstadoInteraccion().isSistemaSuspendido()) 
        {
            if (blvs.getbPerfilPwA().getPerfil().getPerfilEjercicio() != null) {
                PerfilEjercicio miPerfil = blvs.getbPerfilPwA().getPerfil().getPerfilEjercicio();
                Date currDate = new Date();
                Calendar currDiaCalendar = GregorianCalendar.getInstance();
                currDiaCalendar.setTime(currDate);
                int currHour = currDiaCalendar.get(Calendar.HOUR_OF_DAY);
                if (((currDate.equals(miPerfil.getFechaProx()) || currDate.after(miPerfil.getFechaProx())) && currHour >= miPerfil.getHoraProx())) {
                    return 1.0;
                }
            }

            if (blvs.getbEstadoRobot().getBatteryPerc() > 30.0 && !blvs.getbEstadoRobot().getBateria()) {
                return 1.0;
            }
        }
        return 0;
    }

    @Override
    public double evaluatePlausibility(Believes believes) throws KernellAgentEventExceptionBESA {
        //System.out.println("Meta RecargarBateria evaluatePlausibility");
        return 1;
    }

    @Override
    public double evaluateContribution(StateBDI stateBDI) throws KernellAgentEventExceptionBESA {
        //System.out.println("Meta RecargarBateria evaluateContribution");
        RobotAgentBelieves blvs = (RobotAgentBelieves) stateBDI.getBelieves();
        return blvs.getbEstadoRobot().getBateria() ? 1 : 0;
    }

    @Override
    public boolean predictResultUnlegality(StateBDI agentStatus) throws KernellAgentEventExceptionBESA {
        //System.out.println("Meta RecargarBateria predictResultUnlegality");
        return true;
    }

    @Override
    public boolean goalSucceeded(Believes believes) throws KernellAgentEventExceptionBESA {
        RobotAgentBelieves blvs = (RobotAgentBelieves) believes;
        return !blvs.getbEstadoRobot().getBateria();
    }

}

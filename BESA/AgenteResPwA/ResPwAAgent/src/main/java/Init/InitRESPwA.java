package Init;

import BDInterface.RESPwABDInterface;
import BESA.BDI.AgentStructuralModel.GoalBDI;
import BESA.ExceptionBESA;
import BESA.Kernel.System.AdmBESA;
import EmotionalAnalyzerAgent.Agent.EmotionalAnalyzerAgent;
import PepperPackage.EmotionalModel.PepperEAStrategy;
import PepperPackage.Utils.PepperAdapter;
import ResPwAEntities.*;
import RobotAgentBDI.Agent.RobotAgentBDI;
import RobotAgentBDI.Metas.*;
import RobotAgentBDI.ServiceRequestDataBuilder.ServiceRequestBuilder;
import SensorHandlerAgent.Agent.SensorHandlerAgent;
import ServiceAgentResPwA.Agent.ServiceAgentRESPwA;
import ServiceAgentResPwA.Guard.ServiceDataRequest;
import ServiceAgentResPwA.MovementServices.MovementServiceRequestType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author juans Cuidador--> crea perfil y crea pwas
 *
 * Autenticar. 1. Pepper se acerca. 2. Si identifica la cara, lo saluda e inicia
 * sesion. 3. Le avisa que no lo conoce y que hable con su cuidador para poder
 * hacerle un perfil. Conversacion Casual. "Hola, como estas hoy" y muestra en
 * tablet y espera respuesta oral. Como estas como te fue hoy. Y luego, lo
 * escucha por un rato. y le dice que hagan una actividad. * Luego empieza la
 * sesion. *
 */
public class InitRESPwA {

    public static String aliasRobotAgent = "RobotAgent";
    public static String aliasEAAgent = "EAAgent";
    public static String aliasSHAAgent = "SHAAgent";
    public static String aliasSPAgent = "SPAgent";
    public static String emf = "ResPwAEntitiesPU";
    private static int PLANID = 0;

    public static void main(String[] args) 
    {
        try 
        {
            String cedula = obtenerUsuario();
            //Empieza y Crea Contenedor
            AdmBESA.getInstance();
            System.out.println("Iniciando RES-PwA");
            //Creacion y Activacion de Agentes
            RobotAgentBDI RABDI = new RobotAgentBDI(aliasRobotAgent, createRobotAgentGoals(), cedula);
            EmotionalAnalyzerAgent EAA = new EmotionalAnalyzerAgent(aliasEAAgent, new PepperEAStrategy());
            SensorHandlerAgent SHA = new SensorHandlerAgent(aliasSHAAgent);
            PepperAdapter p = new PepperAdapter();
            ServiceAgentRESPwA SPA = ServiceAgentRESPwA.buildRobotSPAgent(aliasSPAgent, p);
            startAllAgents(RABDI, EAA, SHA, SPA);
            startConfig(p);
            // -------------| PRUEBA - Dance, Macarena |-----------------
            //HashMap<String, Object> hm1 = new HashMap<>();
            //hm1.put("TAGSDANCE", "FRAGCONVRIGHTOUTFOCUS");
            //ServiceDataRequest data = ServiceRequestBuilder.buildRequest(ActivityServiceRequestType.RUNANIMATION, hm1);
            //p.sendRequest(data);
            // ------------------|PRUEBA: Topico| ------------------
            
            // -------------| END - PRUEBA |-----------------------------
        } catch (ExceptionBESA ex)
        {
            Logger.getLogger(InitRESPwA.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) 
        {
            Logger.getLogger(InitRESPwA.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static String obtenerUsuario() {
        String cedula = null, user = "juan_cuidador", pwd = "jpablo98";
        boolean login = false;
        Scanner scan = new Scanner(System.in);
        Cuidador c = null;
        /*
        // ---| CONECTAR SIN BASE DE DATOS |---
            System.out.println("Ingrese su nombre de usuario: ");
            user=scan.nextLine();
            System.out.println("Ingrese su contrasena: ");
            pwd= scan.nextLine();
            return pwd;
         */
        // ---| CONECTAR CON BASE DE DATOS |---
           
        do {
            
            c = RESPwABDInterface.getCarer(user);
            if (user == null) {
                System.out.println("Usuario Inexistente");
            } else {
                login = c.getContrasena().equals(pwd);
                //login = true;
                if (!login) {
                System.out.println("Contrasena no coincide");
                }
            }

        } while (!login);
        List<PerfilPwa> pwalist = c.getPerfilPwaList(); //Cambiar OneToOne a OneToMany en BD

        for (int i = 0; i < pwalist.size(); i++) {
           System.out.println(i + " Paciente: " + pwalist.get(i).getCedula());
        }
        System.out.println("Ingrese el numero del paciente que utilizara ResPwa");
        int selec = 0; //scan.nextInt();
        return pwalist.get(selec).getCedula();
                
        
    }

    public static int getPlanID() {
        return ++PLANID;
    }

    private static List<GoalBDI> createRobotAgentGoals() {
        List<GoalBDI> RAGoals = new ArrayList<>();
        //Crear Metas
        PrepararEjercicio prepEjercicio = PrepararEjercicio.buildGoal();
        ConversarEmpaticamente convEmpatica = ConversarEmpaticamente.buildGoal();
		// CambiarDificultad cambiarDificultadGoal = CambiarDificultad.buildGoal();
		// EstimularEmocionalmente estimularEmocionalmenteGoal = EstimularEmocionalmente.buildGoal();
		Cuenteria cuenteriaGoal = Cuenteria.buildGoal();
		MusicoTerapia musicoTGoal= MusicoTerapia.buildGoal();
		//TestPlan2 testPlan = TestPlan2.buildGoal();
		TestPlan tp = TestPlan.buildGoal();
		LogIn logInGoal = LogIn.buildGoal();
		MantenerAtencionPwA mantenerAtencionPwAGoal=  MantenerAtencionPwA.buildGoal();
		RecargarBateria recargarBateriaGoal=  RecargarBateria.buildGoal();
		PedirAyuda pedirAyudaGoal= PedirAyuda.buildGoal();
		// ReiniciarActividad reiniciarActividadGoal=  ReiniciarActividad.buildGoal();
		// Saludar saludar = Saludar.buildGoal();
		ReportarEmergencia reportar = ReportarEmergencia.buildGoal();
		//Agregar a Lista
		RAGoals.add(prepEjercicio);
		RAGoals.add(convEmpatica);
		RAGoals.add(cuenteriaGoal);
		RAGoals.add(tp);
		RAGoals.add(musicoTGoal);
		// RAGoals.add(testPlan);
		RAGoals.add(tp);
		RAGoals.add(musicoTGoal);
		RAGoals.add(logInGoal);
		RAGoals.add(mantenerAtencionPwAGoal);
		RAGoals.add(recargarBateriaGoal);
		RAGoals.add(pedirAyudaGoal);
		// RAGoals.add(reiniciarActividadGoal);
		// RAGoals.add(saludar);
		RAGoals.add(reportar);
		// RAGoals.add(cambiarDificultadGoal);
		// RAGoals.add(estimularEmocionalmenteGoal);

        // ----------- PRUEBA NUEVAS METAS ------------ //
        RutinaEjercicio rutEjercicio = RutinaEjercicio.buildGoal();
        AsignarProgramaEjercicio asigEjercicio = AsignarProgramaEjercicio.buildGoal();
        DetectarPersonaCercana detecPersona = DetectarPersonaCercana.buildGoal();
        
        // -- Activación de Metas en el sistema.
        RAGoals.add(rutEjercicio);
        RAGoals.add(asigEjercicio);
        RAGoals.add(detecPersona);

        return RAGoals;
    }

    private static void startAllAgents(RobotAgentBDI RABDI, EmotionalAnalyzerAgent EAA, SensorHandlerAgent SHA, ServiceAgentRESPwA SPA) throws ExceptionBESA {
        RABDI.start();
        SPA.start();
        EAA.start();
        SHA.start();
        SHA.subscribeServices();
        RABDI.startTimers();
    }

    public static void startConfig(PepperAdapter p) {
        List<Emocion> emociones = RESPwABDInterface.getEmociones();
        HashMap<String, Object> infoServicio = new HashMap<>();
        HashMap<String, Object> params = new HashMap<>();
        HashMap<String, Object> accion = new HashMap<>();
        HashMap<String, Object> joint = new HashMap<>();
        HashMap<String, Object> joints = new HashMap<>();
        List<HashMap<String, Object>> paramList = new ArrayList<>();
        List<Double> keys = new ArrayList<>();
        List<Double> times = new ArrayList<>();

        params = new HashMap<>();
        for (Emocion e : emociones) {
            if (!params.containsKey(e.getEmotionalTag())) {
                params.put(e.getEmotionalTag(), new HashMap<String, Object>());
            }
            accion = new HashMap<>();
            for (Accion a : e.getAccionList())
            {
                joints = new HashMap<>();
                for (Joint j : a.getJointList()) {
                    if (!joints.containsKey(j.getNombre())) {
                        joint = new HashMap<>();
                        joint.put("key", new ArrayList<Double>());
                        joint.put("time", new ArrayList<Double>());
                        joints.put(j.getNombre(), joint);
                    }
                    joint = (HashMap<String, Object>) joints.get(j.getNombre());
                    keys = (List<Double>) joint.get("key");
                    times = (List<Double>) joint.get("time");

                    keys.add(j.getAngulo());
                    times.add(j.getTiempo());

                }
                accion.put("image", e.getImagen());
                accion.put(a.getNombre(), joints);
            }
            params.put(e.getEmotionalTag(), accion);
        }
        infoServicio.put("INITIALCONF", params);
        ServiceDataRequest srb = ServiceRequestBuilder.buildRequest(MovementServiceRequestType.INITIALCONF, infoServicio);
        p.sendRequest(srb);
    }

}

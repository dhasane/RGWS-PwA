/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RobotAgentBDI.Believes;

import BDInterface.RESPwABDInterface;
import EmotionalAnalyzerAgent.Guards.EmotionalData;
import Personalizacion.Modelo.ModeloRetroalimentacion;
import ResPwAEntities.*;
import RobotAgentBDI.Utils.ResPwAActivity;
import SensorHandlerAgent.Guards.SensorData;
import Utils.Imagen;
import rational.data.InfoData;
import rational.mapping.Believes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author juans
 */
public class RobotAgentBelieves implements Believes {

    public int metaActual;
    private BEstadoInteraccion bEstadoInteraccion;
    private BEstadoEmocionalPwA bEstadoEmocionalPwA;
    private BEstadoActividad bEstadoActividad;
    private BPerfilPwA bPerfilPwA;
    private BEstadoRobot bEstadoRobot;
    private Map<String, List<String>> imgCuentos; //nomCuento //Lista de Strings -> url
    private List<Imagen> imgsPerfil;

    public RobotAgentBelieves(String cedula) {
        bEstadoEmocionalPwA = new BEstadoEmocionalPwA();
        bEstadoInteraccion = new BEstadoInteraccion();
        bEstadoRobot = new BEstadoRobot();
        imgCuentos = new HashMap<>();
        imgsPerfil = new ArrayList<>();
        bEstadoActividad = new BEstadoActividad(cedula, this);
        bPerfilPwA = new BPerfilPwA(this);
        bPerfilPwA.setPerfil(getPerfilBD(cedula));

        System.out.println("VERRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRrr: " + bPerfilPwA.getPerfil().getPerfilPreferencia().getActXPreferenciaList().get(0).getGusto());
//        FBaseUtils.initResPwa(this);
        metaActual = -1;
    }

    //AQUI SE MANDA LO DE INFORMATIONFLOW
    //Aqui se accede a BD y se pide info de otros believes. 
    @Override
    public boolean update(InfoData si) {
        if (si != null && si instanceof EmotionalData) {
            EmotionalData se = (EmotionalData) si;
//            System.out.println("Emotional RobotAgentBelieves update Received: " + se.getInfo());
            if (se.getEmoEv() != null) {
                bEstadoRobot.update(se);
            }
            bEstadoEmocionalPwA.update(si);
        } else if (si != null) {
            SensorData infoRecibida = (SensorData) si;
//            System.out.println("RobotAgentBelieves update Received: " + infoRecibida.getDataP());
            switch (infoRecibida.getDataType()) {
                case ACTIVITY:
                    bEstadoActividad.update(si);
                    break;
                case INTERACTION:
                    bEstadoInteraccion.update(si);
                    break;
                case ROBOT:
                    bEstadoRobot.update(si);
                    break;
                case PROFILE:
                    bPerfilPwA.update(si);
                    break;
                default:
                    break;
            }
        }

        return true;
    }

    public void feedbackActivity(double voiceFeedback) {
        double emotionFeedback = 0.0, emotionFeedbackAux = 0;
        ResPwAActivity activity = bEstadoActividad.getActividadActual();
        Object activityInCourse = null;
        List<Antecedente> antecedents = RESPwABDInterface.getActecedents();
        emotionFeedback = bEstadoEmocionalPwA.getFeedbackEmotion();
        emotionFeedback = aproximateEmotionValue(emotionFeedback);

        List<Antecedente> antecedentsForFeedback = getAntecedentsForFeedback(emotionFeedback, voiceFeedback, antecedents);
        System.out.println("ACTIVIDAD ACTUAL" + activity);

        switch (activity) {
            case CUENTERIA:
                activityInCourse = (PreferenciaXCuento) bEstadoActividad.getCuentoActual();
                ModeloRetroalimentacion<PreferenciaXCuento> modeloRetroCuento = new ModeloRetroalimentacion<>((PreferenciaXCuento) activityInCourse);
                modeloRetroCuento.activityFeedback(antecedentsForFeedback);
                break;

            case MUSICOTERAPIA:
                activityInCourse = (PreferenciaXCancion) bEstadoActividad.getCancionActual();
                ModeloRetroalimentacion<PreferenciaXCancion> modelRetroCancion = new ModeloRetroalimentacion<>((PreferenciaXCancion) activityInCourse);
                modelRetroCancion.activityFeedback(antecedentsForFeedback);
                break;
            case EJERCICIO:
                // save to bd : )
                PerfilEjercicio currPerfilEjercicio = bPerfilPwA.getPerfil().getPerfilEjercicio();
                if (currPerfilEjercicio != null)
                {
                    for (int i = 0; i < currPerfilEjercicio.getEjercicioList().size(); i++)
                    {
                        //currPerfilEjercicio.getEjercicioList().get(i).setPreferencia(voiceFeedback);
                        //System.out.println("Entró acá: " + voiceFeedback);
                        //RESPwABDInterface.updateExercise(currPerfilEjercicio.getEjercicioList().get(i));
                    }
                }
                break;
        }

    }

    private List<Antecedente> getAntecedentsForFeedback(double emotionFeedback, double voiceFeedback, List<Antecedente> antecedents) {

        List<Antecedente> antecedentsForFeedback = new ArrayList<>();
        for (int i = 0; i < antecedents.size(); i++) {
            if (antecedents.get(i).getEtiqueta().equals("EMOTIONFEEDBACK") && antecedents.get(i).getValor() == emotionFeedback) {
                antecedentsForFeedback.add(antecedents.get(i));
            } else {
                if (antecedents.get(i).getEtiqueta().equals("VOICEFEEDBACK") && antecedents.get(i).getValor() == voiceFeedback) {
                    antecedentsForFeedback.add(antecedents.get(i));
                }
            }
        }

        return antecedentsForFeedback;
    }

    private double aproximateEmotionValue(double emotionFeedback) {
        double aproximation = 0.0;
        final double VERY_PLEASANT = 1;
        final double PLEASANT = 0.65;
        final double LITTLE_PLEASANT = 0.35;
        final double VERY_UNPLEASANT = -1;
        final double UNPLEASANT = -0.65;
        final double LITTLE_UNPLEASANT = -0.35;
        final double ZERO = 0.0;

        if (emotionFeedback > PLEASANT) {
            aproximation = VERY_PLEASANT;
        } else {
            if (emotionFeedback > LITTLE_PLEASANT) {
                aproximation = PLEASANT;
            } else {
                if (emotionFeedback > ZERO) {
                    aproximation = LITTLE_PLEASANT;
                } else {
                    if (emotionFeedback < UNPLEASANT) {
                        aproximation = VERY_UNPLEASANT;
                    } else {
                        if (emotionFeedback < LITTLE_PLEASANT) {
                            aproximation = UNPLEASANT;
                        } else {
                            aproximation = LITTLE_UNPLEASANT;
                        }
                    }
                }
            }
        }

        return aproximation;
    }

    public Object getActivityInCourse() {
        ResPwAActivity activity = bEstadoActividad.getActividadActual();
        Object activityInCourse = null;

        switch (activity) {
            case CUENTERIA:
                activityInCourse = (PreferenciaXCuento) bEstadoActividad.getCuentoActual();
                break;

            case MUSICOTERAPIA:
                activityInCourse = (PreferenciaXCancion) bEstadoActividad.getCancionActual();
                break;
        }

        return activityInCourse;

    }

    private PerfilPwa getPerfilBD(String cedula) {
        //conectarConBD
        return getFromDB(cedula);
    }

    PerfilPwa getFromDB(String cedula) {
        PerfilPwa perfil = RESPwABDInterface.getProfile(cedula);
        return perfil;
    }
    public void updateProfileInBelieves (String cedula)
    {
         bPerfilPwA.setPerfil(getPerfilBD(cedula));
    }

    public BEstadoInteraccion getbEstadoInteraccion() {
        return bEstadoInteraccion;
    }

    public void setbEstadoInteraccion(BEstadoInteraccion bEstadoInteraccion) {
        this.bEstadoInteraccion = bEstadoInteraccion;
    }

    public BEstadoEmocionalPwA getbEstadoEmocionalPwA() {
        return bEstadoEmocionalPwA;
    }

    public void setbEstadoEmocionalPwA(BEstadoEmocionalPwA bEstadoEmocionalPwA) {
        this.bEstadoEmocionalPwA = bEstadoEmocionalPwA;
    }

    public BEstadoActividad getbEstadoActividad() {
        return bEstadoActividad;
    }

    public void setbEstadoActividad(BEstadoActividad bEstadoActividad) {
        this.bEstadoActividad = bEstadoActividad;
    }

    public BPerfilPwA getbPerfilPwA() {
        return bPerfilPwA;
    }

    public void setbPerfilPwA(BPerfilPwA bPerfilPwA) {
        this.bPerfilPwA = bPerfilPwA;
    }

    public BEstadoRobot getbEstadoRobot() {
        return bEstadoRobot;
    }

    public void setbEstadoRobot(BEstadoRobot bEstadoRobot) {
        this.bEstadoRobot = bEstadoRobot;
    }

    public Map<String, List<String>> getImgCuentos() {
        return imgCuentos;
    }

    public void setImgCuentos(Map<String, List<String>> imgCuentos) {
        this.imgCuentos = imgCuentos;
    }

    public List<Imagen> getImgsPerfil() {
        return imgsPerfil;
    }

    public void setImgsPerfil(List<Imagen> imgsPerfil) {
        this.imgsPerfil = imgsPerfil;
    }

    public List<Imagen> getImagexTag(String tag) {
        List<Imagen> imagenes = new ArrayList<>();

        for (Imagen i : imgsPerfil) {
            for (String t : i.getTags()) {
                if (t.equalsIgnoreCase(tag)) {
                    imagenes.add(i);
                }
            }
        }
        return imagenes;
    }

    @Override
    public Believes clone() throws CloneNotSupportedException {
        return this;
    }

}

package com.InvBF.EntityFacade.install;

import com.InvBF.EntityFacade.TareasFacadeLocal;
import com.invbf.adminclientesapi.entity.Tarea;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;

@Singleton
public class TimerCheckoutTareasEstado {

    @EJB
    private TareasFacadeLocal tareasFacadeLocal;

    @PostConstruct
    @Schedule(hour = "*/1", minute = "0", second = "0", persistent = false)
    public void run() {
        List<Tarea> lista = tareasFacadeLocal.findAll();
        for (Tarea tarea : lista) {
            if (!tarea.getEstado().equals("VENCIDO")) {
                Calendar fechainicio = Calendar.getInstance();
                Calendar fechafinal = Calendar.getInstance();
                Calendar nowDate = Calendar.getInstance();
                fechainicio.setTime(tarea.getFechaInicio());
                fechafinal.setTime(tarea.getFechaFinalizacion());
                tarea.setEstado("POR INICIAR");
                if (fechainicio.before(nowDate)) {
                    tarea.setEstado("ACTIVO");
                }
                if (fechafinal.before(nowDate)) {
                    tarea.setEstado("VENCIDO");
                }
            }
        }
    }
}
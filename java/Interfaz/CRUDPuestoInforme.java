package Interfaz;

import Modelo.PuestoInforme;
import java.util.List;



public interface CRUDPuestoInforme {
    List<PuestoInforme> findAllPuestoInforme();
    PuestoInforme findPuestoById(int idPuesto);
    int savePuesto(PuestoInforme puesto);
    int updatePuesto(PuestoInforme puesto);
    int deletePuestoById(int idPuesto);
}

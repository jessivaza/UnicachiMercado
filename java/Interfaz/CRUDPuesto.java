package Interfaz;

import Modelo.Puesto;
import java.util.List;

public interface CRUDPuesto {
    List<Puesto> findAllPuestos();
    Puesto findPuestoById(int idPuesto);
    int savePuesto(Puesto puesto);
    int updatePuesto(Puesto puesto);
    int deletePuestoById(int idPuesto);
}

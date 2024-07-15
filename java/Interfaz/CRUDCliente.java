
package Interfaz;

import Modelo.ClienteDash;
import java.util.List;

import Modelo.DTOusuario;

public interface CRUDCliente{
    List<ClienteDash> findAllClientesDash();
    ClienteDash findClienteById(int idClienteDash);
    int saveCliente(ClienteDash cliente);
    int updateCliente(ClienteDash cliente);
    int deleteClienteById(int idClienteDash);
    public ClienteDash InicioSesion(String nombreUsuario, String contrasena);
}

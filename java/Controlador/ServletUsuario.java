/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controlador;

import DAO.ClienteDashDAO;
import Modelo.ClienteDash;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet("/ServletUsuario")
public class ServletUsuario extends HttpServlet {

     public ServletUsuario() {
        super();
    }

	
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       
        String tipo = request.getParameter("accion");

        if(tipo.equals("guardar"))
            guardarClientes(request, response);
        if(tipo.equals("editar"))
            editarClientes(request, response);
        if(tipo.equals("eliminar"))
            eliminarClientes(request, response);
        if(tipo.equals("login"))
            loginU(request, response);
  
    }

    protected void guardarClientes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nombreClie = request.getParameter("nombreCliePag");
        String apellidoClie = request.getParameter("apellidoCliePag");
        String direcionClie = request.getParameter("direccionCliePag");
        String dniClie = request.getParameter("dniCliePag");
        String emailClie = request.getParameter("emailCliePag");
        String contrasenaClie = request.getParameter("contrasenaCliePag");
        String telefonoClie  = request.getParameter("telefonoCliePag");
        
        
        ClienteDash cliente = new ClienteDash();   
        
        cliente.setNombre(nombreClie);
        cliente.setApellido(apellidoClie);
        cliente.setDireccion(direcionClie);
        cliente.setDni(dniClie);
        cliente.setEmail(emailClie);
        cliente.setContrasena(contrasenaClie);
        cliente.setTelefono(telefonoClie);
        
 
        int estado = new ClienteDashDAO().saveCliente(cliente);
        
         
        if(estado == 1){
            System.out.println("Cliente registrado");
            response.sendRedirect("ModalsAgregar/registroClientes.jsp"); 
        } else {
            System.out.println("Cliente no registrado");
            response.sendRedirect("ModalsAgregar/registroClientes.jsp"); 
        }
    }
    protected void editarClientes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idCliente = Integer.parseInt(request.getParameter("idClientePag"));
        String nombreClie = request.getParameter("nombreCliePag");
        String apellidoClie = request.getParameter("apellidoCliePag");
        String direcionClie = request.getParameter("direccionCliePag");
        String dniClie = request.getParameter("dniCliePag");
        String emailClie = request.getParameter("emailCliePag");
        String contrasenaClie = request.getParameter("contrasenaCliePag");
        String telefonoClie  = request.getParameter("telefonoCliePag");

        ClienteDash cliente = new ClienteDash();

        cliente.setIdCliente(idCliente);
        cliente.setNombre(nombreClie);
        cliente.setApellido(apellidoClie);
        cliente.setDireccion(direcionClie);
        cliente.setDni(dniClie);
        cliente.setEmail(emailClie);
        cliente.setContrasena(contrasenaClie);
        cliente.setTelefono(telefonoClie);

        int estado = new ClienteDashDAO().updateCliente(cliente);

        if(estado == 1){
            System.out.println("Cliente  actualizado");
            response.sendRedirect("Das.jsp"); 
        } else {
            System.out.println("Cliente  no actualizado");
            response.sendRedirect("Das.jsp"); 
        }
    }
    protected void eliminarClientes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idCliente = Integer.parseInt(request.getParameter("idClientePag"));
        
        int estado = new ClienteDashDAO().deleteClienteById(idCliente);
        
        if(estado==1){
            System.out.println("Cliente  eliminado exitosamente");
            response.sendRedirect("Das.jsp"); 
        } else{
            System.out.println("No fue eliminado el Cliente ");
            response.sendRedirect("Das.jsp"); 
        }
               
    }
    
    protected void loginU(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nombre = request.getParameter("nombrePag");
        String contra = request.getParameter("contrasenaPag");
        
        ClienteDash usu = new ClienteDashDAO().InicioSesion(nombre, contra);
        
        if (usu == null) {
                System.out.println("No se inicio sesion");
                response.sendRedirect("Registrarse.jsp");
        } else {
            
            //Obtener datos del usuario cliente si fue exitoso el login
            int idUsu = usu.getIdCliente();
            String nombreUsu = usu.getNombre();
            String apellidoUsu = usu.getApellido();
            String direccion = usu.getDireccion();
            String emailUsu = usu.getEmail();
            String contraUsu = usu.getContrasena();
            String telefonoUsu = usu.getTelefono();
            String dniUsu = usu.getDni();
            
            //se almacenan los datos del usuario o cliente ne la sesion http
            HttpSession session = request.getSession();
            session.setAttribute("idClie", idUsu);
            session.setAttribute("nombreClie", nombreUsu);
            session.setAttribute("apellidoClie", apellidoUsu);
            session.setAttribute("direccionClie", direccion);
            session.setAttribute("emailClie", emailUsu);
            session.setAttribute("contraClie", contraUsu);
            session.setAttribute("telefonoClie", telefonoUsu);
            session.setAttribute("dniClie", dniUsu);
            
            
            System.out.println("Sesion iniciada");
            response.sendRedirect("index.jsp");
        }
    }
      
}
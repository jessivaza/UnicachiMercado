package Controlador;

import Modelo.Cliente;
import DAO.ClienteDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ClienteControlador extends HttpServlet {

    private final String pagNuevo = "Jsp/PagRegistrarCliente.jsp";
    private ClienteDAO cliDAO = new ClienteDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String accion = request.getParameter("accion");

        switch (accion) {
            case "nuevo":
                Nuevo(request, response);
                break;
            case "guardar":
                Guardar(request, response);
                break;
            default:
                throw new AssertionError();
        }

    }

    protected void Guardar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        Cliente cl = new Cliente();
        cl.setNombres(request.getParameter("nombres"));
        cl.setApellidos(request.getParameter("apellidos"));
        cl.setTelefono(request.getParameter("telefono"));
        cl.setCorreo(request.getParameter("correo"));
        cl.setPassword(request.getParameter("password"));
        cl.setDni(request.getParameter("dni"));
        cl.setDireccion(request.getParameter("direccion"));
        cl.setRuc(request.getParameter("ruc"));
        cl.setDenominacion(request.getParameter("denominacion"));

        if (cliDAO.ExisteCorreo(cl.getCorreo().trim()) == false) {
            int result = cliDAO.Guardar(cl);
            if (result > 0) {
                request.getSession().setAttribute("success", "Cuenta registrada");
                response.sendRedirect("ClienteControlador?accion=nuevo");
                return;

            } else {
                request.getSession().setAttribute("error", "No se pudo registrar cuenta!");
            }
        } else {
            request.getSession().setAttribute("error", "El correo"+cl.getCorreo()+""
            +"ya se encuentra registrado");
        }

        request.setAttribute("cliente", cl);
        request.getRequestDispatcher(pagNuevo).forward(request, response);

    }

    protected void Nuevo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setAttribute("cliente", new Cliente());
        request.getRequestDispatcher(pagNuevo).forward(request, response);

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

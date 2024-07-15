package Controlador;

import Modelo.Cliente;
import Modelo.DetallePedido;
import Modelo.Pedido;
import Modelo.Producto;
import DAO.ClienteDAO;
import DAO.PedidoDAO;
import utils.Carrito;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@WebServlet("/PedidoControlador")
public class PedidoControlador extends HttpServlet {

    private PedidoDAO pedidoDAO = new PedidoDAO();
    private Carrito objCarrito = new Carrito();
    private final String pagLogin = "Jsp/PagLogin.jsp";
    private final String pagCarrito = "Jsp/PagCarrito.jsp";
    private final String pagMisPedidos = "Jsp/PagMisPedidos.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, JRException {
        response.setContentType("text/html;charset=UTF-8");

        String accion = request.getParameter("accion");

        switch (accion) {
            case "procesar":
                Procesar(request, response);
                break;
            case "mis_pedidos":
                MisPedidos(request, response);
                break;
            case "generar_factura":
                GenerarFactura(request, response);
                break;
            case "generar_boleta":
                GenerarBoleta(request, response);
                break;

            default:
                throw new AssertionError();
        }
    }

    protected void GenerarFactura(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Ruta relativa del archivo .jrxml
        String reportPath = "/Documentos/Factura1.jrxml";

        // Obtiene la ruta absoluta del archivo .jrxml
        String realPath = this.getServletConfig().getServletContext().getRealPath(reportPath);
        System.out.println("Ruta absoluta del archivo: " + realPath); // Verifica esta ruta en la consola

        // Verifica si el archivo existe en la ruta absoluta
        File reportFile = new File(realPath);
        if (!reportFile.exists()) {
            System.err.println("El archivo .jrxml no existe en la ruta: " + realPath);
            response.getWriter().println("El archivo .jrxml no existe en la ruta: " + realPath);
            return;
        }

        // Conectar a la base de datos y obtener los datos
        List<Map<String, ?>> data = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bd_unicachi", "root", ""); PreparedStatement statement = connection.prepareStatement(
                "SELECT bd_unicachi.detalle_pedido.id_ped, "
                + "bd_unicachi.detalle_pedido.id_detalle_ped, "
                + "bd_unicachi.detalle_pedido.id_prod, "
                + "bd_unicachi.detalle_pedido.precio, "
                + "bd_unicachi.detalle_pedido.cantidad, "
                + "bd_unicachi.producto.id_prod AS producto_id_prod, "
                + "bd_unicachi.producto.nombre, "
                + "bd_unicachi.producto.precio AS producto_precio, "
                + "bd_unicachi.producto.imagen, "
                + "bd_unicachi.producto.precioOnline, "
                + "bd_unicachi.producto.descuento, "
                + "bd_unicachi.pedido.id_ped AS pedido_id_ped, "
                + "bd_unicachi.pedido.id_cli, "
                + "bd_unicachi.pedido.fecha_ped, "
                + "bd_unicachi.pedido.total, "
                + "bd_unicachi.pedido.estado, "
                + "bd_unicachi.cliente.id_cli AS cliente_id_cli, "
                + "bd_unicachi.cliente.nombres, "
                + "bd_unicachi.cliente.password, "
                + "bd_unicachi.cliente.correo, "
                + "bd_unicachi.cliente.telefono, "
                + "bd_unicachi.cliente.apellidos "
                + "FROM bd_unicachi.detalle_pedido "
                + "INNER JOIN bd_unicachi.pedido ON "
                + "bd_unicachi.detalle_pedido.id_ped = bd_unicachi.pedido.id_ped "
                + "INNER JOIN bd_unicachi.producto ON "
                + "bd_unicachi.detalle_pedido.id_prod = bd_unicachi.producto.id_prod "
                + "INNER JOIN bd_unicachi.cliente ON "
                + "bd_unicachi.pedido.id_cli = bd_unicachi.cliente.id_cli")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("id_ped", resultSet.getInt("id_ped"));
                row.put("id_detalle_ped", resultSet.getInt("id_detalle_ped"));
                row.put("id_prod", resultSet.getInt("id_prod"));
                row.put("precio", resultSet.getBigDecimal("precio"));
                row.put("cantidad", resultSet.getInt("cantidad"));
                row.put("producto_id_prod", resultSet.getInt("producto_id_prod"));
                row.put("nombre", resultSet.getString("nombre"));
                row.put("producto_precio", resultSet.getDouble("producto_precio"));
                row.put("imagen", resultSet.getString("imagen"));
                row.put("precioOnline", resultSet.getBigDecimal("precioOnline"));
                row.put("descuento", resultSet.getString("descuento")); // Tratado como String
                row.put("pedido_id_ped", resultSet.getInt("pedido_id_ped"));
                row.put("id_cli", resultSet.getInt("id_cli"));
                row.put("fecha_ped", resultSet.getDate("fecha_ped"));
                row.put("total", resultSet.getBigDecimal("total"));
                row.put("estado", resultSet.getString("estado"));
                row.put("cliente_id_cli", resultSet.getInt("cliente_id_cli"));
                row.put("nombres", resultSet.getString("nombres"));
                row.put("password", resultSet.getString("password"));
                row.put("correo", resultSet.getString("correo"));
                row.put("telefono", resultSet.getString("telefono"));
                row.put("apellidos", resultSet.getString("apellidos"));
                data.add(row);
            }
        } catch (Exception e) {
            System.err.println("Error al obtener los datos de la base de datos: " + e.getMessage());
            e.printStackTrace(); // Imprime el stack trace para ver la causa del error
            response.getWriter().println("Error al obtener los datos de la base de datos: " + e.getMessage());
            return;
        }

        // Intentar abrir el archivo y compilarlo
        try (InputStream jasperStream = new FileInputStream(reportFile)) {
            System.out.println("Archivo .jrxml encontrado y cargado correctamente.");

            // Compilar el archivo .jrxml
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperStream);
            System.out.println("Archivo .jrxml compilado correctamente.");

            // Parámetros del reporte (si los hubiera)
            Map<String, Object> parameters = new HashMap<>();

            // Crear un JRDataSource con los datos obtenidos
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data);

            // Llenar el reporte con los datos
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
            System.out.println("Reporte llenado correctamente.");

            // Configurar la respuesta para PDF
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=Comprobante.pdf");

            // Exportar el reporte a PDF y escribirlo a la respuesta
            JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
            System.out.println("Comprobante generado correctamente.");
        } catch (JRException e) {
            System.err.println("Error al generar el comprobante: " + e.getMessage());
            e.printStackTrace(); // Imprime el stack trace para ver la causa del error
            response.getWriter().println("Error al generar el comprobante: " + e.getMessage());
        }
    }

    protected void GenerarBoleta(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Ruta relativa del archivo .jrxml
        String reportPath = "/Documentos/Boleta3.jrxml";

        // Obtiene la ruta absoluta del archivo .jrxml
        String realPath = this.getServletConfig().getServletContext().getRealPath(reportPath);
        System.out.println("Ruta absoluta del archivo: " + realPath); // Verifica esta ruta en la consola

        // Verifica si el archivo existe en la ruta absoluta
        File reportFile = new File(realPath);
        if (!reportFile.exists()) {
            System.err.println("El archivo .jrxml no existe en la ruta: " + realPath);
            response.getWriter().println("El archivo .jrxml no existe en la ruta: " + realPath);
            return;
        }

        // Conectar a la base de datos y obtener los datos
        List<Map<String, ?>> data = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bd_unicachi", "root", ""); PreparedStatement statement = connection.prepareStatement(
                "SELECT bd_unicachi.detalle_pedido.id_detalle_ped, "
                + "bd_unicachi.detalle_pedido.id_ped, "
                + "bd_unicachi.detalle_pedido.id_prod, "
                + "bd_unicachi.detalle_pedido.precio, "
                + "bd_unicachi.detalle_pedido.cantidad, "
                + "bd_unicachi.producto.id_prod, "
                + "bd_unicachi.pedido.estado, "
                + "bd_unicachi.pedido.id_ped, "
                + "bd_unicachi.pedido.id_cli, "
                + "bd_unicachi.pedido.fecha_ped, "
                + "bd_unicachi.pedido.total, "
                + "bd_unicachi.cliente.id_cli, "
                + "bd_unicachi.cliente.nombres, "
                + "bd_unicachi.cliente.apellidos, "
                + "bd_unicachi.cliente.telefono, "
                + "bd_unicachi.cliente.correo, "
                + "bd_unicachi.producto.nombre, "
                + "bd_unicachi.producto.precio, "
                + "bd_unicachi.producto.imagen, "
                + "bd_unicachi.producto.precioOnline, "
                + "bd_unicachi.producto.descuento, "
                + "bd_unicachi.cliente.password "
                + "FROM bd_unicachi.detalle_pedido "
                + "INNER JOIN bd_unicachi.pedido ON "
                + "bd_unicachi.detalle_pedido.id_ped = bd_unicachi.pedido.id_ped "
                + "INNER JOIN bd_unicachi.producto ON "
                + "bd_unicachi.detalle_pedido.id_prod = bd_unicachi.producto.id_prod "
                + "INNER JOIN bd_unicachi.cliente ON "
                + "bd_unicachi.pedido.id_cli = bd_unicachi.cliente.id_cli"
        )) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("id_detalle_ped", resultSet.getInt("id_detalle_ped"));
                row.put("id_ped", resultSet.getInt("id_ped"));
                row.put("id_prod", resultSet.getInt("id_prod"));
                row.put("precio", resultSet.getBigDecimal("precio"));
                row.put("cantidad", resultSet.getInt("cantidad"));
                row.put("producto_id_prod", resultSet.getInt("id_prod"));
                row.put("estado", resultSet.getString("estado"));
                row.put("pedido_id_ped", resultSet.getInt("id_ped"));
                row.put("id_cli", resultSet.getInt("id_cli"));
                row.put("fecha_ped", resultSet.getTimestamp("fecha_ped").toLocalDateTime());
                row.put("total", resultSet.getBigDecimal("total"));
                row.put("cliente_id_cli", resultSet.getInt("id_cli"));
                row.put("nombres", resultSet.getString("nombres"));
                row.put("apellidos", resultSet.getString("apellidos"));
                row.put("telefono", resultSet.getString("telefono"));
                row.put("correo", resultSet.getString("correo"));
                row.put("nombre", resultSet.getString("nombre"));
                row.put("producto_precio", resultSet.getBigDecimal("precio"));
                row.put("imagen", resultSet.getString("imagen"));
                row.put("precioOnline", resultSet.getBigDecimal("precioOnline"));
                row.put("descuento", resultSet.getString("descuento"));
                row.put("password", resultSet.getString("password"));
                data.add(row);
            }
        } catch (Exception e) {
            System.err.println("Error al obtener los datos de la base de datos: " + e.getMessage());
            e.printStackTrace(); // Imprime el stack trace para ver la causa del error
            response.getWriter().println("Error al obtener los datos de la base de datos: " + e.getMessage());
            return;
        }

        // Intentar abrir el archivo y compilarlo
        try (InputStream jasperStream = new FileInputStream(reportFile)) {
            System.out.println("Archivo .jrxml encontrado y cargado correctamente.");

            // Compilar el archivo .jrxml
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperStream);
            System.out.println("Archivo .jrxml compilado correctamente.");

            // Parámetros del reporte (si los hubiera)
            Map<String, Object> parameters = new HashMap<>();

            // Crear un JRDataSource con los datos obtenidos
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data);

            // Llenar el reporte con los datos
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
            System.out.println("Reporte llenado correctamente.");

            // Configurar la respuesta para PDF
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=Boleta.pdf");

            // Exportar el reporte a PDF y escribirlo a la respuesta
            JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
            System.out.println("Boleta generada correctamente.");
        } catch (JRException e) {
            System.err.println("Error al generar la boleta: " + e.getMessage());
            e.printStackTrace(); // Imprime el stack trace para ver la causa del error
            response.getWriter().println("Error al generar la boleta: " + e.getMessage());
        }
    }

    protected void MisPedidos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        if (request.getSession().getAttribute("usuario") != null) {
            Cliente objCli = (Cliente) request.getSession().getAttribute("usuario");
            ArrayList<Pedido> listaPed = pedidoDAO.ListarPorIdCliente(objCli.getIdCliente());
            request.setAttribute("pedidos", listaPed);
            request.getRequestDispatcher(pagMisPedidos).forward(request, response);
        } else {
            request.getRequestDispatcher(pagLogin).forward(request, response);
        }
    }

    protected void Procesar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        if (request.getSession().getAttribute("usuario") != null) {
            Pedido objPed = new Pedido();
            Cliente objCli = (Cliente) request.getSession().getAttribute("usuario");
            ArrayList<DetallePedido> lista = objCarrito.ObtenerSesion(request);
            double total = objCarrito.ImporteTotal(lista);
            objPed.setCliente(objCli);
            objPed.setDetalles(lista);
            objPed.setTotal(total);
            objPed.setEstado("Pendiente");

            int result = pedidoDAO.GenerarPedido(objPed);
            if (result > 0) {
                // Limpiar el carrito
                objCarrito.GuardarSesion(request, new ArrayList<DetallePedido>());
                request.getSession().setAttribute("success", "Pedido procesado de forma correcta");

                // Generar el reporte y exportarlo como PDF
                try {
                    // Cargar el archivo .jrxml
                    InputStream reportFile = this.getClass().getResourceAsStream("/ReportesB/Boleta3.jrxml");
                    if (reportFile == null) {
                        throw new FileNotFoundException("No se encontró el archivo Boleta3.jrxml");

                    }
                    JasperReport jasperReport = JasperCompileManager.compileReport(reportFile);

                    // Parámetros para el reporte
                    Map<String, Object> parametros = new HashMap<>();
                    parametros.put("clienteNombre", objCli.getNombres()); // Ejemplo de parámetro
                    parametros.put("detalleLista", lista);
                    // Crear el origen de datos
                    JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(lista);

                    // Llenar el reporte con datos y generar PDF
                    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, dataSource);

                    // Guardar el reporte compilado
                    JasperExportManager.exportReportToPdfFile(jasperPrint, "application/pdf");

                    // Redirigir al usuario
                    response.sendRedirect("PedidoControlador?accion=mis_pedidos");
                } catch (JRException e) {
                    e.printStackTrace();
                    request.getSession().setAttribute("error", "Error al generar el reporte PDF");
                    response.sendRedirect(pagCarrito);
                }
            } else {
                request.getSession().setAttribute("error", "No se pudo procesar pedido");
                request.getRequestDispatcher(pagCarrito).forward(request, response);
            }
        } else {
            request.getSession().setAttribute("error", "Debe autentificarse primero antes de procesar su compra");
            request.getRequestDispatcher(pagLogin).forward(request, response);
        }
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
        try {
            processRequest(request, response);
        } catch (JRException ex) {
            Logger.getLogger(PedidoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (JRException ex) {
            Logger.getLogger(PedidoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
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

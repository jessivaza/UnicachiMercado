
package Controlador;

import DAO.ClienteDashDAO;
import Modelo.ClienteDash;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


@WebServlet("/ServletCliente")
public class ServletCliente extends HttpServlet {

    public ServletCliente() {
        super();
    }

    
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String tipo = request.getParameter("accion");

        if (tipo.equals("guardar")) {
            guardarCliente(request, response);
        }
        if (tipo.equals("editar")) {
            editarCliente(request, response);
        }
        if (tipo.equals("eliminar")) {
            eliminarCliente(request, response);
        }
        if (tipo.equals("exel")) {
            exelCliente(request, response);
        }
        if (tipo.equals("pdf"))
            try {
            pdfCliente(request, response);
        } catch (JRException ex) {
            Logger.getLogger(ServletCliente.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    protected void guardarCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nombreP = request.getParameter("nombreCliePag");
        String apellidoP = request.getParameter("apellidoCliePag");
        String direccionP = request.getParameter("direccionCliePag");
        String dniP = request.getParameter("dniCliePag");
        String emailP = request.getParameter("emailCliePag");
        String contrasenaP = request.getParameter("contrasenaCliePag");
        String telefonoP = request.getParameter("telefonoCliePag");

        ClienteDash cliente = new ClienteDash();
        cliente.setNombre(nombreP);
        cliente.setApellido(apellidoP);
        cliente.setDireccion(direccionP);
        cliente.setDni(dniP);
        cliente.setEmail(emailP);
        cliente.setContrasena(contrasenaP);
        cliente.setTelefono(telefonoP);

        int estado = new ClienteDashDAO().saveCliente(cliente);

        if (estado == 1) {
            System.out.println("Cliente registrado");
            response.sendRedirect("ModalsAgregar/registroCliente.jsp");
        } else {
            System.out.println("Producto no registrado");
            response.sendRedirect("ModalsAgregar/registroCliente.jsp");
        }
    }

    protected void editarCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         String nombreP = request.getParameter("nombreCliePag");
        String apellidoP = request.getParameter("apellidoCliePag");
        String direccionP = request.getParameter("direccionCliePag");
        String dniP = request.getParameter("dniCliePag");
        String emailP = request.getParameter("emailCliePag");
        String contrasenaP = request.getParameter("contrasenaCliePag");
        String telefonoP = request.getParameter("telefonoCliePag");

        ClienteDash cliente = new ClienteDash();
        cliente.setNombre(nombreP);
        cliente.setApellido(apellidoP);
        cliente.setDireccion(direccionP);
        cliente.setDni(dniP);
        cliente.setEmail(emailP);
        cliente.setContrasena(contrasenaP);
        cliente.setTelefono(telefonoP);

        int estado = new ClienteDashDAO().updateCliente(cliente);

        if (estado == 1) {
            System.out.println("Cliente actualizado");
            response.sendRedirect("Das.jsp");
        } else {
            System.out.println("Cliente no actualizado");
            response.sendRedirect("Das.jsp");
        }
    }

    protected void eliminarCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idClienteP = Integer.parseInt(request.getParameter("idClientePag"));

        int estado = new ClienteDashDAO().deleteClienteById(idClienteP);

        if (estado == 1) {
            System.out.println("Cliente eliminado exitosamente");
            response.sendRedirect("Das.jsp");
        } else {
            System.out.println("No fue eliminado el Cliente");
            response.sendRedirect("Das.jsp");
        }

    }

    protected void exelCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Obtener la lista de categorias
        List<ClienteDash> lista = new ClienteDashDAO().findAllClientesDash();

        // Crear un nuevo libro de trabajo
        XSSFWorkbook workbook = new XSSFWorkbook();

        // Crear una hoja en el libro de trabajo
        XSSFSheet sheet = workbook.createSheet("Cliente");

        // Crear la fila de encabezado
        XSSFRow headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("IDCliente");
        headerRow.createCell(1).setCellValue("Nombre");
        headerRow.createCell(2).setCellValue("Apellido");
        headerRow.createCell(3).setCellValue("Direccion");
        headerRow.createCell(4).setCellValue("Dni");
        headerRow.createCell(5).setCellValue("email");
        headerRow.createCell(6).setCellValue("contrasena");
        headerRow.createCell(7).setCellValue("telefono");

        // Llenar el resto de filas con los datos de las categorias
        int rowNum = 1;
        for (ClienteDash Cliente : lista) {
            XSSFRow row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(Cliente.getIdCliente());
            row.createCell(1).setCellValue(Cliente.getNombre());
            row.createCell(2).setCellValue(Cliente.getApellido());
            row.createCell(3).setCellValue(Cliente.getDireccion());
            row.createCell(4).setCellValue(Cliente.getDni());
            row.createCell(5).setCellValue(Cliente.getEmail());
            row.createCell(6).setCellValue(Cliente.getContrasena());
            row.createCell(7).setCellValue(Cliente.getTelefono());
        }

        // Escribir el libro de trabajo en un flujo de salida
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=Cliente.xlsx");
        try (OutputStream outputStream = response.getOutputStream()) {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Cerrar el libro de trabajo
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    protected void pdfCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, JRException {
        // Obtener la lista de categorías
         List<ClienteDash> lista = new ClienteDashDAO().findAllClientesDash();

        if (!lista.isEmpty()) {
            try (InputStream jasperStream = this.getServletConfig().getServletContext().getResourceAsStream("/Documentos/reporteCliente.jrxml")) {
                // Cargar y compilar el archivo .jrxml
                JasperReport jasperReport = JasperCompileManager.compileReport(jasperStream);

                // Crear el JRDataSource usando la lista de categorías
                JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(lista);

                // Parámetros del reporte (si los hubiera)
                Map<String, Object> parameters = new HashMap<>();

                // Llenar el reporte con los datos
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

                // Configurar la respuesta para PDF
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "inline; filename=ClienteReport.pdf");

                // Exportar el reporte a PDF y escribirlo a la respuesta
                JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());

                System.out.println("Informe generado correctamente.");
            } catch (JRException e) {
                System.err.println("Error al generar el informe: " + e.getMessage());
                response.getWriter().println("Error al generar el informe: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("No hay productos disponibles.");
            response.getWriter().println("No hay productos disponibles.");
        }

    }

}

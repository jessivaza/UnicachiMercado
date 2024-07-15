/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controlador;

import DAO.PuestoDAO;
import DAO.PuestoInformeDAO;
import Modelo.Puesto;
import Modelo.PuestoInforme;
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
/**
 *
 * @author Brandon
 */
@WebServlet("/ServletPuestos")
public class ServletPuestos extends HttpServlet {

    public ServletPuestos() {
        super();
    }

	
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       
        String tipo = request.getParameter("accion");

        if(tipo.equals("guardar"))
            guardarPuesto(request, response);
        if(tipo.equals("editar"))
            editarPuesto(request, response);
        if(tipo.equals("eliminar"))
            eliminarPuesto(request, response);
        if(tipo.equals("exel"))
            exelPuesto(request, response);
        if(tipo.equals("pdf"))
            try {
                pdfPuesto(request, response);
        } catch (JRException ex) {
            Logger.getLogger(ServletPuestos.class.getName()).log(Level.SEVERE, null, ex);
        }
  
    }

    protected void guardarPuesto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idCategoriaPu = Integer.parseInt(request.getParameter("idCategoriaPuPag"));
        int idProductoPu = Integer.parseInt(request.getParameter("idProductoPuPag"));
        int idClientePu = Integer.parseInt(request.getParameter("idClientePuPag"));
        String duenoPu = request.getParameter("duenoPuPag");
        boolean estadoPu = Boolean.parseBoolean(request.getParameter("estadoPuPag"));
         
        Puesto puesto = new Puesto();   
        
        puesto.setIdCategoria(idCategoriaPu);
        puesto.setIdProducto(idProductoPu);
        puesto.setIdCliente(idClientePu);
        puesto.setDueño(duenoPu);
        puesto.setEstado(estadoPu);
           
        int estado = new PuestoDAO().savePuesto(puesto);
        
         
        if(estado == 1){
            System.out.println("Producto registrado");
            response.sendRedirect("ModalsAgregar/registroProducto.jsp"); 
        } else {
            System.out.println("Producto no registrado");
            response.sendRedirect("ModalsAgregar/registroProducto.jsp"); 
        }
    }
    protected void editarPuesto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idPuestosPu = Integer.parseInt(request.getParameter("idPuestoPuPag"));
        int idCategoriaPu = Integer.parseInt(request.getParameter("idCategoriaPuPag"));
        int idProductoPu = Integer.parseInt(request.getParameter("idProductoPuPag"));
        int idClientePu = Integer.parseInt(request.getParameter("idClientePuPag"));
         String duenoPu = request.getParameter("duenoPuPag");
        boolean estadoPu = Boolean.parseBoolean(request.getParameter("estadoPuPag"));

        Puesto puesto = new Puesto();  

        puesto.setIdPuesto(idPuestosPu);
        puesto.setIdCategoria(idCategoriaPu);
        puesto.setIdProducto(idProductoPu);
        puesto.setIdCliente(idClientePu);
        puesto.setDueño(duenoPu);
        puesto.setEstado(estadoPu);


        int estado = new PuestoDAO().updatePuesto(puesto);

        if(estado == 1){
            System.out.println("Producto actualizado");
            response.sendRedirect("Das.jsp"); 
        } else {
            System.out.println("Producto no actualizado");
            response.sendRedirect("Das.jsp"); 
        }
    }
    protected void eliminarPuesto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idPuestosPu = Integer.parseInt(request.getParameter("idPuestoPuPag"));
        
        int estado = new PuestoDAO().deletePuestoById(idPuestosPu);
        
        if(estado==1){
            System.out.println("Producto eliminado exitosamente");
            response.sendRedirect("Das.jsp"); 
        } else{
            System.out.println("No fue eliminado el producto");
            response.sendRedirect("Das.jsp"); 
        }
               
    }
    
      protected void exelPuesto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        // Obtener la lista de puestos
        List<PuestoInforme> lista = new PuestoInformeDAO().findAllPuestoInforme();
        
        // Crear un nuevo libro de trabajo
        XSSFWorkbook workbook = new XSSFWorkbook();
        
        // Crear una hoja en el libro de trabajo
        XSSFSheet sheet = workbook.createSheet("Puestos");
        
        // Crear la fila de encabezado
        XSSFRow headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Categoria");
        headerRow.createCell(2).setCellValue("Producto");
        headerRow.createCell(3).setCellValue("Cliente");
        headerRow.createCell(4).setCellValue("Dueño del puesto");
        headerRow.createCell(5).setCellValue("Estado");
        
        // Llenar el resto de filas con los datos de las categorias
        int rowNum = 1;
        for (PuestoInforme puestoInforme : lista) {
            XSSFRow row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(puestoInforme.getIdPuestoInforme());
            row.createCell(1).setCellValue(puestoInforme.getCategoriaPuestoInforme());
            row.createCell(2).setCellValue(puestoInforme.getProductoPuestoInforme());
            row.createCell(3).setCellValue(puestoInforme.getClientePuestoInforme());
            row.createCell(4).setCellValue(puestoInforme.getDuenoPuestoInforme());
            row.createCell(5).setCellValue(puestoInforme.getEstadoPuestoInforme());
        }
        
        // Escribir el libro de trabajo en un flujo de salida
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=Puestos.xlsx");
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
    protected void pdfPuesto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, JRException {
         // Obtener la lista de categorías
         List<PuestoInforme> lista = new PuestoInformeDAO().findAllPuestoInforme();

        if (!lista.isEmpty()) {
            try (InputStream jasperStream = this.getServletConfig().getServletContext().getResourceAsStream("/Documentos/reportePuestos.jrxml")) {
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
                response.setHeader("Content-Disposition", "inline; filename=Puestos.pdf");

                // Exportar el reporte a PDF y escribirlo a la respuesta
                JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());

                System.out.println("Informe generado correctamente.");
            } catch (JRException e) {
                System.err.println("Error al generar el informe: " + e.getMessage());
                response.getWriter().println("Error al generar el informe: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("No hay categorías disponibles.");
            response.getWriter().println("No hay categorías disponibles.");
        }
    }
      
}
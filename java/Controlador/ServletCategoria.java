/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controlador;

import DAO.CategoriaDAO;
import Modelo.Categoria;
import com.google.gson.Gson;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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


import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;




@WebServlet("/ServletCategoria")
public class ServletCategoria extends HttpServlet {

    public ServletCategoria() {
        super();
    }
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       
        String tipo = request.getParameter("accion");

        if(tipo.equals("guardar"))
            guardarCategoria(request, response);
        if(tipo.equals("editar"))
            editarCategoria(request, response);
        if(tipo.equals("eliminar"))
            eliminarCategoria(request, response);
        if(tipo.equals("exel"))
            exelCategoria(request, response);
        if(tipo.equals("pdf"))
            try {
                 pdfCategoria(request, response);
        } catch (JRException ex) {
            Logger.getLogger(ServletCategoria.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(tipo.equals("graficolis"))
            graficoLis(request, response);
  
    }
    
    protected void guardarCategoria(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         String nombreC = request.getParameter("nombreCPag");
         String descripcionC = request.getParameter("descripcionCPag");
         int cantidadProC = Integer.parseInt(request.getParameter("cantidadProPag"));
         double precioC = Double.parseDouble(request.getParameter("precioCPag"));
         String popularidadCP = request.getParameter("popularidadCPag");
         
         Categoria categoria = new Categoria();   
         
         categoria.setNombreCategoria(nombreC);
         categoria.setDescripcion(descripcionC);
         categoria.setCantidadProductos(cantidadProC);
         categoria.setPrecioPromedio(precioC);
         categoria.setPopularidad(popularidadCP);
         
         int estado = new CategoriaDAO().save(categoria);
         
         if(estado == 1){
             System.out.println("Producto registrado");
             response.sendRedirect("ModalsAgregar/registroProducto.jsp"); 
         } else {
             System.out.println("Producto no registrado");
             response.sendRedirect("ModalsAgregar/registroProducto.jsp"); 
         }
    }
    protected void editarCategoria(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idCategoriaP = Integer.parseInt(request.getParameter("idCategoriaPag"));
        String nombreC = request.getParameter("nombreCPag");
        String descripcionC = request.getParameter("descripcionCPag");
        int cantidadProC = Integer.parseInt(request.getParameter("cantidadProPag"));
        double precioC = Double.parseDouble(request.getParameter("precioCPag"));
        String popularidadCP = request.getParameter("popularidadCPag");

        Categoria categoria = new Categoria();   

        categoria.setIdCategoria(idCategoriaP);
        categoria.setNombreCategoria(nombreC);
        categoria.setDescripcion(descripcionC);
        categoria.setCantidadProductos(cantidadProC);
        categoria.setPrecioPromedio(precioC);
        categoria.setPopularidad(popularidadCP);

        int estado = new CategoriaDAO().update(categoria);

        if(estado == 1){
            System.out.println("Producto actualizado");
            response.sendRedirect("Das.jsp"); 
        } else {
            System.out.println("Producto no actualizado");
            response.sendRedirect("Das.jsp"); 
        }
    }
    protected void eliminarCategoria(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idCategoriaP = Integer.parseInt(request.getParameter("idCategoriaPag"));
        
        int estado = new CategoriaDAO().deleteById(idCategoriaP);
        
        if(estado==1){
            System.out.println("Producto eliminado exitosamente");
           response.sendRedirect("Das.jsp"); 
        } else{
            System.out.println("No fue eliminado el producto");
            response.sendRedirect("Das.jsp"); 
        }
               
    }
    
     protected void exelCategoria(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         
        // Obtener la lista de categorias
        List<Categoria> lista = new CategoriaDAO().findAllCategoria();
        
        // Crear un nuevo libro de trabajo
        XSSFWorkbook workbook = new XSSFWorkbook();
        
        // Crear una hoja en el libro de trabajo
        XSSFSheet sheet = workbook.createSheet("Categorias");
        
        // Crear la fila de encabezado
        XSSFRow headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Nombre");
        headerRow.createCell(2).setCellValue("Descripción");
        headerRow.createCell(3).setCellValue("Cantidad de Productos");
        headerRow.createCell(4).setCellValue("Precio Promedio");
        headerRow.createCell(5).setCellValue("Popularidad");
        
        // Llenar el resto de filas con los datos de las categorias
        int rowNum = 1;
        for (Categoria categoria : lista) {
            XSSFRow row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(categoria.getIdCategoria());
            row.createCell(1).setCellValue(categoria.getNombreCategoria());
            row.createCell(2).setCellValue(categoria.getDescripcion());
            row.createCell(3).setCellValue(categoria.getCantidadProductos());
            row.createCell(4).setCellValue(categoria.getPrecioPromedio());
            row.createCell(5).setCellValue(categoria.getPopularidad());
        }
        
        // Escribir el libro de trabajo en un flujo de salida
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=Categorias.xlsx");
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
     
    protected void pdfCategoria(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, JRException {
        // Obtener la lista de categorías
        List<Categoria> lista = new CategoriaDAO().findAllCategoria();

        if (!lista.isEmpty()) {
            try (InputStream jasperStream = this.getServletConfig().getServletContext().getResourceAsStream("/Documentos/reporteCategoria.jrxml")) {
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
                response.setHeader("Content-Disposition", "inline; filename=CategoriaReport.pdf");

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
    
protected void graficoLis(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Ingreso a la función");

        List<Categoria> lista = new CategoriaDAO().findAllCategoria();

        
        List<String> nombresCategorias = new ArrayList<>();
        List<Integer> cantidadesCategorias = new ArrayList<>();

        
        for (Categoria categoria : lista) {
            nombresCategorias.add(categoria.getNombreCategoria());
            cantidadesCategorias.add(categoria.getCantidadProductos());
        }

        
        Gson gson = new Gson();
        String nombresCategoriasJson = gson.toJson(nombresCategorias);
        String cantidadesCategoriasJson = gson.toJson(cantidadesCategorias);

        HttpSession session = request.getSession();
        session.setAttribute("nombresCategoriasJson", nombresCategoriasJson);
        session.setAttribute("cantidadesCategoriasJson", cantidadesCategoriasJson);

        // Redirigir a una página JSP para renderizar el gráfico
        RequestDispatcher dispatcher = request.getRequestDispatcher("Grafico.jsp");
        dispatcher.forward(request, response);
    }
    
}
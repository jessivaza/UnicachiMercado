/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controlador;

import DAO.ProductoDashDAO;
import Modelo.ProductoDash;
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

@WebServlet("/ServletProducto")
public class ServletProducto extends HttpServlet {

    public ServletProducto() {
        super();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String tipo = request.getParameter("accion");

        if (tipo.equals("guardar")) {
            guardarProducto(request, response);
        }
        if (tipo.equals("editar")) {
            editarProducto(request, response);
        }
        if (tipo.equals("eliminar")) {
            eliminarProducto(request, response);
        }
        if (tipo.equals("exel")) {
            exelProducto(request, response);
        }
        if (tipo.equals("pdf")) {
            try {
                pdfProducto(request, response);
            } catch (JRException ex) {
                Logger.getLogger(ServletProducto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    protected void guardarProducto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String descripcionP = request.getParameter("descripcionPag");
        double precioP = Double.parseDouble(request.getParameter("precioPag"));
        int cantidadP = Integer.parseInt(request.getParameter("cantidadPag"));
        String popularidadP = request.getParameter("popularidadPag");
        int idCategoriaP = Integer.parseInt(request.getParameter("idCategoriaPag"));

        ProductoDash producto = new ProductoDash();

        producto.setDescripcion(descripcionP);
        producto.setPrecio(precioP);
        producto.setCantidad(cantidadP);
        producto.setPopularidad(popularidadP);
        producto.setIdCategoria(idCategoriaP);
        int estado = new ProductoDashDAO().save(producto);

        if (estado == 1) {
            System.out.println("Producto registrado");
            response.sendRedirect("Das.jsp");
        } else {
            System.out.println("Producto no registrado");
            response.sendRedirect("ModalsAgregar/registroProducto.jsp");
        }
    }

    protected void editarProducto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idProductoP = Integer.parseInt(request.getParameter("idProductoPag"));
        String descripcionP = request.getParameter("descripcionPag");
        double precioP = Double.parseDouble(request.getParameter("precioPag"));
        int cantidadP = Integer.parseInt(request.getParameter("cantidadPag"));
        String popularidadP = request.getParameter("popularidadPag");
        int idCategoriaP = Integer.parseInt(request.getParameter("idCategoriaPag"));

        ProductoDash producto = new ProductoDash();

        producto.setIdProducto(idProductoP);
        producto.setDescripcion(descripcionP);
        producto.setPrecio(precioP);
        producto.setCantidad(cantidadP);
        producto.setPopularidad(popularidadP);
        producto.setIdCategoria(idCategoriaP);

        int estado = new ProductoDashDAO().update(producto);

        if (estado == 1) {
            System.out.println("Producto actualizado");
            response.sendRedirect("Das.jsp");
        } else {
            System.out.println("Producto no actualizado");
            response.sendRedirect("Das.jsp");
        }
    }

    protected void eliminarProducto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idProductoP = Integer.parseInt(request.getParameter("idProductoPag"));

        int estado = new ProductoDashDAO().deleteById(idProductoP);

        if (estado == 1) {
            System.out.println("Producto eliminado exitosamente");
            response.sendRedirect("Das.jsp");
        } else {
            System.out.println("No fue eliminado el producto");
            response.sendRedirect("Das.jsp");
        }

    }

    protected void exelProducto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Obtener la lista de categorias
        List<ProductoDash> lista = new ProductoDashDAO().findAllProducto();

        // Crear un nuevo libro de trabajo
        XSSFWorkbook workbook = new XSSFWorkbook();

        // Crear una hoja en el libro de trabajo
        XSSFSheet sheet = workbook.createSheet("Producto");

        // Crear la fila de encabezado
        XSSFRow headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Descripción");
        headerRow.createCell(2).setCellValue("Precio");
        headerRow.createCell(3).setCellValue("Cantidad");
        headerRow.createCell(4).setCellValue("Popularidad");
        headerRow.createCell(5).setCellValue("IdCategoria");
        headerRow.createCell(6).setCellValue("Imagen");

        // Llenar el resto de filas con los datos de las categorias
        int rowNum = 1;
        for (ProductoDash Producto : lista) {
            XSSFRow row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(Producto.getIdProducto());
            row.createCell(1).setCellValue(Producto.getDescripcion());
            row.createCell(2).setCellValue(Producto.getPrecio());
            row.createCell(3).setCellValue(Producto.getCantidad());
            row.createCell(4).setCellValue(Producto.getPopularidad());
            row.createCell(5).setCellValue(Producto.getIdCategoria());
            row.createCell(6).setCellValue(Producto.getImagen());
        }

        // Escribir el libro de trabajo en un flujo de salida
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=Producto.xlsx");
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

    protected void pdfProducto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, JRException {
        // Obtener la lista de categorías
        List<ProductoDash> lista = new ProductoDashDAO().findAllProducto();

        if (!lista.isEmpty()) {
            try (InputStream jasperStream = this.getServletConfig().getServletContext().getResourceAsStream("/Documentos/reporteProducto.jrxml")) {
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
                response.setHeader("Content-Disposition", "inline; filename=productosReport.pdf");

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

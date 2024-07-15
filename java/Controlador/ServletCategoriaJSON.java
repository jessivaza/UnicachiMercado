/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controlador;

import DAO.CategoriaDAO;
import Modelo.Categoria;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


@WebServlet("/ServletCategoriaJSON")
public class ServletCategoriaJSON extends HttpServlet {

    public ServletCategoriaJSON() {
        super();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Categoria> lista = new CategoriaDAO().findAllCategoria();
        Gson gson = new Gson();
        String json = gson.toJson(lista);
        List<String> nombresCategorias = new ArrayList<>();
        List<Integer> cantidadesCategorias = new ArrayList<>();

        for (Categoria categoria : lista) {
            nombresCategorias.add(categoria.getNombreCategoria());
            cantidadesCategorias.add(categoria.getCantidadProductos());
        }

        String nombresCategoriasJson = gson.toJson(nombresCategorias);
        String cantidadesCategoriasJson = gson.toJson(cantidadesCategorias);

        HttpSession session = request.getSession();
        session.setAttribute("nombresCategoriasJson", nombresCategoriasJson);
        session.setAttribute("cantidadesCategoriasJson", cantidadesCategoriasJson);
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter pw = response.getWriter();
        pw.print(json);
    }

}

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="ModeloDAO.ProductoDAO"%>
<%@page import="Modelo.Producto"%>
<%@page import="java.util.ArrayList"%>

<!DOCTYPE html>
<html>
    <head> 
        <title>Catálogo de Productos</title>
        <meta name="format-detection" content="telephone=no">
        <meta name="viewport" content="width=device-width, height=device-height, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta charset="utf-8">
        <link rel="icon" href="IMG/img/logo1.png" type="image/x-icon">
        <link href="../CSS/css/cssMenu.css" rel="stylesheet" type="text/css"/>

    </head>
    <body>
        <%@ include file="navegacion.jsp" %>
        <div class="container mt-4">
            <h3 class="text-center">Catálogo de Productos</h3>
            <hr />
            <div class="product-grid">
                <% 
                    ProductoDAO productoDAO = new ProductoDAO();
                    ArrayList<Producto> productos = productoDAO.ListarTodos();
                    for (Producto producto : productos) {
                %>
                <div class="card product-card">
                    <form action="../CarritoControlador" method="get">
                        <img src="../IMG/img/<%= producto.getImagen() %>" alt="<%= producto.getNombre() %>" class="card-img-top">
                        <div class="card-body">
                            <p class="fw-bold"><%= producto.getNombre() %></p>
                            <input type="hidden" name="accion" value="agregar">
                            <input type="hidden" name="id" value="<%= producto.getIdProd() %>">
                            <div class="d-flex justify-content-between align-items-center">
                                <button type="submit" class="btn btn-sm btn-primary">
                                    <i class="fa fa-shopping-cart"></i> Agregar al Carrito
                                </button>
                                <span class="price">S/<%= producto.getPrecio() %></span>
                            </div>
                        </div>
                    </form>
                </div>
                <% } %>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossorigin="anonymous"></script>
    </body>
</html>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="DAO.ProductoDAO"%>
<%@page import="Modelo.Producto"%>
<%@page import="java.util.ArrayList"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Catálogo de Productos</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css" integrity="sha512-SnH5WK+bZxgPHs44uWIX+LLJAJ9/2PkPKZ5QiAj6Ta86w+fsb2TkcmfRyVX3pBnMFcV7oQPJkl9QevSCWr3W6A==" crossorigin="anonymous" referrerpolicy="no-referrer" />
        <link href="../CSS/css/cssMenu.css" rel="stylesheet" type="text/css"/>
        <style>
            .col-md-2-4 {
                flex: 0 0 auto;
                width: 25%; /* Para las cuadrado 5x5*/
            }
        </style>
    </head>
    <body>
        <%@ include file="navegacion.jsp" %>
        <div class="container mt-4">
            <h3 class="text-center">Catálogo de Productos</h3>
            <hr />
            <div class="row">
                <% 
                    ProductoDAO productoDAO = new ProductoDAO();
                    ArrayList<Producto> productos = productoDAO.ListarTodos();
                    for (Producto producto : productos) {
                %>
                <div class="col-md-2-4 mb-4">
                    <div class="card product-card h-100">
                        <div>
                            <span class="descuento"><%= producto.getDescuento() %></span>
                        </div>
                        <form action="../CarritoControlador" method="get">
                            <div class="card-img-container">
                                <img src="../IMG/img/<%= producto.getImagen() %>" alt="<%= producto.getNombre() %>" class="card-img-top">
                            </div>
                            <div class="card-body">
                                <p class="fw-bold"><%= producto.getNombre() %></p>
                                <input type="hidden" name="accion" value="agregar">
                                <input type="hidden" name="id" value="<%= producto.getIdProd() %>">
                                <div>
                                    <span class="price2">Precio Online: S/<%= producto.getPrecioOnline() %></span>
                                </div>
                                <div>
                                    <span class="price regular-price">Precio Regular: S/<%= producto.getPrecio() %></span>
                                </div>
                                <div class="d-flex justify-content-center align-items-center">
                                    <button type="submit" class="btn btn-sm btn-primary">
                                        <i class="fa fa-shopping-cart"></i> Agregar al Carrito
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
                <% } %>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossorigin="anonymous"></script>
    </body>
</html>

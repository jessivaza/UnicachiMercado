<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Modelo.Cliente" %>
<link href="../CSS/css/cssnavegacion.css" rel="stylesheet" type="text/css"/>
<nav class="navbar navbar-expand-lg navbar-light bg-primary shadow-sm">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">
            <img src="../IMG/logo1.png" alt="Logo" class="img-fluid" style="width: 150px; height: auto;"/>
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link" aria-current="page" href="carrito.jsp">
                        <i class="fa fa-home"></i> Categorias
                    </a>
                </li>
                <li class="nav-item">
                    <a href="../CarritoControlador?accion=listar" class="nav-link">
                        <i class="fa fa-shopping-cart"></i> 
                        (<span class="fw-bold"><%= (session.getAttribute("carrito") != null) ? ((ArrayList) session.getAttribute("carrito")).size() : 0 %></span>) Carrito
                    </a>
                </li>
                <% 
                Cliente cliente = (Cliente) session.getAttribute("usuario");
                if (cliente != null) { 
                %>
                <li class="nav-item">
                    <a href="../PedidoControlador?accion=mis_pedidos" class="nav-link">
                        <i class="fa fa-receipt"></i> Mis pedidos
                    </a>
                </li>
                <% 
                } 
                %>
            </ul>
            <form class="d-flex me-auto search-form">
                <input class="form-control me-2" type="search" placeholder="Buscar" aria-label="Buscar">
                <button class="btn btn-outline-light" type="submit">Buscar</button>
            </form>
            <form class="d-flex">
                <% 
                if (cliente == null) { 
                %>
                    <a href="../ClienteControlador?accion=nuevo" class="btn btn-outline-light me-2">
                        <i class="fas fa-user-plus"></i> Registrarse
                    </a>
                    <a href="../AuthControlador?accion=login" class="btn btn-outline-light">
                        <i class="fas fa-user-lock"></i> Login
                    </a>
                <% 
                } else { 
                %>
                    <span class="btn btn-light"><%= cliente.nombresCompletos() %></span>
                    &nbsp;
                    <a href="../AuthControlador?accion=logout" class="btn btn-outline-light">
                        <i class="fa fa-sign-out-alt"></i> Cerrar Sesion
                    </a>
                <% 
                } 
                %>
            </form>
        </div>
    </div>
</nav>


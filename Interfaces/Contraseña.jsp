<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.sql.*, javax.servlet.*, javax.servlet.http.*" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cambiar Contraseña</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            background-color: #f8f9fa;
        }
        .change-password-form {
            background: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            text-align: center;
            max-width: 400px;
            width: 100%;
        }
        .change-password-form h2 {
            margin-bottom: 20px;
        }
        .change-password-form .form-control {
            border: 2px solid #e6e6e6;
            border-radius: 30px;
            padding: 10px;
            font-size: 14px;
        }
        .change-password-form .btn {
            width: 100%;
            padding: 15px;
            border-radius: 30px;
            background-color: #007bff;
            color: #fff;
        }
        .change-password-form .btn:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <div class="change-password-form">
        <h2>Cambiar Contraseña</h2>
        <form method="post">
            <div class="form-group">
                <label for="email">Correo Electrónico</label>
                <input type="email" class="form-control" id="email" name="email" required>
            </div>
            <div class="form-group">
                <label for="newPassword">Nueva Contraseña</label>
                <input type="password" class="form-control" id="newPassword" name="newPassword" required>
            </div>
            <button type="submit" class="btn btn-primary">Cambiar Contraseña</button>
        </form>

        <% 
            // Obtener los parámetros del formulario
            String email = request.getParameter("email");
            String newPassword = request.getParameter("newPassword");

            if (email != null && newPassword != null) {
                Connection conn = null;
                PreparedStatement stmt = null;

                // Configuración de la conexión a la base de datos
                String dbUrl = "jdbc:mysql://localhost:3306/bd_unicachi";
                String dbUser = "root";
                String dbPassword = "";

                try {
                    // Establecer la conexión a la base de datos
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);

                    // Verificar si el correo electrónico existe
                    String checkEmailQuery = "SELECT * FROM usuario WHERE correo = ?";
                    stmt = conn.prepareStatement(checkEmailQuery);
                    stmt.setString(1, email);
                    ResultSet rs = stmt.executeQuery();

                    if (rs.next()) {
                        // Actualizar la contraseña
                        String updatePasswordQuery = "UPDATE usuario SET password = ? WHERE correo = ?";
                        stmt = conn.prepareStatement(updatePasswordQuery);
                        stmt.setString(1, newPassword);
                        stmt.setString(2, email);
                        int rowsAffected = stmt.executeUpdate();

                        if (rowsAffected > 0) {
                            out.println("<div class='alert alert-success'>");
                            out.println("<strong>Éxito!</strong> Tu contraseña ha sido cambiada exitosamente.");
                            out.println("</div>");
                        } else {
                            out.println("<div class='alert alert-danger'>");
                            out.println("<strong>Error!</strong> No se pudo cambiar la contraseña. Verifica tus datos e intenta nuevamente.");
                            out.println("</div>");
                        }
                    } else {
                        out.println("<div class='alert alert-danger'>");
                        out.println("<strong>Error!</strong> El correo electrónico no está registrado.");
                        out.println("</div>");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    out.println("<div class='alert alert-danger'>");
                    out.println("<strong>Error!</strong> Ocurrió un error al intentar cambiar la contraseña.");
                    out.println("</div>");
                } finally {
                    try {
                        if (stmt != null) stmt.close();
                        if (conn != null) conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        %>
    </div>
</body>
</html>

package Controlador;

import DAO.UsuarioDAO;
import Modelo.Usuario;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import jakarta.activation.CommandMap;
import jakarta.activation.MailcapCommandMap;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/ServletCorreoUnicachi")
public class ServletCorreoUnicachi extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tipo = request.getParameter("accion");

        if (tipo != null && tipo.equals("recuperar")) {
            try {
                recuperarCuenta(request, response);
            } catch (MessagingException ex) {
                Logger.getLogger(ServletCorreoUnicachi.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void enviarCorreo(String destinatario) throws MessagingException {
        // Datos de autenticación
        final String username = "mercadounicachi1@gmail.com";
        final String password = "vnwo zpdt sgvo dcco";

        // Configuración de propiedades
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Inicio de sesión de correo electrónico
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Crear un mensaje de correo electrónico
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject("Bienvenido al Mercado Unicachi");
            message.setText("Hola, bienvenido al Mercado Unicachi.\n\n" +
                "Hola, este correo te indicaremos cómo recuperar tu cuenta. No te preocupes, solo tienes que darle clic al siguiente enlace:\n\n" +
                "http://localhost:8088/mavenproject1/Interfaces/Contrase%C3%B1a.jsp\n\n" +
                "Puedes darle clic o copiarlo y pegarlo en el navegador donde has estado haciendo el trámite de recuperación de cuenta.");


            // Enviar correo electrónico
            Transport.send(message);
        } catch (MessagingException e) {
            throw new MessagingException("Error al enviar el correo electrónico: " + e.getMessage());
        }
    }
    // Método recuperarCuenta 
    private void recuperarCuenta(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, MessagingException {
        String EmailLog = request.getParameter("correo");
        System.out.println("LLEGO"+EmailLog);
       /* String EmailLog = "jessivaza10@gmail.com";*/
     
        Usuario usu = new UsuarioDAO().iniciarSesionRecuperar(EmailLog);

        if (usu == null) { System.out.println("no llego");
            request.setAttribute("errorMessage", "Usuario no encontrado.");
            request.getRequestDispatcher("Interfaces/Mensaje.jsp").forward(request, response);
        } else { System.out.println(" llego");
            HttpSession session = request.getSession();
            session.setAttribute("id", usu.getId());
            session.setAttribute("correo", usu.getCorreo());
            session.setAttribute("password", usu.getPassword());

            try {
                enviarCorreo(usu.getCorreo());
                request.setAttribute("successMessage", "Email enviado correctamente.");
                request.getRequestDispatcher("Interfaces/Mensaje.jsp").forward(request, response);
            } catch (MessagingException e) {
                request.setAttribute("errorMessage", "Error al enviar el correo electrónico: " + e.getMessage());
                request.getRequestDispatcher("Interfaces/Mensaje.jsp").forward(request, response);
            }
        }
    }
}


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>
<body>
<%
    if (session.getAttribute("success") != null) {
%>
    <script>
        Swal.fire({
            title: "Exito",
            text: "<%= session.getAttribute("success") %>",
            icon: "success"
        });
    </script>
<%
        session.removeAttribute("success");
    }
    if (session.getAttribute("error") != null) {
%>
    <script>
        Swal.fire({
            title: "Advertencia",
            text: "<%= session.getAttribute("error") %>",
            icon: "error"
        });
    </script>
<%
        session.removeAttribute("error");
    }
%>
</body>
</html>

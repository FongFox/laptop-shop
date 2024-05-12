<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <meta name="description" content="Hỏi Dân IT - Dự án laptopshop"/>
    <meta name="author" content="Hỏi Dân IT"/>
    <title>Create A User - Laptop Shop</title>
    <link href="/css/styles.css" rel="stylesheet"/>
    <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
</head>

<body class="sb-nav-fixed">
<%-- Header --%>
<jsp:include page="../layout/header.jsp"/>

<div id="layoutSidenav">
    <%-- Sidebar --%>
    <jsp:include page="../layout/sidebar.jsp"/>
    <%-- Content--%>
    <div id="layoutSidenav_content">
        <main>
            <div class="container-fluid px-4">
                <h1 class="mt-4">Manage Users</h1>
                <ol class="breadcrumb mb-4">
                    <li class="breadcrumb-item">
                        <a href="/admin">Dashboard</a>
                    </li>
                    <li class="breadcrumb-item">
                        <a href="/admin/user">Table User</a>
                    </li>
                    <li class="breadcrumb-item active">User Detail</li>
                </ol>
                <div class="mt-5">
                    <div class="row">
                        <div class="col-12 mx-auto">
                            <div class="d-flex justify-content-between">
                                <h3>User detail</h3>
                            </div>
                            <hr/>
                            <div class="card" style="width: 60%;">
                                <div class="card-header">
                                    User Information
                                </div>
                                <ul class="list-group list-group-flush">
                                    <li class="list-group-item d-none">
                                        Id: ${user.id}
                                    </li>
                                    <li class="list-group-item">
                                        Role: ${user.role.name}
                                    </li>
                                    <li class="list-group-item">
                                        Email: ${user.email}
                                    </li>
                                    <li class="list-group-item">
                                        FullName: ${user.fullName}
                                    </li>
                                    <li class="list-group-item">
                                        Phone: ${user.phone}
                                    </li>
                                    <li class="list-group-item">
                                        Address: ${user.address}
                                    </li>
                                </ul>
                            </div>
                            <div class="mt-3 mb-3">
                                <a href="/admin/user" type="button" class="btn btn-outline-success">
                                    Back
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>
        <%-- Footer --%>
        <jsp:include page="../layout/footer.jsp"/>
    </div>
</div>

<%-- JS File, CDN --%>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
        crossorigin="anonymous"></script>
<script src="/js/scripts.js"></script>
</body>

</html>
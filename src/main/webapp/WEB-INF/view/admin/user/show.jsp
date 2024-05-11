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
    <title>Manage User - Laptop Shop</title>
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
                    <li class="breadcrumb-item active">User</li>
                </ol>
                <div class="mt-5">
                    <div class="row">
                        <div class="col-12 mx-auto">
                            <div class="d-flex justify-content-between">
                                <h3>Table Users</h3>
                                <a type="button" class="btn btn-outline-primary" href="/admin/user/create">
                                    Add New User
                                </a>
                            </div>
                            <hr/>
                            <table class="table table-bordered table-hover text-center">
                                <thead>
                                <tr>
                                    <th scope="col">#</th>
                                    <th scope="col">Email</th>
                                    <th scope="col">Full Name</th>
                                    <th scope="col">Role</th>
                                    <th scope="col" colspan="3">Action</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="user" items="${users}">
                                    <tr>
                                        <td>${user.id}</td>
                                        <td>${user.email}</td>
                                        <td>${user.fullName}</td>
                                        <td>${user.role.name}</td>
                                        <td>
                                            <a href="/admin/user/detail/${user.id}"
                                               type="button" class="btn btn-outline-success">
                                                View
                                            </a>
                                        </td>
                                        <td>
                                            <a href="/admin/user/update/${user.id}" type="button"
                                               class="btn btn-outline-warning">
                                                Update
                                            </a>
                                        </td>
                                        <td>
                                            <a href="/admin/user/delete/${user.id}" type="button"
                                               class="btn btn-outline-danger">
                                                Delete
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
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
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- Latest compiled and minified CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
          rel="stylesheet">
    <!-- Latest compiled JavaScript -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>
<body>
<div class="container mt-5">
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
                    <th scope="col" colspan="3">Action</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="user" items="${users}">
                    <tr>
                        <td>${user.id}</td>
                        <td>${user.email}</td>
                        <td>${user.fullName}</td>
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
</body>
</html>
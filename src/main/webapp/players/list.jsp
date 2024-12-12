<%@ page import="java.util.List" %>
<%@ page import="org.example.examservlet.model.req.PlayerReq" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Class</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</head>
<body>
<div class="container">
    <h1>List player</h1>
        <a href="?action=new">Create a player</a>
    <table class="table">
        <thead>
        <tr>
            <th scope="col">Id</th>
            <th scope="col">Full Name</th>
            <th scope="col">Age</th>
            <th scope="col">Index name</th>
            <th scope="col">Value</th>
            <th scope="col"></th>
        </tr>
        </thead>
        <tbody>
        <% for (PlayerReq prq : (List<PlayerReq>)request.getAttribute("playerReqList")){ %>
        <tr>
            <th scope="row"><%= prq.getPlayerId() %></th>
            <td><%= prq.getFullName() %></td>
            <td><%= prq.getAge() %></td>
            <td><%= prq.getIndexName() %></td>
            <td><%= prq.getValue() %></td>
            <td></td>
            <td>
                <a href="?action=edit&playerId=<%= prq.getPlayerId() %>" class="btn btn-primary">Edit</a>
                <a href="?action=delete&playerId=<%= prq.getPlayerId()%>" class="btn btn-danger" onclick="return confirm('Are you sure you want to delete this player?');">Delete</a>
            </td>
        </tr>
        <%}%>
        </tbody>
    </table>
</div>
</body>
</html>



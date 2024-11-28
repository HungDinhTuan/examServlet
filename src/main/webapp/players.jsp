<%@ page import="org.example.examservlet.entity.Indexer" %>
<%@ page import="org.example.examservlet.entity.Player" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Indexer indexer = (Indexer) request.getAttribute("indexers");
    Player players = (Player) request.getAttribute("players");
%>
<html>
<head>
    <title>Player Management</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</head>
<body>
<div class="container my-4">
    <h1 class="text-center mb-4">Player Management</h1>

    <!-- Add Player Form -->
    <div class="card mb-4">
        <div class="card-header">
            <h5>Add New Player</h5>
        </div>
        <div class="card-body">
            <form action="players" method="post">
                <input type="hidden" name="action" value="add">
                <div class="row g-3">
                    <div class="col-md-4">
                        <label for="name" class="form-label">Player Name</label>
                        <input type="text" class="form-control" id="name" name="name" placeholder="Player Name" required>
                    </div>
                    <div class="col-md-4">
                        <label for="fullName" class="form-label">Full Name</label>
                        <input type="text" class="form-control" id="fullName" name="fullName" placeholder="Full Name" required>
                    </div>
                    <div class="col-md-4">
                        <label for="age" class="form-label">Age</label>
                        <input type="text" class="form-control" id="age" name="age" placeholder="Age" required>
                    </div>
                </div>
                <div class="row g-3 mt-3">
                    <div class="col-md-6">
                        <label for="indexId" class="form-label">Index Name</label>
                        <select class="form-select" id="indexId" name="indexId" required>
                            <option value="" selected disabled>Select Index</option>
                            <c:forEach var="indexer" items="${indexers}">
                                <option value="${indexer.indexId}">${indexer.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-6">
                        <label for="value" class="form-label">Value</label>
                        <input type="text" class="form-control" id="value" name="value" placeholder="Value" required>
                    </div>
                </div>
                <div class="mt-3">
                    <button type="submit" class="btn btn-primary">Add Player</button>
                </div>
            </form>
        </div>
    </div>

    <!-- Player List Table -->
    <div class="card">
        <div class="card-header">
            <h5>Players List</h5>
        </div>
        <div class="card-body">
            <table class="table table-bordered table-hover">
                <thead class="table-light">
                <tr>
                    <th>Id</th>
                    <th>Name</th>
                    <th>Full Name</th>
                    <th>Age</th>
                    <th>Index Name</th>
                    <th>Value</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="player" items="${players}">
                    <tr>
                        <td>${player.playerId}</td>
                        <td>${player.name}</td>
                        <td>${player.fullName}</td>
                        <td>${player.age}</td>
                        <td>${player.indexer.name}</td>
                        <td>${player.indexer.value}</td>
                        <td>
                            <div class="d-flex justify-content-center">
                                <!-- Edit Action -->
                                <a href="editPlayer.jsp?playerId=${player.playerId}" class="btn btn-sm btn-warning me-2">Edit</a>
                                <!-- Delete Action -->
                                <form action="players" method="post" style="display:inline;">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="playerId" value="${player.playerId}">
                                    <button type="submit" class="btn btn-sm btn-danger">Delete</button>
                                </form>
                            </div>
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


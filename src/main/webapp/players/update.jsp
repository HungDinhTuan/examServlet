<%@ page import="java.util.List" %>
<%@ page import="org.example.examservlet.entity.Player" %>
<%@ page import="org.example.examservlet.entity.PlayerIndex" %>
<%@ page import="org.example.examservlet.entity.Indexer" %>
<%@ page import="java.util.Objects" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    Player player = (Player) request.getAttribute("player");
    PlayerIndex playerIndex = (PlayerIndex) request.getAttribute("playerIndex");
    Indexer indexer = (Indexer) request.getAttribute("indexer");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Player</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</head>
<body>
<div class="container">
    <h1>Create a player</h1>
    <form method="post" action="?action=update">
        <div class="mb-3">
            <label class="form-label">Player Name</label>
            <input type="text" name="name" class="form-control" value="<%= player.getFullName() %>">
        </div>
        <div class="mb-3">
            <label class="form-label">Player Age</label>
            <input type="number" name="age" class="form-control" value="<%= player.getAge() %>">
        </div>
        <div class="mb-3">
            <label class="form-label">Index name</label>
            <select class="form-select" name="indexId">
                <%
                    List<Indexer> indexers = (List<Indexer>) request.getAttribute("indexers");
                    Integer selectedIndexId = playerIndex != null && playerIndex.getIndexer() != null
                            ? playerIndex.getIndexer().getIndexId()
                            : null;
                    for (Indexer i : indexers) {
                        boolean isSelected = selectedIndexId != null && Objects.equals(i.getIndexId(), selectedIndexId);
                %>
                <option value="<%= i.getIndexId() %>" <%= isSelected ? "selected" : "" %>><%= i.getName() %></option>
                <%
                    }
                %>
            </select>
        </div>

        <div class="mb-3">
            <label class="form-label">Value</label>
            <input type="number" name="value" class="form-control" value="<%= playerIndex.getValue() %>">
        </div>
        <button type="submit" class="btn btn-success">Submit</button>
    </form>
</div>
</body>
</html>



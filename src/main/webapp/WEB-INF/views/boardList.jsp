<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Board List</title>
</head>
<body>
    <h2>Board List</h2>
    <table border="1">
        <thead>
            <tr>
                <th>ID</th>
                <th>Title</th>
                <th>Writer</th>
                <th>Date</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="board" items="${boardList}">
                <tr>
                    <td>${board.id}</td>
                    <td>${board.title}</td>
                    <td>${board.writer}</td>
                    <td>${board.regDate}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Board Search Results</title>
</head>
<body>
<h2>Board Search</h2>
<form action="http://localhost:8080/boards/free/list" method="get">
    <label for="regDateStart">Registration Date Start:</label>
    <input type="date" id="regDateStart" name="regDateStart"><br><br>

    <label for="regDateEnd">Registration Date End:</label>
    <input type="date" id="regDateEnd" name="regDateEnd"><br><br>

    <label for="categoryName">Category Name:</label>
    <input type="text" id="categoryName" name="categoryName"><br><br>

    <label for="keyword">Keyword:</label>
    <input type="text" id="keyword" name="keyword"><br><br>

    <input type="submit" value="Search">
</form>

<h2>Board Search Results 폼데이터 추가 ver</h2>
<table>
    <tr>
        <th>Category</th>
        <th>Title</th>
        <th>File Count</th>
        <th>Writer</th>
        <th>Views</th>
        <th>Reg Date</th>
        <th>Update Date</th>
    </tr>
    <c:forEach var="board" items="${boards}">
        <tr>
            <td>${board.category}</td>
            <td>${board.title}</td>
            <td>${board.fileCount}</td>
            <td>${board.writer}</td>
            <td>${board.views}</td>
            <td>${board.regDateAsString}</td>
            <td>${board.updateDateAsString}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Board Search Results</title>
</head>
<body>
<h2>Board Search Results 폼데이터 추가 ver</h2>
<table border="1">
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
            <td><fmt:formatDate value="${board.regDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
            <td><fmt:formatDate value="${board.updateDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>

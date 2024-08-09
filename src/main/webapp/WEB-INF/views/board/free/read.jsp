<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Read Page</title>
</head>
<body>
<h2>Read Page</h2>

<c:if test="${not empty boardDetailWrapperDTO}">
    <h3>Board Details</h3>
    <p>Category: ${boardDetailWrapperDTO.boardDetail.category}</p>
    <p>Title: ${boardDetailWrapperDTO.boardDetail.title}</p>
    <p>Contents: ${boardDetailWrapperDTO.boardDetail.content}</p>
    <p>Reg Date: ${boardDetailWrapperDTO.boardDetail.regDate}</p>
    <p>Update Date: ${boardDetailWrapperDTO.boardDetail.updateDate}</p>

    <h3>Files</h3>
    <c:forEach var="file" items="${boardDetailWrapperDTO.files}">
        <p>File ID: ${file.filesId}</p>
        <p>Type: ${file.attachType}</p>
        <p>Size: ${file.byteSize}</p>
        <p>UUID Name: ${file.uuidName}</p>
        <p>Original Name: ${file.orgName}</p>
        <p>Directory: ${file.fileDir}</p>
    </c:forEach>

    <h3>Comments</h3>
    <c:forEach var="comment" items="${boardDetailWrapperDTO.comments}">
        <p>Reg Date: ${comment.regDate}</p>
        <p>Content: ${comment.content}</p>
    </c:forEach>

    <!-- 댓글 작성 폼 -->
    <h3>Add a Comment</h3>
    <form action="${pageContext.request.contextPath}/boards/free/views/${boardDetailWrapperDTO.boardDetail.boardId}" method="post">
    <input type="hidden" name="boardId" value="${boardDetailWrapperDTO.boardDetail.boardId}">
    
    <label for="content">Content:</label><br>
    <textarea id="content" name="content" rows="4" cols="50" required></textarea><br>
    
    <input type="submit" value="Add Comment">
    </form>
</c:if>

</body>
</html>

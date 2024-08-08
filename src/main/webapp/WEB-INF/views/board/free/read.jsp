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
    <p>Title: ${boardDetailWrapperDTO.boardDetail.boardTitle}</p>
    <p>Category: ${boardDetailWrapperDTO.boardDetail.category}</p>
    <p>Reg Date: ${boardDetailWrapperDTO.boardDetail.regDate}</p>
    <p>Update Date: ${boardDetailWrapperDTO.boardDetail.updateDate}</p>

    <h3>Comments</h3>
    <c:forEach var="comment" items="${boardDetailWrapperDTO.comments}">
        <p>Comment ID: ${comment.commentId}</p>
        <p>Content: ${comment.content}</p>
        <p>Reg Date: ${comment.regDate}</p>
    </c:forEach>

    <h3>Files</h3>
    <c:forEach var="file" items="${boardDetailWrapperDTO.files}">
        <p>File ID: ${file.filesId}</p>
        <p>Type: ${file.attachType}</p>
        <p>Size: ${file.byteSize}</p>
        <p>UUID Name: ${file.uuidName}</p>
        <p>Original Name: ${file.orgName}</p>
        <p>Directory: ${file.fileDir}</p>
    </c:forEach>
</c:if>

</body>
</html>

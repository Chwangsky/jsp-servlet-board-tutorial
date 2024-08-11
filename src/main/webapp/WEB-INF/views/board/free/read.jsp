<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Read Page</title>
    <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <script>
        const formatDateTime = (dateTimeString) => {
            console.log(dateTimeString); //2022-04-08T16:32 or 2022-04-08T16:32:33
            const dateTimeStringSplitted = dateTimeString.split(/[-T:]/);
            console.log(dateTimeStringSplitted);
            const year = dateTimeStringSplitted[0];
            const month = dateTimeStringSplitted[1];
            const day = dateTimeStringSplitted[2];
            const hours = dateTimeStringSplitted[3];
            const minutes = dateTimeStringSplitted[4];
            return `${year}-${month}-${day} ${hours}:${minutes}`;
        };

        const applyFormatting = () => {
            const regDateElement = document.getElementById('regDate');
            const updateDateElement = document.getElementById('updateDate');

            if (regDateElement && regDateElement.dataset.datetime) {
                regDateElement.textContent = formatDateTime(regDateElement.dataset.datetime);
                console.log(regDateElement.dataset.datetime);
            }

            if (updateDateElement && updateDateElement.dataset.datetime) {
                updateDateElement.textContent = formatDateTime(updateDateElement.dataset.datetime);
                console.log(updateDateElement.dataset.datetime);
            }
        };

        window.onload = applyFormatting;

        const validationCheck = () => {
            const commentContent = document.getElementById('comment-content').value.trim();
            if (commentContent.length === 0) {
                alert("댓글을 입력해주세요");
                return false;
            }
            return true;
        }
    </script>
</head>
<body>
    <div class="container mt-5">
        <h2 class="mb-4">게시판 - 보기</h2>

        <c:if test="${not empty boardDetailWrapperDto}">
            <div class="card mb-4">
                <div class="card-header">
                    <h3>${boardDetailWrapperDto.boardDetail.title}</h3>
                    <small class="text-muted">카테고리: ${boardDetailWrapperDto.boardDetail.category}</small>
                </div>
                <div class="card-body">
                    <p class="card-text">${boardDetailWrapperDto.boardDetail.content}</p>
                </div>
                <div class="card-footer text-muted">
                    <small>등록일: 
                        <span id="regDate" data-datetime="${boardDetailWrapperDto.boardDetail.regDate}"></span>
                    </small><br>
                    <small>수정일: 
                        <span id="updateDate" data-datetime="${boardDetailWrapperDto.boardDetail.updateDate}"></span>
                    </small>
                </div>
            </div>

            <c:if test="${not empty boardDetailWrapperDto.files}">
                <h4>첨부 파일</h4>
                <ul class="list-group mb-4">
                    <c:forEach var="file" items="${boardDetailWrapperDto.files}">
                        <li class="list-group-item">
                            <strong>${file.orgName}</strong> <small class="text-muted">(${file.attachType}, ${file.byteSize} bytes)</small><br>
                            <small>파일 경로: 
                                <a href="${pageContext.request.contextPath}/download.do?id=${file.filesId}">
                                    ${file.orgName}
                                </a>
                            </small>
                        </li>
                    </c:forEach>
                </ul>
            </c:if>

            <h4>댓글</h4>
            <ul class="list-group mb-4">
                <c:forEach var="comment" items="${boardDetailWrapperDto.comments}">
                    <li class="list-group-item">
                        <p>${comment.content}</p>
                        <small class="text-muted">작성일: ${comment.regDate}</small>
                    </li>
                </c:forEach>
            </ul>

            <!-- 댓글 작성 폼 -->
            <h4>댓글 작성</h4>
            <form action="${pageContext.request.contextPath}/boards/free/views/${boardDetailWrapperDto.boardDetail.boardId}" method="post" onsubmit="return validationCheck()">
                <input type="hidden" name="boardId" value="${boardDetailWrapperDto.boardDetail.boardId}">

                <div class="form-group">
                    <label for="comment-content">댓글 내용</label>
                    <textarea id="comment-content" name="content" class="form-control" rows="4" required></textarea>
                </div>

                <button type="submit" class="btn btn-primary">댓글 등록</button>
            </form>

            <!-- 목록, 수정 버튼 -->
            <div class="mb-4">
                <a href="${pageContext.request.contextPath}/boards/free/list" class="btn btn-secondary">목록</a>
                <a href="${pageContext.request.contextPath}/boards/free/modify?boardId=${boardDetailWrapperDto.boardDetail.boardId}" class="btn btn-primary">수정</a>
            </div>
            
        </c:if>
    </div>


    <!-- Bootstrap JS and dependencies -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.amazonaws.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>

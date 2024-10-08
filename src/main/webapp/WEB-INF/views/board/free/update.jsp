<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>게시글 수정</title>
    <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <script>
        // 파일 삭제를 위한 배열
        let filesToDelete = [];

        // 파일 입력 필드를 추가하는 함수
        function addFileInput() {
            const fileContainer = document.getElementById('fileContainer');
            if (fileContainer.childElementCount < 10) {
                const newInput = document.createElement('input');
                newInput.type = 'file';
                newInput.name = 'newFiles';
                newInput.className = 'file-input form-control mb-2';
                fileContainer.appendChild(newInput);
            } else {
                alert("최대 10개의 파일만 추가할 수 있습니다.");
            }
        }

        // 파일을 삭제 목록에 추가하고 화면에서 숨기는 함수
        function removeFile(fileId) {
            filesToDelete.push(fileId);
            document.getElementById('removeFiles').value = filesToDelete.join(',');
            document.getElementById('file-' + fileId).style.display = 'none';
        }

        // 폼 검증 함수
        function validateForm() {
            const title = document.getElementById('title').value.trim();
            const content = document.getElementById('content').value.trim();
            if (title === '' || content === '') {
                alert("제목과 내용을 입력해주세요.");
                return false;
            }
            return true;
        }
    </script>
</head>
<body>
    <div class="container mt-5">
        <h2 class="mb-4">게시글 수정</h2>

        <!-- 에러 메시지 출력 -->
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger">
                ${errorMessage}
            </div>
        </c:if>

        <!-- 게시글 수정 폼 -->
        <form action="${pageContext.request.contextPath}/boards/free/modify" method="post" enctype="multipart/form-data" onsubmit="return validateForm()">
            <input type="hidden" name="boardId" value="${boardUpdateDetailDto.boardId}">
            <input type="hidden" id="removeFiles" name="removeFiles" value="">

            <!-- 게시글 정보 테이블 -->
            <table class="table table-bordered">
                <tr>
                    <th>카테고리</th>
                    <td>${boardUpdateDetailDto.category}</td>
                </tr>
                <tr>
                    <th>등록 일시</th>
                    <td>${boardUpdateDetailDto.regDate}</td>
                </tr>
                <tr>
                    <th>수정 일시</th>
                    <td>${boardUpdateDetailDto.updateDate}</td>
                </tr>
                <tr>
                    <th>조회수</th>
                    <td>${boardUpdateDetailDto.views}</td>
                </tr>
                <tr>
                    <th>작성자</th>
                    <td>
                        <input type="text" id="writer" name="writer" class="form-control" value="${boardUpdateDetailDto.writer}" required>
                    </td>
                </tr>
                <tr>
                    <th>비밀번호</th>
                    <td>
                        <input type="password" id="password" name="password" class="form-control" required>
                    </td>
                </tr>
                <tr>
                    <th>제목</th>
                    <td>
                        <input type="text" id="title" name="title" class="form-control" value="${boardUpdateDetailDto.title}" required>
                    </td>
                </tr>
                <tr>
                    <th>내용</th>
                    <td>
                        <textarea id="content" name="content" class="form-control" rows="10" required>${boardUpdateDetailDto.content}</textarea>
                    </td>
                </tr>
            </table>

            <!-- 기존 첨부파일 목록 -->
            <div class="form-group">
                <label>기존 첨부파일</label>
                <ul class="list-group" id="existingFiles">
                    <c:forEach var="file" items="${fileEntities}">
                        <li class="list-group-item d-flex justify-content-between align-items-center" id="file-${file.filesId}">
                            <span>
                                ${file.orgName} (${file.byteSize} bytes)
                            </span>
                            <span>
                                <button type="button" class="btn btn-sm btn-danger" onclick="removeFile(${file.filesId})">삭제</button>
                            </span>
                        </li>
                    </c:forEach>
                </ul>
            </div>

            <!-- 새로운 첨부파일 추가 -->
            <div class="form-group">
                <label for="newFiles">새로운 파일 추가</label>
                <div id="fileContainer">
                    <input type="file" name="newFiles" class="file-input form-control mb-2">
                </div>
                <button type="button" class="btn btn-secondary mt-2" onclick="addFileInput()">파일 추가하기</button>
            </div>

            <!-- 수정 완료 버튼 -->
            <button type="submit" class="btn btn-primary">수정 완료</button>
        </form>
    </div>

    <!-- Bootstrap JS and dependencies -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>

package com.study.command;

import java.util.UUID;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import com.study.dto.BoardUpdateDetailDto;
import com.study.entity.BoardUpdateDetailEntity;
import com.study.entity.FileEntity;
import com.study.mapper.BoardUpdateMapper;
import com.study.util.MyBatisUtil;

public class ModifyCommand implements HttpCommand {

    private final static String FILE_UPLOAD_PATH = "C:/fileUpload";

    private SqlSessionFactory sqlSessionFactory;

    public ModifyCommand() {
        this.sqlSessionFactory = MyBatisUtil.getSqlSessionFactory();
    }

    @Override
    public String doService(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String method = request.getMethod();
        if ("GET".equalsIgnoreCase(method)) {
            return doGet(request, response);
        } else if ("POST".equalsIgnoreCase(method)) {
            return doPost(request, response);
        } else {
            throw new UnsupportedOperationException("Unsupported method: " + method);
        }
    }

    // 게시글 수정 폼 읽기
    public String doGet(HttpServletRequest request, HttpServletResponse response) throws Exception {

        int boardId = Integer.parseInt(request.getParameter("boardId"));

        try (SqlSession session = sqlSessionFactory.openSession()) {

            BoardUpdateMapper mapper = session.getMapper(BoardUpdateMapper.class);
            BoardUpdateDetailEntity boardUpdateDetailEntity = mapper.selectBoardDetailById(boardId);

            BoardUpdateDetailDto boardUpdateDetailDto =
                    new BoardUpdateDetailDto(boardUpdateDetailEntity);

            List<FileEntity> fileEntities = mapper.selectFilesByBoardId(boardId);

            request.setAttribute("boardUpdateDetailDto", boardUpdateDetailDto);
            request.setAttribute("fileEntities", fileEntities);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("ReadCommand 처리 도중 예외가 발생했습니다.", e);
        }

        // 게시글 수정 폼 로직
        return "dispatch:update.jsp";
    }

    private String doPost(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        int boardId = Integer.parseInt(request.getParameter("boardId"));
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String password = request.getParameter("password");
        String writer = request.getParameter("writer"); // writer를 포함하도록 수정

        String storedPassword = null;

        try (SqlSession session = sqlSessionFactory.openSession()) {

            BoardUpdateMapper mapper = session.getMapper(BoardUpdateMapper.class);
            BoardUpdateDetailEntity boardUpdateDetailEntity = mapper.selectBoardDetailById(boardId);
            storedPassword = boardUpdateDetailEntity.getPassword();

            // 비밀번호가 일치하지 않으면 오류 메시지를 추가하고 다시 수정 페이지로 리다이렉트
            if (!storedPassword.equals(password)) {
                System.out.println("비밀번호 불일치 로직");
                request.setAttribute("errorMessage", "비밀번호가 일치하지 않습니다.");
                return doGet(request, response);
            }

            mapper.updateBoard(boardId, title, content, writer);

            // 파일 삭제 처리
            String[] filesToDelete = request.getParameterValues("removeFiles");
            if (filesToDelete != null) {
                for (String fileId : filesToDelete) {
                    if (!fileId.trim().isEmpty()) { // 공백이나 빈 문자열 무시
                        try {
                            int parsedFileId = Integer.parseInt(fileId.trim());
                            mapper.deleteFileById(parsedFileId);
                        } catch (NumberFormatException e) {
                            // 로깅 또는 적절한 에러 처리
                            e.printStackTrace();
                        }
                    }
                }
            }

            // 새로운 파일 업로드 처리
            Collection<Part> parts = request.getParts();

            String uploadPath = FILE_UPLOAD_PATH;
            Files.createDirectories(Paths.get(uploadPath)); // 업로드 디렉토리가 없는 경우 생성

            for (Part part : parts) {
                if (part.getName().equals("newFiles") && part.getSize() > 0) {
                    String originalFileName = part.getSubmittedFileName();
                    String uuid = UUID.randomUUID().toString();
                    String fileName = uuid + "_" + originalFileName;
                    String filePath = uploadPath + "/" + fileName;

                    // 파일 저장
                    part.write(filePath);

                    // 새로운 파일 정보를 DB에 저장
                    FileEntity fileEntity = new FileEntity();
                    fileEntity.setBoardId(boardId);
                    fileEntity.setAttachType(Files.probeContentType(Paths.get(filePath)));
                    fileEntity.setByteSize((int) part.getSize());
                    fileEntity.setUuidName(fileName);
                    fileEntity.setOrgName(originalFileName);
                    fileEntity.setFileDir(filePath);

                    mapper.insertFile(fileEntity);
                }
            }

            session.commit();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("게시물 수정 중 오류가 발생했습니다.", e);
        }

        // 수정 후 게시물 보기 페이지로 리다이렉트
        return "redirect:/boards/free/views/" + boardId;
    }

}

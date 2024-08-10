package com.study.command;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import com.study.mapper.BoardWriteMapper;
import com.study.util.MyBatisUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class WriteCommand implements HttpCommand {
    private SqlSessionFactory sqlSessionFactory;

    public WriteCommand() {
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

    // 게시글 등록 폼 읽기
    public String doGet(HttpServletRequest request, HttpServletResponse response) throws Exception {


        try (SqlSession session = sqlSessionFactory.openSession()) {
            BoardWriteMapper mapper = session.getMapper(BoardWriteMapper.class);

            List<String> categories = mapper.getAllCategory();

            request.setAttribute("categories", categories);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("ReadCommand 처리 도중 예외가 발생했습니다.", e);
        }

        // 게시글 작성 로직
        return "dispatch:write.jsp";
    }

    // 댓글 등록하기
    private String doPost(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        // 파라미터 받아오기
        // String boardIdString = request.getParameter("boardId");
        // String content = request.getParameter("content");

        try (SqlSession session = sqlSessionFactory.openSession()) {

            // int boardId = Integer.parseInt(boardIdString);

            // BoardReadMapper mapper = session.getMapper(BoardReadMapper.class);
            // mapper.insertComment(boardId, content);

            // 커밋
            // session.commit();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("댓글 작성 중 오류가 발생했습니다.", e);
        }

        // 다시 게시글 페이지로 리다이렉트
        return "redirect:/boards/free/list";
    }
}


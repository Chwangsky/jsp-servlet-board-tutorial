package com.study.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import com.study.entity.BoardSearchEntity;
import com.study.mapper.BoardSearchMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/boardSearch")
public class BoardSearchServlet extends HttpServlet {
    private SqlSessionFactory sqlSessionFactory;

    @Override
    public void init() throws ServletException {
        String resource = "mybatis-config.xml";
        try (Reader reader = Resources.getResourceAsReader(resource);
                BufferedReader bufferedReader = new BufferedReader(reader)) {

            // MyBatis SqlSessionFactory 생성
            try (Reader configReader = Resources.getResourceAsReader(resource)) {
                sqlSessionFactory = new SqlSessionFactoryBuilder().build(configReader);
            }

            System.out.println("MyBatis SqlSessionFactory 생성 완료.");
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServletException("Unable to load MyBatis configuration file", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String regDateStart = request.getParameter("regDateStart");
        String regDateEnd = request.getParameter("regDateEnd");
        String categoryName = request.getParameter("categoryName");
        String titleAndContentKeyword = request.getParameter("keyword");
        System.out.printf("변수 테스트: %s %s %s %s\n", regDateStart, regDateEnd, categoryName,
                titleAndContentKeyword);

        int limit = 5;
        int offset = 0;

        try (SqlSession session = sqlSessionFactory.openSession()) {
            BoardSearchMapper mapper = session.getMapper(BoardSearchMapper.class);
            List<BoardSearchEntity> boards = mapper.boardSearch(regDateStart, regDateEnd,
                    categoryName, titleAndContentKeyword, limit, offset);

            // FOR DEBUGGING
            System.out.println(boards.get(0).getRegDate());


            request.setAttribute("boards", boards);
            request.getRequestDispatcher("/WEB-INF/views/boardSearchResult.jsp").forward(request,
                    response);
        }
    }
}

package com.study.command;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import com.study.entity.BoardSearchEntity;
import com.study.mapper.BoardSearchMapper;
import com.study.util.MyBatisUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ListCommand implements HttpCommand {

    private SqlSessionFactory sqlSessionFactory;

    public ListCommand() {
        this.sqlSessionFactory = MyBatisUtil.getSqlSessionFactory();
    }

    @Override
    public String doService(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

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

            request.setAttribute("boards", boards);
        }

        return "dispatch:list.jsp";
    }
}


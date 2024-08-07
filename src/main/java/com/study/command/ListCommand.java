package com.study.command;

import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import com.study.entity.BoardSearchEntity;
import com.study.mapper.BoardSearchMapper;
import com.study.util.MyBatisUtil;
import com.study.dto.BoardListDto;
import com.study.dto.resultset.BoardSearchResultSet;

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
        String keyword = request.getParameter("keyword");
        String currentPageString = request.getParameter("page");

        System.out.printf("변수 테스트: %s %s %s %s %s\n", regDateStart, regDateEnd, categoryName,
                keyword, currentPageString);

        int boardsPerPage = 5; // 한 페이지당 보여주는 값

        if (regDateStart != null && !isStringDateType(regDateStart))
            regDateStart = null;
        if (regDateEnd != null && !isStringDateType(regDateEnd))
            regDateEnd = null;
        if (categoryName != null && categoryName.isEmpty())
            categoryName = null;
        if (keyword != null && keyword.isEmpty())
            keyword = null;

        int offset = 0;
        int currentPage = 1; // 기본 페이지의 값
        if (isStringInteger(currentPageString)) {
            currentPage = Integer.parseInt(currentPageString);
            offset = (currentPage - 1) * boardsPerPage;
        }

        try (SqlSession session = sqlSessionFactory.openSession()) {
            BoardSearchMapper mapper = session.getMapper(BoardSearchMapper.class);

            List<BoardSearchEntity> boards = mapper.boardSearch(regDateStart, regDateEnd,
                    categoryName, keyword, boardsPerPage, offset);
            int totalCount =
                    mapper.boardSearchCount(regDateStart, regDateEnd, categoryName, keyword);

            int totalPage = (int) Math.ceil((double) totalCount / boardsPerPage);
            int sectionPageBegin =
                    ((int) Math.ceil((double) (offset / boardsPerPage) / 10) * 10) + 1;
            int sectionPageEnd = Math.min(sectionPageBegin + 9, totalPage);

            List<BoardSearchResultSet> boardListResultSet = boards.stream()
                    .map(board -> new BoardSearchResultSet(board.getCategory(),
                            board.getFileCount(), board.getTitle(), board.getWriter(),
                            board.getViews(), board.getRegDate(), board.getUpdateDate()))
                    .toList();

            BoardListDto boardListDto = new BoardListDto(totalCount, boardListResultSet,
                    currentPage, totalPage, sectionPageBegin, sectionPageEnd);

            request.setAttribute("boardListDto", boardListDto);
        }

        return "dispatch:list.jsp";
    }

    /**
     * 주어진 스트링이 date값과 직접 비교할 수 있는 값 (1999-09-09)인지 확인한다.
     * 
     * @param 입력 스트링
     * @return 입력 스트링이 날짜형식 해당 여부
     *
     */
    private boolean isStringDateType(String input) {
        String regex = "^\\d{4}-\\d{2}-\\d{2}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    private boolean isStringInteger(String s) {
        if (s == null || s.isEmpty()) {
            return false;
        }
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

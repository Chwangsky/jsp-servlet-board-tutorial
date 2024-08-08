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
import com.study.dto.PaginationDto;
import com.study.dto.resultset.BoardSearchResultSet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ListCommand implements HttpCommand {

    private SqlSessionFactory sqlSessionFactory;
    private static final int ITEMS_PER_PAGE = 5; // 한 페이지당 보여주는 게시물의 개수
    private static final int PAGE_PER_SECTION = 10;

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
            offset = (currentPage - 1) * ITEMS_PER_PAGE;
        }

        try (SqlSession session = sqlSessionFactory.openSession()) {
            BoardSearchMapper mapper = session.getMapper(BoardSearchMapper.class);

            List<BoardSearchEntity> boards = mapper.boardSearch(regDateStart, regDateEnd,
                    categoryName, keyword, ITEMS_PER_PAGE, offset);
            int totalCount =
                    mapper.boardSearchCount(regDateStart, regDateEnd, categoryName, keyword);

            List<BoardSearchResultSet> boardListResultSet = boards.stream()
                    .map(board -> new BoardSearchResultSet(board.getBoardId(), board.getCategory(),
                            board.getFileCount(), board.getTitle(), board.getWriter(),
                            board.getViews(), board.getRegDate(), board.getUpdateDate()))
                    .toList();

            PaginationDto paginationDto = PaginationDto.createPaginationDto(totalCount,
                    ITEMS_PER_PAGE, PAGE_PER_SECTION, currentPage);

            BoardListDto boardListDto =
                    new BoardListDto(totalCount, boardListResultSet, paginationDto);

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

    /**
     * 주어진 스트링이 Integer에 해당하는지 여부부
     * 
     * @param 입력 스트링
     * @return 입력 Integer 해당 여부
     *
     */
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

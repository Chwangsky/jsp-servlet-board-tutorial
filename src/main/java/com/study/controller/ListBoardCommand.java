package com.study.controller;

import java.util.List;

import com.study.model.Board;
import com.study.service.BoardService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ListBoardCommand implements Command {

    private BoardService boardService;

    public ListBoardCommand() {
        this.boardService = new BoardService(); // 실제로는 DI를 사용할 수도 있습니다.
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Board> boardList = boardService.getAllBoards();
        request.setAttribute("boardList", boardList);
        return "/WEB-INF/views/boardList.jsp"; // jsp 파일 위치:
                                               // C:\ebrainstudy-template-week-1\src\main\webapp\WEB-INF\views\boardList.jsp
    }
}

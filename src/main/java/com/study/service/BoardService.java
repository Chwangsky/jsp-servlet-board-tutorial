package com.study.service;

import java.util.ArrayList;
import java.util.List;

import com.study.model.Board;

public class BoardService {

    public List<Board> getAllBoards() {
        // 여기서는 임시로 하드코딩된 데이터를 반환합니다.
        // 실제로는 데이터베이스에서 데이터를 가져와야 합니다.
        List<Board> boardList = new ArrayList<>();
        boardList.add(new Board() {
            {
                setId(1);
                setTitle("First Post");
                setWriter("John");
                setRegDate("2024-08-01");
            }
        });
        boardList.add(new Board() {
            {
                setId(2);
                setTitle("Second Post");
                setWriter("Jane");
                setRegDate("2024-08-02");
            }
        });
        return boardList;
    }
}
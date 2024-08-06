package com.study.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Map;
import java.util.HashMap;
import java.io.IOException;

@WebServlet("/controller")
public class ControllerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Map<String, Command> commands = new HashMap<>();

    public ControllerServlet() {
        commands.put("listBoards", new ListBoardCommand());
        // 추가 명령 객체들을 여기에 등록
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String commandName = request.getParameter("command");
        Command command = commands.get(commandName);
        System.out.printf("command : %s\n", commandName);

        if (command != null) {
            try {
                String view = command.execute(request, response);
                request.getRequestDispatcher(view).forward(request, response);
            } catch (Exception e) {
                throw new ServletException("Command execution failed", e);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Command not found");
        }
    }
}

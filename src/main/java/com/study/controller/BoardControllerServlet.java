package com.study.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.study.service.HttpService;
import com.study.service.ListService;
import com.study.service.WriteService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/board/free/*")
public class BoardControllerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    Map<String, HttpService> commandMap = new HashMap<>();

    @Override
    public void init() throws ServletException {
        super.init();
        commandMap.put("GET:list", new ListService());
        commandMap.put("GET:read", new WriteService());
        commandMap.put("GET:insert", new WriteService());
        commandMap.put("POST:insert", new WriteService());
        commandMap.put("GET:update", new WriteService());
        commandMap.put("PUT:update", new WriteService());
        commandMap.put("DELETE:delete", new WriteService());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("service method called");
        HttpService targetService = findTargetService(request);
        // String view = targetService.doService(request, response);

        // TODO: view 문자열 분석해서 dispatch 혹은 redirect
    }

    private HttpService findTargetService(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        String queryString = request.getQueryString();
        System.out.println("Request URI: " + requestUri);
        System.out.println("Query String: " + queryString);
        System.out.println("version:0.0.0.3");
        return null;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        service(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        service(request, response);
    }
}

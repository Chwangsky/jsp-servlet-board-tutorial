package com.study.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface HttpService {
    String doService(HttpServletRequest request, HttpServletResponse response) throws Exception;
}

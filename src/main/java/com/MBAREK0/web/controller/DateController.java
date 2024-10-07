package com.MBAREK0.web.controller;

import com.MBAREK0.web.util.DateUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class DateController extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String jsonInput = request.getReader().lines().collect(Collectors.joining());

        JsonObject jsonObject = new JsonParser().parse(jsonInput).getAsJsonObject();
        String dateString = jsonObject.get("date").getAsString();

        LocalDate specifiedDate = LocalDate.parse(dateString);

        List<String> disabledDates = DateUtil.getDisabledDates(specifiedDate);

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        new Gson().toJson(disabledDates, out);
        out.flush();
    }
}

package com.project.LibraryLocator.client;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/myservlet")
public class MyServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LibraryLocator myClass = new LibraryLocator();

        if (request.getParameter("button1") != null) {
            myClass.createDialogBox();
      
        } else {
            // 
        }

        request.getRequestDispatcher("/WEB-INF/some-result.jsp").forward(request, response);
    }

}
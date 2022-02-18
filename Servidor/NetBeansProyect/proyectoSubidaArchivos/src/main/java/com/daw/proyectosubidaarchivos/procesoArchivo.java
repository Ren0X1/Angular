/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.daw.proyectosubidaarchivos;

import java.io.*;
import java.util.Collection;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author jaxft
 */
@MultipartConfig(location="C:/subidaArchivos")
public class procesoArchivo extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        Collection<Part> elementos = request.getParts();
        for(Part p : elementos) {
            p.getName();
            p.getSubmittedFileName();
            p.getContentType();
            p.getInputStream();
                    
            String contenidoHeader = p.getHeader("content-disposition");
            String[] cabeceras = contenidoHeader.split(";");
            for(String s : cabeceras) {
                if(s.trim().startsWith("filename")) {
                    int pos = s.indexOf('=');
                    String fileName = s.substring(pos+1);
                    p.write(fileName.replace("\"", ""));
                }
            }
        }
        request.getParameter("nombre");
        RequestDispatcher rd=getServletContext().getRequestDispatcher("/indice.jsp");
        rd.forward(request,response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        
        
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
}

package com.newlecture.web.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.newlecture.web.entity.Notice;

@WebServlet("/notice/detail")
public class NoticeDetailController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		String url = "jdbc:mysql://localhost/newlecture";
		String sql = "select * from notice where id = ?";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url,"root","mysql");
			PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1, id);
			ResultSet rs = st.executeQuery();
			
			rs.next();
			
			String title = rs.getString("title");
			Date regDate = rs.getDate("regdate");
			String writerId = rs.getString("writer_id");
			int hit = rs.getInt("hit");
			String files = rs.getString("files");
			String content = rs.getString("content");
			
			Notice notice = new Notice(
								id,
								title,
								regDate,
								writerId,
								hit,
								files,
								content);
			
			request.setAttribute("n", notice);
			
			/*
			request.setAttribute("title", title);
			request.setAttribute("regDate", regDate);
			request.setAttribute("writerId", writerId);
			request.setAttribute("hit", hit);
			request.setAttribute("files", files);
			request.setAttribute("content", content);
			*/
			rs.close();
			st.close();
			con.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//redirect
		
		
		
		//forward
		request
		.getRequestDispatcher("/notice/detail.jsp")
		.forward(request, response);
		
	}
}

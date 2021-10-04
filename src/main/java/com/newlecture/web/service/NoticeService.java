package com.newlecture.web.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.newlecture.web.entity.Notice;

public class NoticeService {
	public List<Notice> getNoticeList() {

		return getNoticeList("title", "", 1);
	}

	public List<Notice> getNoticeList(int page) {

		return getNoticeList("title", "", page);
	}

	public List<Notice> getNoticeList(String field, String query, int page) {
		
//		String sql = "select * from ("
//					+ "	select @rownum := @rownum+1 as num,"
//					+ "	N1.* FROM "
//					+ "	(select * from notice order by regdate desc) N1,"
//					+ "	(select @rownum := 0) r "
//					+ ") N2 "
//					+ "where num between 6 and 10";

		List<Notice> list = new ArrayList<>();
		
		String sql = "select * from("
					+ "		select row_number() over (order by regdate desc, id desc) num,"
					+ "		notice.* from notice where " + field + " like ? "
					+ "	) N "
					+ "where num between ? and ?";
		
		// 1, 11, 21, 31 -> an = 1+(page-1)*10
		// 10, 20, 30, 40 -> page*10
		
		String url = "jdbc:mysql://localhost/newlecture";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url,"root","mysql");
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, "%"+query+"%");
			st.setInt(2, 1+(page-1)*10);
			st.setInt(3, page*10);
			ResultSet rs = st.executeQuery();

			while(rs.next()){
				int id = rs.getInt("id");
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
				list.add(notice);
			}
			
			
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
		
		return list;
	}

	public int getNoticeCount() {

		return getNoticeCount("title", "");
	}

	public int getNoticeCount(String field, String query) {
		
		int count = 0;
		
		String sql = "select count(id) as count from("
				+ "		select row_number() over (order by regdate desc, id desc) num,"
				+ "		notice.* from notice where " + field + " like ? "
				+ "	) N ";
		
		String url = "jdbc:mysql://localhost/newlecture";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url,"root","mysql");
			PreparedStatement st = con.prepareStatement(sql);
			
			st.setString(1, "%"+query+"%");
			
			ResultSet rs = st.executeQuery();
			
			if(rs.next())
				count = rs.getInt("count");
			
			count = rs.getInt("count");
			
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
		return count;
	}

	public Notice getNotice(int id) {
		
		Notice notice = null;
		
		String sql = "select * from notice where id = ?";
		
		String url = "jdbc:mysql://localhost/newlecture";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url,"root","mysql");
			PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1, id);
			ResultSet rs = st.executeQuery();

			if(rs.next()){
				int nid = rs.getInt("id");
				String title = rs.getString("title");
				Date regDate = rs.getDate("regdate");
				String writerId = rs.getString("writer_id");
				int hit = rs.getInt("hit");
				String files = rs.getString("files");
				String content = rs.getString("content");
				
				notice = new Notice(
						nid,
						title,
						regDate,
						writerId,
						hit,
						files,
						content);
			}
			
			
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
		
		return notice;
	}

	public Notice getNextNotice(int id) {
		
		Notice notice = null;
		
		String sql = "select * from notice"
					+ " where id = ("
					+ "		select id from notice "
					+ "		where regdate > (select regdate from notice where id = ?)"
					+ "		limit 1"
					+ ")";
		
		String url = "jdbc:mysql://localhost/newlecture";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url,"root","mysql");
			PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1, id);
			ResultSet rs = st.executeQuery();

			if(rs.next()){
				int nid = rs.getInt("id");
				String title = rs.getString("title");
				Date regDate = rs.getDate("regdate");
				String writerId = rs.getString("writer_id");
				int hit = rs.getInt("hit");
				String files = rs.getString("files");
				String content = rs.getString("content");
				
				notice = new Notice(
						nid,
						title,
						regDate,
						writerId,
						hit,
						files,
						content);
			}
			
			
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
		
		return notice;
	}

	public Notice getPrevNotice(int id) {
		
		Notice notice = null;
		
		String sql = "select * from notice"
				+ " where id = ("
				+ "		select id from notice "
				+ "		where regdate < (select regdate from notice where id = ?)"
				+ "		order by id limit 1"
				+ ")";
		
		String url = "jdbc:mysql://localhost/newlecture";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url,"root","mysql");
			PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1, id);
			ResultSet rs = st.executeQuery();

			if(rs.next()){
				int nid = rs.getInt("id");
				String title = rs.getString("title");
				Date regDate = rs.getDate("regdate");
				String writerId = rs.getString("writer_id");
				int hit = rs.getInt("hit");
				String files = rs.getString("files");
				String content = rs.getString("content");
				
				notice = new Notice(
						nid,
						title,
						regDate,
						writerId,
						hit,
						files,
						content);
			}
			
			
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
		
		return notice;
	}
}

package com.newlecture.web.service;

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
//					+ ") N2"
//					+ "where num between 6 and 10";

		String sql = "select * from("
					+ "		select row_number() over (order by regdate desc) num,"
					+ "		notice.* from notice"
					+ "	) N "
					+ "where num between 6 and 10";
		
		return null;
	}

	public int getNoticeCount() {

		return getNoticeCount("title", "");
	}

	public int getNoticeCount(String field, String query) {

		return 0;
	}

	public Notice getNotice(int id) {

		return null;
	}

	public Notice getNextNotice(int id) {

		return null;
	}

	public Notice getPrevNotice(int id) {

		return null;
	}
}

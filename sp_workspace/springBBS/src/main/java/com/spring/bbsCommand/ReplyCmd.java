package com.spring.bbsCommand;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.spring.bbsDAO.BDAO;

public class ReplyCmd implements Bcmd {

	@Override
	public void service(Model model) {

		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest)map.get("request");
		//replyForm으로부터 받아오는 것들
		String bNo = request.getParameter("bNo");
		String bName = request.getParameter("bName");
		String bSubject = request.getParameter("bSubject");
		String bContent = request.getParameter("bContent");
		//답글용(hidden으로 + bNo도)
		String bGroup = request.getParameter("bGroup");
		String bStep = request.getParameter("bStep");
		String bIndent = request.getParameter("bIndent");
		
		BDAO bDAO = new BDAO();
		bDAO.replyOk(bNo, bName, bSubject, bContent, bGroup, bStep, bIndent);
		
	}

}

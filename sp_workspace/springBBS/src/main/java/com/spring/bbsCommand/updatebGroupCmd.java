package com.spring.bbsCommand;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.spring.bbsDAO.BDAO;

public class updatebGroupCmd implements Bcmd {

	@Override
	public void service(Model model) {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		String bNo = request.getParameter("bNo");
//		String bGroup = request.getParameter("bGroup");
		
		BDAO bDAO = new BDAO();
		bDAO.updatebGroup(bNo);
	}

}

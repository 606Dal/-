package com.spring.bbsCommand;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.spring.bbsDAO.BDAO;

public class DeleteCmd implements Bcmd {

	@Override
	public void service(Model model) {

		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		//뷰에서 삭제로 이동할 때 경로에 지정한 bNo
		String bNo = request.getParameter("bNo");
		BDAO bDAO = new BDAO();
		bDAO.delete(bNo);
		
	}

}

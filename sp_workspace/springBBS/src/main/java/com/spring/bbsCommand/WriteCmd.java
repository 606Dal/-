package com.spring.bbsCommand;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.spring.bbsDAO.BDAO;

public class WriteCmd implements Bcmd {

	@Override
	public void service(Model model) {
		Map<String, Object> map = model.asMap(); //map 형태로 변경
		//컨트롤러에서 모델에 담은 request를 get
		HttpServletRequest request = (HttpServletRequest)map.get("request");

		//writeForm에서 입력해준 값들
		String bName = request.getParameter("bName");
		String bSubject = request.getParameter("bSubject");
		String bContent = request.getParameter("bContent");
		
		BDAO dao = new BDAO();
		dao.write(bName, bSubject, bContent);
		
	}

}

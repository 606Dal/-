package com.spring.bbsCommand;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.spring.bbsDAO.BDAO;

//글 수정 처리
public class ModifyCmd implements Bcmd {

	@Override
	public void service(Model model) {

		Map<String, Object> map = model.asMap();
		//컨트롤러에서 request객체로 모델에 담은 걸 얻어옴
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		//작성자, 제목, 내용만 수정 가능
		String bNo = request.getParameter("bNo"); //글 번호를 알아야 함.
		String bName = request.getParameter("bName");
		String bSubject = request.getParameter("bSubject");
		String bContent = request.getParameter("bContent");
		
		BDAO bDAO = new BDAO();
		bDAO.modify(bNo, bName, bSubject, bContent);
		
	}

}

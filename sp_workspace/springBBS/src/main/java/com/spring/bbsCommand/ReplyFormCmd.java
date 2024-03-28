package com.spring.bbsCommand;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.spring.bbsDAO.BDAO;
import com.spring.bbsVO.BVO;

public class ReplyFormCmd implements Bcmd {

	@Override
	public void service(Model model) {
		
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		String bNo = request.getParameter("bNo");
		
		BDAO bDAO = new BDAO();
		BVO bVO = bDAO.replyForm(bNo); //bNo 로 글을 가져와서 리턴은 BVO
		
		model.addAttribute("replyForm", bVO);
	}

}

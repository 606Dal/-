package com.spring.bbsController;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.spring.bbsCommand.Bcmd;
import com.spring.bbsCommand.ContentCmd;
import com.spring.bbsCommand.DeleteCmd;
import com.spring.bbsCommand.ListCmd;
import com.spring.bbsCommand.ModifyCmd;
import com.spring.bbsCommand.ReplyCmd;
import com.spring.bbsCommand.ReplyFormCmd;
import com.spring.bbsCommand.WriteCmd;
import com.spring.bbsCommand.updatebGroupCmd;
import com.spring.bbsVO.BVO;

@Controller
public class BController {
	Bcmd cmd = null;
	
	//게시판 목록 부분
	@RequestMapping("/list")
	public String list(Model model) {
		System.out.println("-------list() 호출-------");
		cmd = new ListCmd();
		cmd.service(model); //뷰에 모델에 담은 데이터를 전달
		
		return "list"; //list.jsp
	}
	
	//글쓰기 부분
	@RequestMapping("/writeForm")//list.jsp에서 경로 writeForm으로 함
	public String writeForm(Model model) {
		System.out.println("-------writeForm() 호출-------");
		
		return "writeForm";
	}
	
	//글 등록 성공
	@RequestMapping("/writeOk")
	public String writeOk(HttpServletRequest request, Model model) {
		System.out.println("-------writeOk() 호출-------");
		
		model.addAttribute("request", request);
		cmd = new WriteCmd();
		cmd.service(model);
		
//		 return "redirect:list"; 
		 return "redirect:updatebGroup"; 
	}
	
	//그룹 값이랑 넘버값이랑 맞추기
	@RequestMapping("/updatebGroup")
	public String updatebGroup(HttpServletRequest request, Model model) {
		System.out.println("-------updatebGroup() 호출-------");
		
		model.addAttribute("request", request);
		cmd = new updatebGroupCmd();
		cmd.service(model);
		
		return "redirect:list";
	}
	
	//글 상세 내용 보기
	@RequestMapping("/contentView")
	public String contentView(HttpServletRequest request, Model model) {
		System.out.println("-------contentView() 호출-------");
		
		model.addAttribute("request", request);
		cmd = new ContentCmd();
		cmd.service(model);
		
		return "contentView";
	}
	
	//글 수정하기
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String modify(HttpServletRequest request, Model model) {
		System.out.println("-------modify() 호출-------");
		
		model.addAttribute("request", request);
		cmd = new ModifyCmd();
		cmd.service(model);
		
		return "redirect:list";
	}
	
	//글 삭제하기
	@RequestMapping("/delete")
	public String delete(HttpServletRequest request, Model model) {
		System.out.println("-------delete() 호출-------");
		
		model.addAttribute("request", request);
		cmd = new DeleteCmd();
		cmd.service(model);
		
		return "redirect:list";
	}
	
	//답변 폼
	@RequestMapping("/replyForm")
	public String replyForm(HttpServletRequest request, Model model) {
		System.out.println("-------replyForm() 호출-------");
		
		model.addAttribute("request", request);
		cmd = new ReplyFormCmd();
		cmd.service(model);
		
		return "replyForm";
	}
	
	//답변 등록시 이동
	@RequestMapping("replyOk")
	public String replyOk(HttpServletRequest request, Model model) {
		System.out.println("-------replyOk() 호출-------");
		
		//답글 정보들 request로 받아오기
		model.addAttribute("request", request);
		cmd = new ReplyCmd();
		cmd.service(model);
		
		return "redirect:list";
	}
	
	
	@ModelAttribute("BVO") //뷰에서 쓰기 위해 모델에 등록
	public BVO formBacking() {
		return new BVO();
	}
	
}

package recipe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import recipe.model.BoardDTO;
import recipe.model.ReplyBoardDTO;
import recipe.service.FreeBoardService;
import recipe.service.FreeReplyBoardService;




@Controller
public class FreeReplyBoardController {
	@Autowired
	private FreeReplyBoardService rbs;
	@Autowired
	private FreeBoardService bs;

	@RequestMapping("/free_slist")
	public String slist(int comm_num, Model model) throws Exception {
		BoardDTO board = bs.board_cont(comm_num);
		List<ReplyBoardDTO> slist = rbs.slist(comm_num);

		model.addAttribute("slist", slist);
		model.addAttribute("board", board);
		return "fboard/slist";
	}

	@RequestMapping("/free_sInsert")
	public String sInsert(ReplyBoardDTO reboard, Model model) throws Exception {
		rbs.insert(reboard);

		return "redirect:free_slist?comm_num=" + reboard.getComm_num();
	}

	@RequestMapping("/free_repDelete")
	public String delete(ReplyBoardDTO reboard, Model model) throws Exception {	
		rbs.delete(reboard.getCommre_num());
		System.out.println("commre_num : "+reboard.getCommre_num());
		return "redirect:free_slist?comm_num=" + reboard.getComm_num();
	}
	
	@RequestMapping("/free_repUpdate")
	public String repUpdate(ReplyBoardDTO reboard, Model model) {
		rbs.update(reboard);
		System.out.println(reboard.getCommre_num());
		System.out.println(reboard.getRe_content());
		System.out.println(reboard.getComm_num());
		
		
		return "redirect:free_slist?comm_num=" + reboard.getComm_num();
	}
}
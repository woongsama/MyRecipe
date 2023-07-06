// 메인 컨트롤러
package recipe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import recipe.model.BoardDTO;
import recipe.model.MagaBean;
import recipe.model.RecipeBoard;
import recipe.service.FreeBoardService;
import recipe.service.MainService;

@Controller
public class MainController {
	
	@Autowired
	private MainService service;
	@Autowired
	private FreeBoardService BoardService;
	
	@RequestMapping("main")
	public String testMain() {
		return "main";
	}
// 추천순 회전목마	
	@RequestMapping("main_carousel")
	public String main_carousel(Model model) {
		
		System.out.println("main_carousel 진입");
		
		List<RecipeBoard> rlist = service.main_list();
		System.out.println(rlist);
		
		model.addAttribute("rlist", rlist);
		
		return "main_module/carousel_main";
	}
	
// 자유게시판	
	@RequestMapping("main_free_board")
	public String main_free_board(Model model) throws Exception {
			
		System.out.println("main_free_board 진입");
		
		BoardDTO fboard = new BoardDTO();
		fboard.setStartRow(1);
		fboard.setEndRow(10);
		
		
		List<BoardDTO> boardlist = BoardService.getBoardList(fboard);
		
		System.out.println("boardlist는 " + boardlist);
			
		model.addAttribute("num", 1);
		model.addAttribute("boardlist", boardlist);
			
		return "main_module/free_board_main";
	}	
	
	
// 최신순 회전목마		
	@RequestMapping("main_new_recipe")
	public String main_new_recipe(Model model) {
			
		System.out.println("main_new_recipe 진입");
			
		List<RecipeBoard> boardlist = service.new_rlist();
		System.out.println("boardlist는 " + boardlist);
			
		model.addAttribute("boardlist", boardlist);
			
		return "main_module/new_recipe_main";
		}	
	
	
// 메거진
	@RequestMapping("main_magazine")
	public String main_magazine(Model model) {
		
		System.out.println("main_magazine 진입");
		
		List<MagaBean> mlist = service.main_magalist();
		System.out.println(mlist);
		
		model.addAttribute("mlist", mlist);
		//수정
		return "main_module/magazine_main";
	}
	
	
}

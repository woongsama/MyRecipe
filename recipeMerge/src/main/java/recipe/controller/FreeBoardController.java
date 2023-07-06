// 메인 컨트롤러
package recipe.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import recipe.model.BoardDTO;
import recipe.model.MagaBean;
import recipe.service.FreeBoardService;


@Controller
public class FreeBoardController {
	@Autowired
	private FreeBoardService BoardService;
	
	
	@RequestMapping("/list")
	public String board() {
		return "redirect:/board_list";
	}

	
	// 글 쓰기
	@RequestMapping("/free_board_write")
	public String free_board_write() {
		return "fboard/board_write";
	}


	// 글 목록
	@RequestMapping("/board_list")
	public String board_list(HttpServletRequest request, HttpSession session, BoardDTO board, Model model) throws Exception {
		
		int page = 1;
		int limit = 10;	// 화면에 출력할 데이터 갯수
		
		if (request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		int startRow = (page - 1) * limit + 1;
		int endRow = page * limit;
		
		board.setStartRow(startRow);
		board.setEndRow(endRow);

		// 데이터 개수
		int listcount = BoardService.getListCount(board);		
		
		List<BoardDTO> boardlist = new ArrayList<BoardDTO>();		
		
		int maxpage = listcount / limit + ((listcount % limit == 0) ? 0 : 1);		

		int startPage = ((page-1)/10) * limit +1;
		int endPage = startPage + 10 -1;
		
		// 목록 구하기
		boardlist = BoardService.getBoardList(board);

		if (endPage > maxpage)
			endPage = maxpage;

		model.addAttribute("page", page);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("maxpage", maxpage);
		model.addAttribute("listcount", listcount);
		model.addAttribute("boardlist", boardlist);
		
		model.addAttribute("search",board.getSearch());
		model.addAttribute("keyword",board.getKeyword());

		return "fboard/board_list";
	}
	
	// 상세 페이지 출력
	@RequestMapping("/free_board_cont")
	public String free_board_cont(@RequestParam("comm_num") int comm_num,
			 				@RequestParam("page") String page,
			 				@RequestParam("state") String state, Model model) throws Exception {
		
		if(state.equals("cont")) {
			BoardService.readcount(comm_num);
		}
		
		BoardDTO board = BoardService.board_cont(comm_num);
		
		model.addAttribute("board",board);
		model.addAttribute("page",page);
		
		return "fboard/board_cont";
	}
	
	// 글 작성
	@RequestMapping("/board_write_ok2")
	public String board_write_ok(@RequestParam("commfile2") MultipartFile mf,
								HttpServletRequest request,HttpSession session, BoardDTO board) throws Exception {
		String id = (String)session.getAttribute("id");
		
		board.setId(id);
		System.out.println("글작성 진입");
		String filename = mf.getOriginalFilename();
		int size = (int) mf.getSize();
		String path = request.getRealPath("upload");
		System.out.println(path);
		String newfilename = "";
		String file[] = new String[2];
		int result=0;
		
		if(filename != "") {
			String extension = filename.substring(filename.lastIndexOf("."), filename.length());
			UUID uuid = UUID.randomUUID();
			newfilename = uuid.toString() + extension;	
		}
		
		if(size > 0) {
			mf.transferTo(new File(path + "/" + newfilename));
			board.setCommfile(newfilename);
		}
		
		BoardService.insert(board);
		
		return "redirect:/board_list";
	}
	
	// 글 삭제
	@RequestMapping("/board_del_ok")
	public String board_del_ok(HttpServletRequest request, int comm_num, String page) throws Exception {
		
		BoardDTO board = BoardService.board_cont(comm_num);
		
		String path = request.getRealPath("upload");
		String fname = board.getCommfile();
		
		if (fname != null) {		// 첨부파일이 있으면
			File file = new File(path +"/"+fname);
			file.delete();			// 첨부파일 삭제
		}
		
		int result = BoardService.board_del(comm_num);
		System.out.println(result);
		
		return "redirect:/board_list?page="+page;
	}
	// 글 수정
	@RequestMapping("board_up_form")
	public String boardupdateform(int comm_num, String page, Model model) throws Exception {
		
		BoardDTO board = BoardService.board_cont(comm_num);		// 상세 정보 구하기
		model.addAttribute("board", board);
		model.addAttribute("page", page);
		
		
		return "fboard/board_edit";
	}
	
	@RequestMapping("board_up_ok2")
	public String board_up_ok(@RequestParam("commfile2") MultipartFile mf,@ModelAttribute BoardDTO b,HttpServletRequest request, int comm_num, String page,Model model) throws Exception {
		
		BoardDTO board = BoardService.board_cont(comm_num);
		
		System.out.println("수정");
		
		
		if (mf.isEmpty()) {
			String cfile = board.getCommfile();
			b.setCommfile(cfile);
			
		} else {
			String path = request.getRealPath("upload");
			String fname = board.getCommfile();
			File file = new File(path + "/" + fname);
			file.delete(); // 첨부파일 삭제
			
			String filename = mf.getOriginalFilename();
			int size = (int) mf.getSize();
			String newfilename = "";

			if (filename != "") {
				String extension = filename.substring(filename.lastIndexOf("."), filename.length());
				UUID uuid = UUID.randomUUID();
				newfilename = uuid.toString() + extension;
			}

			if (size > 0) {
				mf.transferTo(new File(path + "/" + newfilename));
				b.setCommfile(newfilename);
			}
		}

	
		BoardService.board_up(b);
		
		
		return "redirect:/free_board_cont?comm_num=" + b.getComm_num()
		+ "&page=" + page + "&state=cont";
	}
	
	
	

}

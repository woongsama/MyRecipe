package recipe.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.SimpleFormatter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import recipe.model.MemberBean;
import recipe.model.R_recomm;
import recipe.model.R_zzim;
import recipe.model.RecipeBoard;
import recipe.service.PagingPgm;
import recipe.service.RecipeService;

@Controller
public class RecipeController {

	@Autowired
	private RecipeService service;
	HttpSession session;

	// 카테고리 폼
	@RequestMapping("categoryForm")
	public String category(Model model, HttpServletRequest request) {

		// 임시 세션값 설정
		return "recipe/categoryForm";

	}

// 레시피 목록 폼		
	@RequestMapping("r_listForm")
	public String Recipelist(String pageNum, RecipeBoard board, Model model, R_recomm recomm) {

		System.out.println("r_list");

		final int rowPerPage = 12;
		if (pageNum == null || pageNum.equals("")) {
			pageNum = "1";
		}
		if (board.getSort() == null || board.getSort().equals("")) {
			board.setSort("rnum");
		}
		int currentPage = Integer.parseInt(pageNum);
		int total = service.getTotal(board); // 검색
		System.out.println("total:" + total);

		System.out.println(total);
		System.out.println("rowPerPage" + rowPerPage);
		System.out.println("currentPage" + currentPage);

		int startRow = (currentPage - 1) * rowPerPage + 1;
		int endRow = startRow + rowPerPage - 1;
		PagingPgm pp = new PagingPgm(total, rowPerPage, currentPage);
		System.out.println("startRow" + startRow);
		System.out.println("endRow" + endRow);
		
		board.setSort(board.getSort());
		board.setStartRow(startRow);
		board.setEndRow(endRow);
		int number = total - startRow + 1;

		List<RecipeBoard> list = service.r_list(board);
		System.out.println("list:" + list);

		System.out.println(startRow + "+" + endRow);

		model.addAttribute("total", total);
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("list", list);
		model.addAttribute("number", number);
		model.addAttribute("pp", pp);
		model.addAttribute("category", board.getCategory());

		// 검색
		model.addAttribute("search", board.getSearch());
		model.addAttribute("keyword", board.getKeyword());

		return "recipe/r_list";
	}

// 레시피 작성폼
	@RequestMapping("r_insertForm")
	public String insertform(String pageNum, Model model) {

		model.addAttribute("pageNum", pageNum);

		return "recipe/r_insertForm";
	}

// 레시피 작성	
	@RequestMapping(value = "r_insert", method = RequestMethod.POST)
	public String insert(@RequestParam("thumbnail1") MultipartFile mf1,
//						@RequestParam("r_file1") MultipartFile mf2,
			MultipartHttpServletRequest mhr, RecipeBoard board, HttpServletRequest request, Model model)
			throws Exception {

		System.out.println("insert 진입");
		SimpleDateFormat sf = new SimpleDateFormat("YYYY_MM_dd_");
		Date dt = new Date();

		// 세션확인
		HttpSession session = request.getSession();
		String sessionVal = (String) session.getAttribute("id");
		System.out.println("id:"+sessionVal);
		board.setId(sessionVal);
		
		// 조리사진 내용
		String[] contentList = request.getParameterValues("content1");
		String content = "";

		for (String i : contentList) {
			content += i + "-"; // 내용1-내용2
		}

		System.out.println("contentList:" + contentList);
		System.out.println("content:" + content);

		board.setContent(content);

		// 썸네일사진
		String thumbFileName = mf1.getOriginalFilename();
		int thumbFileSize = (int) mf1.getSize();
		String thumbpath = request.getRealPath("t_images");
		String thumbNewFileName = "";

		if (thumbFileName != "") {
			String extension = thumbFileName.substring(thumbFileName.lastIndexOf("."), thumbFileName.length());
			UUID uuid = UUID.randomUUID();
			thumbNewFileName = sf.format(dt) + uuid.toString() + extension;

			// 전달확인
			System.out.println("thumbFileName=" + thumbFileName); // filename="Koala.jpg"
			System.out.println("thumbFileSize=" + thumbFileSize);
			System.out.println("thumbPath=" + thumbpath);
		}

		mf1.transferTo(new File(thumbpath + "/" + thumbNewFileName));

		System.out.println("thumbNewFileName:" + thumbNewFileName);
		board.setThumbnail(thumbNewFileName);

		/*
		 * // 조리사진 String filename2 = mf2.getOriginalFilename(); int filesize2 = (int)
		 * mf2.getSize(); String newfilename2 = "";
		 * 
		 * // 전달확인 System.out.println("mf2=" + mf2); System.out.println("filename2=" +
		 * filename2); // filename="Koala.jpg" System.out.println("filesize2=" +
		 * filesize2); System.out.println("Path=" + path);
		 * 
		 * if(filename2!="") { String extension =
		 * filename2.substring(filename2.lastIndexOf("."), filename2.length()); UUID
		 * uuid = UUID.randomUUID(); newfilename2 = uuid.toString() + extension; }
		 * 
		 * mf2.transferTo(new File(path + "/" + newfilename2));
		 * 
		 * board.setRfile(newfilename2);
		 * 
		 */

		// 멀티 업로드
		List<MultipartFile> fileList = mhr.getFiles("r_file1");
		String multipath = request.getRealPath("r_images");
		String finalFileName = "";

		System.out.println("path:" + multipath);

		for (MultipartFile mf : fileList) {
			String multiFileName = mf.getOriginalFilename(); // 원본 파일 명
			long multiFileSize = mf.getSize(); // 파일 사이즈

			String extension = multiFileName.substring(multiFileName.lastIndexOf("."), multiFileName.length());
			UUID uuid = UUID.randomUUID();

			String multiNewFileName = sf.format(dt) + uuid.toString() + extension;

			System.out.println("multiFileName : " + multiFileName);
			System.out.println("multiNewFileName : " + multiNewFileName);
			System.out.println("multiFileSize : " + multiFileSize);

			finalFileName += multiNewFileName + "]";

			try {
				mf.transferTo(new File(multipath + "/" + multiNewFileName));
			} catch (Exception e) {

				e.printStackTrace();
			}
		}

		System.out.println("finalFileName:" + finalFileName);
		board.setRfile(finalFileName);

		// ip
		String ip = request.getRemoteAddr();
		board.setIp(ip);
		System.out.println(ip);

		// 재료, 용량 '-' 접합자 추가해서 저장
		String ingre = "";
		String[] ingreEx = request.getParameterValues("ingre");

		for (String i : ingreEx) {
			ingre += i + "-"; // 재료1-재료2
		}

		String capacity = "";
		String[] capacityEx = request.getParameterValues("capacity");

		for (String i : capacityEx) {
			capacity += i + "-"; // 용량1-용량2
		}

		System.out.println("재료:" + ingre);
		System.out.println("용량:" + capacity);

		board.setIngre(ingre);
		board.setCapacity(capacity);

		int result = service.r_insert(board);

		model.addAttribute("category", board.getCategory());
		model.addAttribute("result", result);

		return "result/r_insertResult";
	}

// 레시피 상세페이지 
	@RequestMapping("r_view")
	public String rview(int rnum, String rpageNum, String pageNum, Model model, R_recomm recomm) {

		if (rpageNum == null || rpageNum.equals("")) {
			rpageNum = "1";
		}
		if (pageNum == null || pageNum.equals("")) {
			pageNum = "1";
		}

		service.r_readcountUpdate(rnum);
		int recomm_state = service.r_recomm_count(recomm);

		RecipeBoard board = service.r_select(rnum);

		String ingre[] = (board.getIngre()).split("-");
		String capacity[] = (board.getCapacity()).split("-");
		String rfile[] = (board.getRfile()).split("]");
		String content[] = (board.getContent()).split("-");

		for (int i = 0; i < ingre.length; i++) {
			System.out.println(ingre[i]);
		}
		LinkedHashMap<String, String> map1 = new LinkedHashMap<>();
		LinkedHashMap<String, String> map2 = new LinkedHashMap<>();

		for (int i = 0; i < ingre.length; i++) {
			map1.put(ingre[i], capacity[i]);
		}
		System.out.println("ingre값 출력: " + map1);
		for (int i = 0; i < content.length; i++) {
			map2.put(rfile[i], content[i]);
		}
		
		MemberBean member = service.m_select(board.getId());
		
		model.addAttribute("recomm_state", recomm_state);
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("rpageNum", rpageNum);
		model.addAttribute("board", board);
		model.addAttribute("member", member);
		model.addAttribute("map1", map1);
		model.addAttribute("map2", map2);

		return "recipe/r_view";
	}

	// 상세페이지 - 추천
	@RequestMapping("r_recomm")
	public String r_recomm(R_recomm recomm) {

		System.out.println("r_recomm 진입");

		int result;
		String id = recomm.getId();
		int rnum = recomm.getRnum();

		System.out.println(id);
		System.out.println(rnum);

		result = service.r_recomm(recomm);

		System.out.println(result);

		if (result == 0) {
			System.out.println("추천누른 아이디가 존재하지않음.");
			return "redirect:r_recomm_add?id=" + id + "&rnum=" + rnum;
		} else {
			System.out.println("추천누른 아이디가 존재함.");
			return "redirect:r_recomm_remove?id=" + id + "&rnum=" + rnum;
		}
	}

	// 상세페이지 - 추천 증가
	@RequestMapping("r_recomm_add")
	public String r_recomm_add(R_recomm recomm, RecipeBoard board, Model model) {

		System.out.println("r_recomm_add 진입");

		service.r_recomm_add(recomm);
		int result = service.r_recomm_count(recomm); // 추천갯수
		int number = service.r_recomm_plus(board.getRnum());

		System.out.println("추천갯수:" + result);
		model.addAttribute("result", result);

		return "result/r_recomm_result";
	}

	// 상세페이지 - 추천 감소
	@RequestMapping("r_recomm_remove")
	public String r_recomm_remove(R_recomm recomm, RecipeBoard board, Model model) {

		System.out.println("r_recomm_remove 진입");

		service.r_recomm_delete(recomm);
		service.r_recomm_minus(board.getRnum());
		int result = service.r_recomm_count(recomm);

		System.out.println("추천갯수:" + result);
		model.addAttribute("result", result);

		return "result/r_recomm_result";
	}
	
	// 찜 확인
	@RequestMapping("r_zzim")
	public String r_zzim(R_zzim zzim, Model model) {
		
		System.out.println("r_zzim 진입");
		
		String id = zzim.getId();
		int  rnum = zzim.getRnum();
		
		System.out.println(id);
		System.out.println(rnum);
		
		int result = service.r_zzim(zzim);
		
		System.out.println("result는 "+result);
		
		model.addAttribute("id", id);
		model.addAttribute("rnum", rnum);
		model.addAttribute("result", result);
		
		return "result/r_zzim_result";
		
	}
	
// 찜하기
	@RequestMapping("r_zzim_add")
	public String r_zzim_add(R_zzim zzim, Model model) {
		
		System.out.println("r_zzim_add 진입");
		
		int rnum = zzim.getRnum();
		int result = service.r_zzim_add(zzim);
		System.out.println("zzim_insert문 실행결과:"+result);
		
		model.addAttribute("rnum", rnum);
		model.addAttribute("result", result);
		
		return "result/r_zzim_result_add";
	}
	
// 찜취소
	@RequestMapping("r_zzim_remove")
	public String r_zzim_remove(R_zzim zzim, Model model) {
		
		System.out.println("r_zzim_remove 진입");
		
		int rnum = zzim.getRnum();
		int result = service.r_zzim_remove(zzim);
		
		System.out.println("zzim_delete문 실행결과:"+result);
		
		model.addAttribute("renum", rnum);
		model.addAttribute("result", result);
		
		return "result/r_zzim_result_delete";
	}

// 레시피 수정폼	
	@RequestMapping("r_updateForm")
	public String rupdate(String pageNum, int rnum, Model model) {

		RecipeBoard board = service.r_select(rnum);

		String ingre[] = (board.getIngre()).split("-");
		String capacity[] = (board.getCapacity()).split("-");
		String rfile[] = (board.getRfile()).split("]");
		String content[] = (board.getContent()).split("-");

		LinkedHashMap<String, String> map1 = new LinkedHashMap<>();
		LinkedHashMap<String, String> map2 = new LinkedHashMap<>();

		for (int i = 0; i < ingre.length; i++) {
			map1.put(ingre[i], capacity[i]);
		}
		for (int i = 0; i < content.length; i++) {
			map2.put(rfile[i], content[i]);
		}

		model.addAttribute("pageNum", pageNum);
		model.addAttribute("board", board);
		model.addAttribute("map1", map1);
		model.addAttribute("map2", map2);

		return "recipe/r_updateForm";
	}

//	수정
	@RequestMapping("r_update")
	public String r_update(@RequestParam("thumbnail1") MultipartFile mf1, MultipartHttpServletRequest mhr,
			RecipeBoard board, HttpServletRequest request, Model model) throws Exception {

		System.out.println("update 진입");

// 조리사진 내용
		String[] contentList = request.getParameterValues("content1");
		String content = "";

		for (String i : contentList) {
			content += i + "-"; // 내용1-내용2
		}

		System.out.println("contentList:" + contentList);
		System.out.println("content:" + content);

		board.setContent(content);

		// 썸네일사진
		String thumbFileName = mf1.getOriginalFilename();
		int thumbFileSize = (int) mf1.getSize();
		String thumbpath = request.getRealPath("t_images");
		String thumbNewFileName = "";
		
		SimpleDateFormat sf = new SimpleDateFormat("YYYY_MM_dd_");
		Date dt = new Date();

		RecipeBoard image = service.r_select(board.getRnum());

		if (thumbFileName != "") {
			String extension = thumbFileName.substring(thumbFileName.lastIndexOf("."), thumbFileName.length());
			UUID uuid = UUID.randomUUID();
			thumbNewFileName = sf.format(dt) + uuid.toString() + extension;

			// 전달확인
			System.out.println("thumbFileName=" + thumbFileName); // filename="Koala.jpg"
			System.out.println("thumbFileSize=" + thumbFileSize);
			System.out.println("thumbPath=" + thumbpath);
		

		mf1.transferTo(new File(thumbpath + "/" + thumbNewFileName));

		
		System.out.println("thumbNewFileName:" + thumbNewFileName);
		board.setThumbnail(thumbNewFileName);
		}else{
			board.setThumbnail(image.getThumbnail());
		}

		/*
		 * // 조리사진 String filename2 = mf2.getOriginalFilename(); int filesize2 = (int)
		 * mf2.getSize(); String newfilename2 = "";
		 * 
		 * // 전달확인 System.out.println("mf2=" + mf2); System.out.println("filename2=" +
		 * filename2); // filename="Koala.jpg" System.out.println("filesize2=" +
		 * filesize2); System.out.println("Path=" + path);
		 * 
		 * if(filename2!="") { String extension =
		 * filename2.substring(filename2.lastIndexOf("."), filename2.length()); UUID
		 * uuid = UUID.randomUUID(); newfilename2 = uuid.toString() + extension; }
		 * 
		 * mf2.transferTo(new File(path + "/" + newfilename2));
		 * 
		 * board.setRfile(newfilename2);
		 * 
		 */

		// 멀티 업로드
		List<MultipartFile> fileList = mhr.getFiles("r_file1");
		String multipath = request.getRealPath("r_images");
		String finalFileName = "";

		System.out.println("path:" + multipath);

		// if(fileList.size() > 0 && !fileList.get(0).getOriginalFilename().equals("")) {
		for (MultipartFile mf : fileList) {
			String multiFileName = mf.getOriginalFilename(); // 원본 파일 명
			long multiFileSize = mf.getSize(); // 파일 사이즈

			String extension = multiFileName.substring(multiFileName.lastIndexOf("."), multiFileName.length());
			UUID uuid = UUID.randomUUID();

			String multiNewFileName = sf.format(dt) + uuid.toString() + extension;

			System.out.println("multiFileName : " + multiFileName);
			System.out.println("multiNewFileName : " + multiNewFileName);
			System.out.println("multiFileSize : " + multiFileSize);

			finalFileName += multiNewFileName + "]";

			try {
				mf.transferTo(new File(multipath + "/" + multiNewFileName));
			} catch (Exception e) {

				e.printStackTrace();
			}
		}

		System.out.println("finalFileName:" + finalFileName);
		board.setRfile(finalFileName);
		// }else {
		// board.setRfile(image.getRfile());
		// }

		// 재료, 용량 '-' 접합자 추가해서 저장
		String ingre = "";
		String[] ingreEx = request.getParameterValues("ingre");

		for (String i : ingreEx) {
			ingre += i + "-"; // 재료1-재료2
		}

		String capacity = "";
		String[] capacityEx = request.getParameterValues("capacity");

		for (String i : capacityEx) {
			capacity += i + "-"; // 용량1-용량2
		}

		System.out.println("재료:" + ingre);
		System.out.println("용량:" + capacity);

		board.setIngre(ingre);
		board.setCapacity(capacity);

		int result = service.r_update(board);

		model.addAttribute("category", board.getCategory());
		model.addAttribute("result", result);

		return "result/r_updateResult";
	}

// 삭제
	@RequestMapping("r_delete")
	public String r_delete(RecipeBoard board, Model model, HttpServletResponse response) throws IOException {

		System.out.println("r_delete 진입");

		int result = service.r_delete(board.getRnum());

		model.addAttribute("category", board.getCategory());
		model.addAttribute("result", result);

		PrintWriter out = response.getWriter();
		out.print(result);

		return null;
	}

}

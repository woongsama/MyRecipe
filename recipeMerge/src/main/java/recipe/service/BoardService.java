package recipe.service;

import java.util.List;
import recipe.model.BoardBean;

public interface BoardService {

	public void insert(BoardBean board) throws Exception;
	
	public void hit(int board_num) throws Exception;

	public BoardBean board_cont(int board_num) throws Exception;

	public void edit(BoardBean b) throws Exception;

	public void del_ok(int board_num) throws Exception;

	public void reply_ok(BoardBean b) throws Exception;
	
	public int getListCount() throws Exception;
	
	public List<BoardBean> getBoardList(int page) throws Exception;

}

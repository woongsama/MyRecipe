package recipe.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import recipe.dao.FreeBoardDAOImpl;
import recipe.model.BoardDTO;

@Service
public class FreeBoardServiceImpl implements FreeBoardService {

	@Autowired
	private FreeBoardDAOImpl BoardDAO;

	// 데이터 갯수
	@Override
	public int getListCount(BoardDTO board) throws Exception {
		return BoardDAO.getListCount(board);

	}

	public List getBoardList(BoardDTO board) throws Exception {
		return BoardDAO.getBoardList(board);

	}

	public void insert(BoardDTO board) throws Exception {
		BoardDAO.insert(board);

	}

	public void readcount(int comm_num) throws Exception {
		BoardDAO.readcount(comm_num);

	}

	public BoardDTO board_cont(int comm_num) throws Exception {
		return BoardDAO.board_cont(comm_num);

	}

	public int board_del(int comm_num) throws Exception {
		return BoardDAO.board_del(comm_num);

	}

	@Override
	public void board_up(BoardDTO b) {
		BoardDAO.board_up(b);
	}

}

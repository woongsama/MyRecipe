package recipe.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import recipe.dao.FreeReplyBoardDAO;
import recipe.model.ReplyBoardDTO;


@Service
public class FreeReplyBoardServiceImpl implements FreeReplyBoardService {

	@Autowired
	private FreeReplyBoardDAO rbd;

	public List<ReplyBoardDTO> slist(int comm_num) {

		return rbd.slist(comm_num);
	}

	public void insert(ReplyBoardDTO reboard) throws Exception {
		rbd.insert(reboard);
	}

	@Override
	public void delete(int commre_num) throws Exception {
		rbd.delete(commre_num);
	}

	@Override
	public void update(ReplyBoardDTO reboard) {
		rbd.update(reboard);
	}
}

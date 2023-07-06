package recipe.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import recipe.model.MemberBean;
import recipe.model.ReplyMagaBean;

@Repository
public class MemberDAOImpl {

	@Autowired
	private SqlSession sqlSession;

	// ID 중복 체크
	public int checkMemberId(String id) throws Exception {
		int re = -1; // 사용가능한 id
		MemberBean mem = sqlSession.selectOne("login_check", id);
		if (mem != null)
			re = 1; // 중복 id
		return re;
	}

	// 닉네임 중복 체크
	public int checkMemberNickname(String nickname) throws Exception {
		int re = -1; // 사용가능한 닉네임
		MemberBean mem = sqlSession.selectOne("nickname_check", nickname);
		if (mem != null)
			re = 1; // 중복 닉네임
		return re;
	}

	// ID 찾기
	public MemberBean findid(MemberBean im) throws Exception {
		return sqlSession.selectOne("id_find", im);
	}
	
	// 비번 찾기
	public MemberBean findpwd(MemberBean pm) throws Exception {
		return sqlSession.selectOne("pwd_find", pm);
	}

	// 회원 저장(가입)
	public int insertMember(MemberBean m) throws Exception {
		return sqlSession.insert("member_join", m);
	}

	// 로그인 인증 체크
	public MemberBean userCheck(String id) throws Exception {
		return sqlSession.selectOne("login_check", id);
	}
    // 비밀번호 변경
	public int updateNewpwd(MemberBean member) {
		return sqlSession.update("change_pwd", member);
	}
	/* 회원수정 */
//	@Transactional
	public void updateMember(MemberBean member) throws Exception {
		sqlSession.update("member_edit", member);
	}

	/* 회원삭제 */
//	@Transactional
	public void deleteMember(MemberBean delm) throws Exception {
		sqlSession.update("member_delete", delm);
	}
	
	//관리자용 회원목록	
	public List<MemberBean> getMemberList(MemberBean member) {
	    return sqlSession.selectList("memberns.memberlist", member);
	}

	public int getMemberCount() {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("getcount");
	}


}

package recipe.dao;

import java.util.List;

import recipe.model.MemberBean;
import recipe.model.R_recomm;
import recipe.model.R_zzim;
import recipe.model.RecipeBoard;

public interface RecipeDao {

// 글작성
	int r_insert(RecipeBoard board);

// 리스트
	int getTotal(RecipeBoard board);

	List<RecipeBoard> r_list(RecipeBoard board);

// 글조회
	void r_readcountUpdate(int rnum);

	RecipeBoard r_select(int rnum);

// 글수정
	int r_update(RecipeBoard board);

// 글삭제
	int r_delete(int rnum);
	
// 추천
	int r_recomm(R_recomm recomm);

// 추천 증가
	void r_recomm_add(R_recomm recomm);

// 추천 갯수
	int r_recomm_count(R_recomm recomm);

// 추천 감소
	void r_recomm_delete(R_recomm recomm);

// 추천수 증가 상태
	int r_recomm_plus(int rnum);
	
// 추천수 감소 상태
	void r_recomm_minus(int rnum);

	MemberBean m_select(String id);

// 찜 상태
	int r_zzim_add(R_zzim zzim);

// 찜 추가
	int r_zzim(R_zzim zzim);

// 찜 취소
	int r_zzim_remove(R_zzim zzim);
// 찜 번호검색
	List<R_zzim> r_zzim_search(String id);


}

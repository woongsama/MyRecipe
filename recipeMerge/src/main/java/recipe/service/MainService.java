package recipe.service;

import java.util.List;

import recipe.model.MagaBean;
import recipe.model.RecipeBoard;

public interface MainService {
	
	List<RecipeBoard> main_list();

	List<MagaBean> main_magalist();

	List<RecipeBoard> new_rlist();

}

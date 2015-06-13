package utility;
import android.widget.TextView;
import model.Paper;
/**
 * Created by zmhbh on 6/9/15.
 */
public class PaperListViewTag {
    TextView paperTitle, paperAuthor, paperSimplifiedAffl;
    Paper paperTag;
    public PaperListViewTag(TextView paperTitle, TextView paperAuthor, TextView paperSimplifiedAffl, Paper paperTag){
        this.paperTitle=paperTitle;
        this.paperAuthor=paperAuthor;
        this.paperSimplifiedAffl=paperSimplifiedAffl;
        this.paperTag=paperTag;
    }
    public Paper getPaperTag(){
        return paperTag;
    }

}

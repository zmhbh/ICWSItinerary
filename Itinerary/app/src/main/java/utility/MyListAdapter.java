package utility;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import model.Paper;
import android.content.Context;
import icws.itinerary.R;

/**
 * Created by zmhbh on 6/9/15.
 */
public class MyListAdapter extends BaseAdapter {

    private LayoutInflater adapterLayoutInflater;
    private ArrayList<Paper> paperArrayList;

    public MyListAdapter(Context c){
    adapterLayoutInflater=LayoutInflater.from(c);
    paperArrayList = new ArrayList<Paper>();
    }

    public void addItem(int position, Paper addedPaper){
        paperArrayList.add(position,addedPaper);
        this.notifyDataSetChanged();
    }

    public void removeAllItem(){
        paperArrayList.clear();
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return paperArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PaperListViewTag paperTag;
        if(convertView==null){
            convertView=adapterLayoutInflater.inflate(R.layout.listview_fourthlevel_sessionpaper,null);
            paperTag= new PaperListViewTag(
                    (TextView) convertView.findViewById(R.id.textView4_paperTitle),
                    (TextView) convertView.findViewById(R.id.textView4_paperAuthor),
                    (TextView) convertView.findViewById(R.id.textView4_paperSimpifiedAffl));
            convertView.setTag(paperTag);

        }
        else{
            paperTag=(PaperListViewTag)convertView.getTag();
        }

        paperTag.paperAuthor.setText(paperArrayList.get(position).getAuthor());
        paperTag.paperTitle.setText(paperArrayList.get(position).getTitle());
        paperTag.paperSimplifiedAffl.setText(paperArrayList.get(position).getSimplifiedAffiliation());
        return convertView;
    }


}

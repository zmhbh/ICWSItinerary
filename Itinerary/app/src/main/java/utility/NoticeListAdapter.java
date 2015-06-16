package utility;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.Collections;

import icws.itinerary.R;
import model.notice.Notification;
import android.content.Context;
import android.widget.TextView;
/**
 * Created by zmhbh on 6/16/15.
 */
public class NoticeListAdapter extends BaseAdapter {
   private LayoutInflater layoutInflater;
    private ArrayList<Notification> notificationArrayList;

    public NoticeListAdapter(Context c){
        layoutInflater = LayoutInflater.from(c);
        notificationArrayList = new ArrayList<Notification>();
    }

    public void addNotification(int position, Notification notification){
        notificationArrayList.add(position, notification);
        this.notifyDataSetChanged();
    }

    public void removeAllNotification(){
        notificationArrayList.clear();
        this.notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return notificationArrayList.size();
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

        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.notification_list, null);
        }
        TextView textViewNotice= (TextView)convertView.findViewById(R.id.textView_adapterNotice);
        TextView textViewTime= (TextView)convertView.findViewById(R.id.textView_adapterTime);
        textViewNotice.setText(notificationArrayList.get(position).getNotice());
        textViewTime.setText(notificationArrayList.get(position).getTime());

        return convertView;
    }
}

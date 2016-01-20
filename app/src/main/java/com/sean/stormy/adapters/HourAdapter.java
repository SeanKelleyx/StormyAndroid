package com.sean.stormy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sean.stormy.R;
import com.sean.stormy.weather.Hour;

/*
 * Created by snkelley on 5/26/2015.
 */
public class HourAdapter extends BaseAdapter{
    private Context mContext;
    private Hour[] mHours;

    public HourAdapter(Context context, Hour[] hours){
        mContext = context;
        mHours = hours;
    }

    @Override
    public int getCount() {
        return mHours.length;
    }

    @Override
    public Object getItem(int position) {
        return mHours[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;// we are not going to use this
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.hourly_list_item, null);
            holder = new ViewHolder();
            holder.mTimeLabel = (TextView) convertView.findViewById(R.id.timeLabel);
            holder.mDayLabel = (TextView) convertView.findViewById(R.id.dayLabel);
            holder.mSummaryLabel = (TextView) convertView.findViewById(R.id.summaryLabel);
            holder.mTemperatureLabel = (TextView) convertView.findViewById(R.id.temperatureLabel);
            holder.mIconImageView = (ImageView) convertView.findViewById(R.id.iconImageView);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        Hour hour = mHours[position];

        holder.mTimeLabel.setText(hour.getHour());
        holder.mDayLabel.setText(hour.getDayAbbr());
        holder.mSummaryLabel.setText(hour.getSummary());
        holder.mTemperatureLabel.setText(hour.getTemperature() + "" );
        holder.mIconImageView.setImageResource(hour.getIconId());

        return convertView;
    }

    private static class ViewHolder {
        public TextView mTimeLabel;
        public TextView mDayLabel;
        public TextView mSummaryLabel;
        public TextView mTemperatureLabel;
        public ImageView mIconImageView;
    }

}

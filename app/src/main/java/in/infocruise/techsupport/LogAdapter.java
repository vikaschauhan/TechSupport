package in.infocruise.techsupport;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.ArrayList;

import in.infocruise.techsupport.Model.Call_Log;
import in.infocruise.techsupport.Model.TicketDetail;

public class LogAdapter extends RecyclerView.Adapter<LogAdapter.LogViewHolder> {
    private Context mContext;
    private ArrayList<Call_Log> mCallLogDetail;
    private AdapterView.OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    //getting the context and product list with constructor
    public LogAdapter(Context context, ArrayList<Call_Log> call_logs) {
        mContext = context;
        mCallLogDetail = call_logs;
    }

    @Override
    public LogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        View v = LayoutInflater.from(mContext).inflate(R.layout.ticket_example, parent, false);
        return new LogViewHolder(v);
    }

    @Override
    public void onBindViewHolder(LogViewHolder holder, int position) {
        //getting the product of the specified position
        Call_Log currentItem = mCallLogDetail.get(position);

        String call_number = currentItem.getPhone_no();
        String call_type = currentItem.getCall_type();
        String call_time = currentItem.getDate_time();

        //binding the data with the viewholder views

        holder.textViewTitle.setText(call_number);

        holder.textViewShortDesc.setText("Call Type: " + call_type);
        holder.textViewPrice.setText("Call Time : " + call_time);



    }

    @Override
    public int getItemCount() {
        return mCallLogDetail.size();
    }

    class LogViewHolder extends RecyclerView.ViewHolder{
        TextView textViewTitle, textViewShortDesc, textViewPrice;
        public LogViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewShortDesc = itemView.findViewById(R.id.textViewShortDesc);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
        }
    }
}

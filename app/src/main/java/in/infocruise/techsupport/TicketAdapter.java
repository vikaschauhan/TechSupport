package in.infocruise.techsupport;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.ArrayList;

import in.infocruise.techsupport.Model.TicketDetail;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.TicketViewHolder> {
    private Context mContext;
    private ArrayList<TicketDetail> mTicketDetail;
    private AdapterView.OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    //getting the context and product list with constructor
    public TicketAdapter(Context context, ArrayList<TicketDetail> ticketDetail) {
        mContext = context;
        mTicketDetail = ticketDetail;
    }

    @Override
    public TicketViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        View v = LayoutInflater.from(mContext).inflate(R.layout.ticket_example, parent, false);
        return new TicketViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TicketViewHolder holder, int position) {
        //getting the product of the specified position
        TicketDetail currentItem = mTicketDetail.get(position);

        String ticketNote = currentItem.getTicketNote();
        String dealerCode = currentItem.getDealerCode();
        String contact = currentItem.getContact();

        //binding the data with the viewholder views

        holder.textViewTitle.setText(ticketNote);

        holder.textViewShortDesc.setText("Dealer Code: " + dealerCode);
        holder.textViewPrice.setText("Contact info: " + contact);



    }

    @Override
    public int getItemCount() {
        return mTicketDetail.size();
    }

    class TicketViewHolder extends RecyclerView.ViewHolder{
        TextView textViewTitle, textViewShortDesc, textViewPrice;
        public TicketViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewShortDesc = itemView.findViewById(R.id.textViewShortDesc);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
        }
    }
}

package ps.room.headclearancefree;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FastenerTypesRecyclerAdapter extends RecyclerView.Adapter<FastenerTypesRecyclerAdapter.ViewHolder>{
    private static final String TAG = "===CLICK LOG=== ";
    private final List<FastenerType> mFastenerTypeList;
    private final LayoutInflater mLayoutInflater;
    private final Context mContext;

    FastenerTypesRecyclerAdapter(Context context, List<FastenerType> fastenerTypeList) {
        mFastenerTypeList = fastenerTypeList;
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.fastener_type_item, parent, false);
        float height = parent.getHeight();
        itemView.getLayoutParams().height = Math.round(height/3);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FastenerType fastenerType = mFastenerTypeList.get(position);
        holder.mFastenerType.setText(fastenerType.getFastenerType());
        holder.mFastenerImage.setImageBitmap(fastenerType.getImageBitmap());
        //holder.mCurrentPosition = position;
        holder.mCurrentPosition  = Integer.parseInt(fastenerType.getId());
    }

    @Override
    public int getItemCount() {
        return mFastenerTypeList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        final TextView mFastenerType;
        final ImageView mFastenerImage;
        int mCurrentPosition;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mFastenerType = itemView.findViewById(R.id.fastener_type);
            mFastenerImage = itemView.findViewById(R.id.fastener_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Log.i(tag,"Clicked position " + mCurrentPosition);
                    Intent intent = new Intent(mContext, FastenersListActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("FASTENER_TYPE_ID", mCurrentPosition);
                    mContext.startActivity(intent);
                }
            });
        }
    }
}

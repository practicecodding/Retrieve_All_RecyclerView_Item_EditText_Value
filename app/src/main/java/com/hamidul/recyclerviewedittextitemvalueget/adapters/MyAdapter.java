package com.hamidul.recyclerviewedittextitemvalueget.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hamidul.recyclerviewedittextitemvalueget.R;
import com.hamidul.recyclerviewedittextitemvalueget.activities.MainActivity;
import com.hamidul.recyclerviewedittextitemvalueget.model.Order;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.myViewHolder>{

    ArrayList<Order> arrayList;
    Context context;
    OnItemClickListener onItemClickListener;

    public MyAdapter(Context context, ArrayList<Order> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new myViewHolder(LayoutInflater.from(context).inflate(R.layout.item,parent,false),onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.productName.setText(arrayList.get(position).getName());

        /*holder.setIsRecyclable(false);
        holder.edUnit.setText(arrayList.get(position).getEditTextValue());
        holder.edUnit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                arrayList.get(position).setEditTextValue(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });*/

        if (holder.edUnit.getTag() instanceof TextWatcher) {
            holder.edUnit.removeTextChangedListener((TextWatcher) holder.edUnit.getTag());
        }

        holder.edUnit.setText(arrayList.get(position).getQuantity());

        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                arrayList.get(position).setQuantity(charSequence.toString());
                /**MainActivity2.arrayList = arrayList;*/

                for (Order item : arrayList){
                    if (!item.getQuantity().isEmpty()){
                        MainActivity.button.setEnabled(true);
                        MainActivity.button.setVisibility(View.VISIBLE);
                        break;
                    }
                    else {
                        MainActivity.button.setEnabled(false);
                        MainActivity.button.setVisibility(View.GONE);
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String s = editable.toString();
                if (!s.isEmpty() && s.startsWith("0")){
                    editable.delete(0,1);
                }
            }
        };
        holder.edUnit.addTextChangedListener(watcher);
        holder.edUnit.setTag(watcher);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder{
        TextView productName;
        EditText edUnit;
        public myViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            edUnit = itemView.findViewById(R.id.edUnit);

            /**itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    new AlertDialog.Builder(context)
                            .setTitle("Are you sure ?")
                            .setMessage("Delete this item")
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    onItemClickListener.onItemClick(getAdapterPosition());

                                }
                            })
                            .setCancelable(true)
                            .show();
                    return false;
                }
            });*/
        }

    }

}

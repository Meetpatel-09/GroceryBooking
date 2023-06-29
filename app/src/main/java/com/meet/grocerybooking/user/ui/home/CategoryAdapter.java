package com.meet.grocerybooking.user.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.meet.grocerybooking.R;
import com.meet.grocerybooking.user.ui.category.CategoryProductFragment;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private Context context;
    private List<String> list;

    public CategoryAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_item2, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {

        String name = list.get(position);
        holder.textView.setText(name);

        holder.textView.setOnClickListener(v -> {
            context.getSharedPreferences("CATEGORY", Context.MODE_PRIVATE).edit().putString("categoryName", name).apply();
            ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CategoryProductFragment()).commit();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static public class CategoryViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.category_item_home_name);
        }
    }
}

package com.meet.grocerybooking.adpters;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.meet.grocerybooking.R;
import com.meet.grocerybooking.models.ProductModel;
import com.meet.grocerybooking.models.ShopkeeperModel;

import java.util.List;

public class ShopkeeperAdapter extends RecyclerView.Adapter<ShopkeeperAdapter.ShopkeeperViewHolder> {

    private final Context context;
    private final List<ShopkeeperModel> list;

    public ShopkeeperAdapter(Context context, List<ShopkeeperModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ShopkeeperViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.shopkeeper_item, parent , false);
        return new ShopkeeperAdapter.ShopkeeperViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopkeeperViewHolder holder, int position) {
        final ShopkeeperModel model = list.get(position);

        String userID = model.getId();

        holder.textView.setText(model.getName());

        holder.button.setText("Remove");
        holder.button.setVisibility(View.VISIBLE);

        holder.button.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Are you sure you want to remove this Shopkeeper?");
            builder.setCancelable(true);
            builder.setPositiveButton("Yes", (dialog, which) -> {
                removeProducts(userID);
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("shopkeeper");
                reference.child(model.getId()).removeValue().addOnCompleteListener(task -> {
                    Toast.makeText(context, "User Removed Successfully", Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(e -> Toast.makeText(context, "Something went wrong!!", Toast.LENGTH_SHORT).show());
            });

            builder.setNegativeButton("No", (dialog, which) -> dialog.cancel());

            AlertDialog dialog = null;
            try {
                dialog = builder.create();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (dialog != null)
                dialog.show();
        });
    }

    private void removeProducts(String userID) {

        FirebaseDatabase.getInstance().getReference().child("products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ProductModel data = dataSnapshot.getValue(ProductModel.class);{
                        assert data != null;
                        if (data.getShopkeeperID().equals(userID)) {
                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("products");
                            reference.child(data.getId()).removeValue();

                        }
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ShopkeeperViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        private TextView textView;
        private Button button;

        public ShopkeeperViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.user_image_profile);
            textView = itemView.findViewById(R.id.user_name);
            button = itemView.findViewById(R.id.btn_chat);
        }
    }
}

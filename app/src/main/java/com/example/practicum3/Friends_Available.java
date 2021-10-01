package com.example.practicum3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Friends_Available extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Context context;
    private List<Club> clubs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends__available);
        recyclerView = (RecyclerView)findViewById(R.id.fRecycleView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);

        RecycleViewAdapter adapter = new RecycleViewAdapter(clubs);

        initializeData();
        initializeAdapter();

    }
    private void initializeAdapter() {
        RecycleViewAdapter adapter = new RecycleViewAdapter(clubs);
        recyclerView.setAdapter(adapter);
    }

    private void initializeData() {
        clubs = new ArrayList<>();
        clubs.add(new Club("test1",R.drawable.ic_launcher_background));
        clubs.add(new Club("test2",R.drawable.ic_launcher_background));
        clubs.add(new Club("test3",R.drawable.ic_launcher_background));
        clubs.add(new Club("test4",R.drawable.ic_launcher_background));
        clubs.add(new Club("test5",R.drawable.ic_launcher_background));
        clubs.add(new Club("test6",R.drawable.ic_launcher_background));
        clubs.add(new Club("test7",R.drawable.ic_launcher_background));
    }

}
class Club{
    String name;
    int logoId;
    Club(String name,int logoId){
        this.logoId = logoId;
        this.name = name;

    }
}
class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ClubViewHolder>{

    List<Club> clubs;

    public RecycleViewAdapter(List<Club> clubs) {
        this.clubs = clubs;
    }

    @NonNull
    @Override
    public ClubViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layout,parent,false);
        ClubViewHolder cvh = new ClubViewHolder(view);
        return cvh;
    }

    @Override
    public void onBindViewHolder(@NonNull ClubViewHolder holder, int position) {
        holder.clubName.setText(clubs.get(position).name);
        holder.clubLogo.setImageResource(clubs.get(position).logoId);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return clubs.size();
    }

    public static class ClubViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView clubName;
        ImageView clubLogo;
        ClubViewHolder(View itemView){
            super(itemView);
            cardView = (CardView)itemView.findViewById(R.id.cardView);
            clubName = (TextView)itemView.findViewById(R.id.club_name);
            clubLogo = (ImageView)itemView.findViewById(R.id.logo);

        }
    }




}
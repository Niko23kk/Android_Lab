package nao.fit.bstu.lab3;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import nao.fit.bstu.lab3.Room.Cybersport;

public class CustomRecyclerAdapter extends RecyclerView.Adapter<CustomRecyclerAdapter.ViewHolder>{

    private ArrayList<Cybersport> cybersports;
    public CustomRecyclerAdapter(ArrayList<Cybersport> contacts) {
        this.cybersports= contacts;
    }
    @NonNull
    @Override
    public CustomRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_view, parent, false);

        return new ViewHolder((view));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomRecyclerAdapter.ViewHolder viewHolder, int position) {
        Cybersport cybersport = cybersports.get(position);
        viewHolder.id.setText(String.valueOf(cybersport.getId()));
        viewHolder.name.setText(cybersport.getName());
        viewHolder.lastname.setText(cybersport.getLastName());
        viewHolder.age.setText(String.valueOf(cybersport.getAge()));
        viewHolder.salary.setText(String.valueOf(cybersport.getSalary()));
    }

    @Override
    public int getItemCount() {
        return this.cybersports.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView id;
        TextView name;
        TextView lastname;
        TextView age;
        TextView salary;
        public ViewHolder(View view){
            super(view);
            id = view.findViewById(R.id.id);
            name = view.findViewById(R.id.name);
            lastname = view.findViewById(R.id.lastName);
            age = view.findViewById(R.id.age);
            salary = view.findViewById(R.id.salary);

        }
    }
}


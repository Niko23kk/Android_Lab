package nao.fit.bstu.lab3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import nao.fit.bstu.lab3.Room.Cybersport;

public class CybersportView extends ArrayAdapter<Cybersport> {
    private LayoutInflater inflater;
    private int layout;
    private List<Cybersport> cybersports;

    public CybersportView(Context context, int resource, List<Cybersport> cybersports) {
        super(context, resource, cybersports);
        this.cybersports= cybersports;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Cybersport cybersport= getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_view, parent, false);
        }
        // Lookup view for data population
        TextView tvID = (TextView) convertView.findViewById(R.id.id);
        TextView tvName = (TextView) convertView.findViewById(R.id.name);
        TextView tvLastName = (TextView) convertView.findViewById(R.id.lastName);
        TextView tvAge = (TextView) convertView.findViewById(R.id.age);
        TextView tvSalary = (TextView) convertView.findViewById(R.id.salary);

        tvID.setText(String.valueOf(cybersport.getId()));
        tvName.setText(String.valueOf(cybersport.getName()));
        tvLastName.setText(cybersport.getLastName());
        tvAge.setText(String.valueOf(cybersport.getAge()));
        tvSalary.setText(String.valueOf(cybersport.getSalary()));
        // Return the completed view to render on screen
        return convertView;
    }
}

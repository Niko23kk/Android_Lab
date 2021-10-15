package nao.fit.bstu.lab3;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentListView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentListView extends Fragment {

    private OnItemSelectedListener mListener;
    Intent intentInfo;
    Intent updateIntent;
    List<Cybersport> cybersportList;
    ListView allItems;
    View view;
    int orderSort=0;

    // Define the events that the fragment will use to communicate
    public interface OnItemSelectedListener {
        public void onItemSelected(Cybersport cybersport);
    }

    public FragmentListView() {
        // Required empty public constructor
    }

    public static FragmentListView newInstance(String param1, String param2) {
        FragmentListView fragment = new FragmentListView();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_list_view, container, false);
        intentInfo = new Intent(view.getContext(), CybersportInfoActivity.class);
        cybersportList = JSONMethod.importFromJSON(view.getContext());
        if(cybersportList==null){
            cybersportList=new ArrayList<>();
        }
        allItems = view.findViewById(R.id.cybersportList);

        CybersportView stateAdapter = new CybersportView(this.getContext(), R.layout.item_view, cybersportList);
        allItems.setAdapter(stateAdapter);

        registerForContextMenu(allItems);

        AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Cybersport selectedCybersport = (Cybersport) parent.getItemAtPosition(position);
//                intentInfo.putExtra(Cybersport.class.getSimpleName(), (Serializable) selectedCybersport);
//                startActivity(intentInfo);
                mListener.onItemSelected(selectedCybersport);
                Toast.makeText(view.getContext(), "Был выбран пункт " + selectedCybersport.getId(), Toast.LENGTH_SHORT).show();
            }
        };

        allItems.setOnItemClickListener(itemListener);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnItemSelectedListener) {
            mListener = (OnItemSelectedListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement MyListFragment.OnItemSelectedListener");
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.open:
                Cybersport selectedCybersport = (Cybersport) allItems.getItemAtPosition(info.position);
                intentInfo.putExtra(Cybersport.class.getSimpleName(), (Serializable) selectedCybersport);
                startActivity(intentInfo);
                Toast.makeText(view.getContext(), "Был выбран пункт " + selectedCybersport.getId(), Toast.LENGTH_SHORT).show();
                return true;
            case R.id.edit:
                updateIntent = new Intent(view.getContext(), CybersportInfoUpdateActivity.class);
                updateIntent.putExtra(Cybersport.class.getSimpleName(), (Serializable) allItems.getItemAtPosition(info.position));
                startActivity(updateIntent);
                return true;
            case R.id.delete:
                AlertDialog.Builder builder=new AlertDialog.Builder(view.getContext());
                builder.setTitle("Warning")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setMessage("Want you delete an object")
                        .setPositiveButton("Ok",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        deleteItem((Cybersport) allItems.getItemAtPosition(info.position));
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        dialog.dismiss();
                                    }
                                })
                        .create();
                builder.show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void deleteItem(Cybersport selectCybersport){
        cybersportList=JSONMethod.importFromJSON(view.getContext());
        cybersportList=cybersportList.stream()
                .filter(item->item.getId()!=selectCybersport.getId()).collect(Collectors.toList());
        boolean result = JSONMethod.exportToJSON(view.getContext(), cybersportList);
        if (result) {
            Toast.makeText(view.getContext(), "Successful", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(view.getContext(), "Error", Toast.LENGTH_SHORT).show();
        }
        CybersportView stateAdapter = new CybersportView(view.getContext(), R.layout.item_view, cybersportList);
        allItems.setAdapter(stateAdapter);
    }

    public void sortList(){
        CybersportView stateAdapter = new CybersportView(view.getContext(), R.layout.item_view, cybersportList);
        if(orderSort==0) {
            Collections.sort(cybersportList, new Comparator<Cybersport>() {
                public int compare(Cybersport o1, Cybersport o2) {
                    return String.valueOf(o1.getId()).compareTo(String.valueOf(o2.getId()));
                }
            });
            orderSort=1;
        }else {
            Collections.sort(cybersportList, new Comparator<Cybersport>() {
                public int compare(Cybersport o1, Cybersport o2) {
                    return String.valueOf(o2.getId()).compareTo(String.valueOf(o1.getId()));
                }
            });
            orderSort=0;
        }
        allItems.setAdapter(stateAdapter);
    }
}
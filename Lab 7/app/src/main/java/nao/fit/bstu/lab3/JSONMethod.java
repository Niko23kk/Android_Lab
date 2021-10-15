package nao.fit.bstu.lab3;

import android.content.Context;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class JSONMethod {

    public static final String FILE_NAME = "data.json";
    public static FileOutputStream fileOutputStream= null;
    public static InputStreamReader streamReader = null;
    public static FileInputStream fileInputStream = null;


    static boolean exportToJSON(Context context, List<Cybersport> dataList) {
        Gson gson = new Gson();
        DataItems dataItems = new DataItems();
        dataItems.setContacts(dataList);
        String jsonString = gson.toJson(dataItems);

        try {
            fileOutputStream = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            fileOutputStream.write(jsonString.getBytes());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return false;
    }

    static List<Cybersport> importFromJSON(Context context) {
        try{
            fileInputStream = context.openFileInput(FILE_NAME);
            if(fileInputStream==null){
                new File(context.getDataDir(),FILE_NAME);
                fileInputStream = context.openFileInput(FILE_NAME);
            }
            streamReader = new InputStreamReader(fileInputStream);
            Gson gson = new Gson();
            DataItems dataItems = gson.fromJson(streamReader, DataItems.class);
            return  dataItems.getContacts();
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
        finally {
            if (streamReader != null) {
                try {
                    streamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    static class DataItems {
        private List<Cybersport> cybersports;

        List<Cybersport> getContacts() {
            return cybersports;
        }
        void setContacts(List<Cybersport> contacts) {
            this.cybersports = contacts;
        }
    }

}

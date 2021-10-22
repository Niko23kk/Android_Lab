package nao.fit.bstu.lab3.Room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface CybersportDao {
    @Query("SELECT * FROM Cybersport WHERE id = :id")
    ModelCases getCaseId(long id);

    @Query("SELECT * FROM ModelCases")
    LiveData<List<ModelCases>> getAllCases();

    @Insert
    void addCase(ModelCases modelCases);

    @Update
    void updateCase(ModelCases modelCases);

    @Delete
    void deleteCase(ModelCases modelCases);
}

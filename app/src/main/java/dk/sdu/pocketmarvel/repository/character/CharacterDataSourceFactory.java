package dk.sdu.pocketmarvel.repository.character;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;

import dk.sdu.pocketmarvel.vo.Character;

public class CharacterDataSourceFactory extends DataSource.Factory<Integer, Character> {

    private MutableLiveData<CharacterDataSource> sourceLiveData = new MutableLiveData<>();

    @Override
    public DataSource<Integer, Character> create() {
        CharacterDataSource source = new CharacterDataSource();
        sourceLiveData.postValue(source);
        return source;
    }

    public LiveData<CharacterDataSource> getSourceLiveData() {
        return sourceLiveData;
    }
}

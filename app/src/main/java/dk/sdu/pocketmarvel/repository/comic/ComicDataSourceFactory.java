package dk.sdu.pocketmarvel.repository.comic;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;

import dk.sdu.pocketmarvel.vo.Comic;


public class ComicDataSourceFactory extends DataSource.Factory<Integer, Comic> {
    private MutableLiveData<ComicDataSource> sourceLiveData = new MutableLiveData<>();

    @Override
    public DataSource<Integer, Comic> create() {
        ComicDataSource source = new ComicDataSource();
        sourceLiveData.postValue(source);
        return source;
    }

    public LiveData<ComicDataSource> getSourceLiveData() {
        return sourceLiveData;
    }
}

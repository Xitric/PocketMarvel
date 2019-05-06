package dk.sdu.pocketmarvel.repository.comic;

import android.arch.paging.PositionalDataSource;
import android.support.annotation.NonNull;

import dk.sdu.pocketmarvel.repository.api.MarvelClient;
import dk.sdu.pocketmarvel.vo.Comic;
import dk.sdu.pocketmarvel.vo.MarvelDataContainer;
import dk.sdu.pocketmarvel.vo.MarvelDataWrapper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class ComicDataSource  extends PositionalDataSource<Comic> {
    //TODO: How do we handle this best?
    private String namePrefix = "b";

//    private MutableLiveData<FetchError> errorLiveData = new MutableLiveData<>();

    public void setNamePrefix(String namePrefix) {
        this.namePrefix = namePrefix;
    }

//    public LiveData<FetchError> getErrorLiveData() {
//        return errorLiveData;
//    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<Comic> callback) {
        //Perform request on a background thread supplied by Retrofit
        MarvelClient.getService().getComics(namePrefix, params.requestedStartPosition, params.requestedLoadSize)
                .enqueue(new Callback<MarvelDataWrapper<Comic>>() {
                    @Override
                    public void onResponse(@NonNull Call<MarvelDataWrapper<Comic>> call, @NonNull Response<MarvelDataWrapper<Comic>> response) {
                        //OnResponse is also called for erroneous error codes. OnFailure is only for
                        //when the request itself could not even be performed.
                        if (response.isSuccessful()) {
                            MarvelDataContainer<Comic> container = response.body() != null ?
                                    response.body().getData() : null;

                            if (container == null) {
                                onFailure(call, new HttpException(response));
                                return;
                            }

                            callback.onResult(container.getResults(), container.getOffset(), container.getTotal());
                        } else {
                            onFailure(call, new HttpException(response));onFailure(call, new HttpException(response));
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<MarvelDataWrapper<Comic>> call, @NonNull Throwable t) {
                        //TODO: Should we retry the request?
                        handleError(t.getMessage());
                    }
                });
    }

    @Override
    public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<Comic> callback) {
        //Perform request on a background thread supplied by Retrofit
        MarvelClient.getService().getComics(namePrefix, params.startPosition, params.loadSize)
                .enqueue(new Callback<MarvelDataWrapper<Comic>>() {
                    @Override
                    public void onResponse(@NonNull Call<MarvelDataWrapper<Comic>> call, @NonNull Response<MarvelDataWrapper<Comic>> response) {
                        //OnResponse is also called for erroneous error codes. OnFailure is only for
                        //when the request itself could not even be performed.
                        if (response.isSuccessful()) {
                            MarvelDataContainer<Comic> container = response.body() != null ?
                                    response.body().getData() : null;

                            if (container == null) {
                                onFailure(call, new HttpException(response));
                                return;
                            }

                            callback.onResult(container.getResults());
                        } else {
                            onFailure(call, new HttpException(response));
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<MarvelDataWrapper<Comic>> call, @NonNull Throwable t) {
                        //TODO: Should we retry the request?
                        handleError(t.getMessage());
                    }
                });
    }

    private void handleError(String message) {
//        errorLiveData.postValue(new FetchError(message));
    }
}

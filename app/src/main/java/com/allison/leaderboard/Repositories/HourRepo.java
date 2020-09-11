package com.allison.leaderboard.Repositories;

import androidx.lifecycle.MutableLiveData;


import com.allison.leaderboard.DataSource.GadsApi;
import com.allison.leaderboard.DataSource.RetrofitBase;
import com.allison.leaderboard.Models.Hour;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HourRepo {

    private List<Hour> mHours = new ArrayList<>();
    private static HourRepo instance = null;
    private MutableLiveData<Throwable> mThrowableMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<String> ErrorCode = new MutableLiveData<>();


    public static HourRepo getInstance() {
        if (instance == null) {
            instance = new HourRepo();
        }
        return instance;
    }

    public MutableLiveData<List<Hour>> GetHours() {
        final MutableLiveData<List<Hour>> data = new MutableLiveData<>();

        GadsApi gadsApi = RetrofitBase.getInstance().buildRetrofit(RetrofitBase.GET_BASE_URL).create(GadsApi.class);
        Call<List<Hour>> call = gadsApi.getHours();
        call.enqueue(new Callback<List<Hour>>() {
            @Override
            public void onResponse(Call<List<Hour>> call, Response<List<Hour>> response) {
                if (!response.isSuccessful()) {
                    ErrorCode.setValue("An " + response.code() + " has occurred, please try again later.");
                    GetErrorCode();
                    return;
                }
                mHours = response.body();
                data.setValue(mHours);
            }

            @Override
            public void onFailure(Call<List<Hour>> call, Throwable t) {
                mThrowableMutableLiveData.setValue(t);
                GetThrowableError();
            }
        });
        return data;
    }

    public MutableLiveData<Throwable> GetThrowableError() {
        return mThrowableMutableLiveData;
    }

    public MutableLiveData<String> GetErrorCode() {
        return ErrorCode;
    }
}

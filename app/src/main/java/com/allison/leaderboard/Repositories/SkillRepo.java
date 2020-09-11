package com.allison.leaderboard.Repositories;

import androidx.lifecycle.MutableLiveData;

import com.allison.leaderboard.DataSource.GadsApi;
import com.allison.leaderboard.DataSource.RetrofitBase;
import com.allison.leaderboard.Models.Skill;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SkillRepo {
    private static SkillRepo instance = null;
    private List<Skill> mSkills = new ArrayList<>();
    private MutableLiveData<Throwable> mThrowableMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<String> ErrorCode = new MutableLiveData<>();


    public static SkillRepo getInstance() {
        if (instance == null) {
            instance = new SkillRepo();
        }
        return instance;
    }


    public MutableLiveData<List<Skill>> GetSkillIQ() {
        final MutableLiveData<List<Skill>> data = new MutableLiveData<>();
        GadsApi gadsApi = RetrofitBase.getInstance().buildRetrofit(RetrofitBase.GET_BASE_URL).create(GadsApi.class);
        Call<List<Skill>> call = gadsApi.getSkill();
        call.enqueue(new Callback<List<Skill>>() {
            @Override
            public void onResponse(Call<List<Skill>> call, Response<List<Skill>> response) {
                if (!response.isSuccessful()) {
                    ErrorCode.setValue("An " + response.code() + " has occurred, please try again later.");
                    GetErrorCode();
                    return;
                }

                mSkills = response.body();
                data.setValue(mSkills);
            }

            @Override
            public void onFailure(Call<List<Skill>> call, Throwable t) {
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

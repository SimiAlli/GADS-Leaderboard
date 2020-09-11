package com.allison.leaderboard.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.allison.leaderboard.CustomAdapters.SkillRecyclerAdapter;
import com.allison.leaderboard.Models.Skill;
import com.allison.leaderboard.R;
import com.allison.leaderboard.ViewModel.SkillViewModel;

import java.util.ArrayList;
import java.util.List;

public class SkillFragment extends Fragment {

    private List<Skill> mSkills = new ArrayList<>();
    private SkillRecyclerAdapter mAdapter;
    private SkillViewModel mSkillViewModel;
    private ProgressBar mProgressBar;
    private View mErrorPage;
    private Button mErrorButton;
    private TextView mErrorMessage;

    public SkillFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_skill, container, false);

        mErrorPage = view.findViewById(R.id.error_page);
        mErrorButton= mErrorPage.findViewById(R.id.retry_button);
        mErrorMessage= mErrorPage.findViewById(R.id.error_message);
        mErrorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mErrorPage.setVisibility(View.INVISIBLE);
                mProgressBar.setVisibility(View.VISIBLE);
                mSkillViewModel.Retry();
                getSkillIQ();
            }
        });


        mProgressBar = view.findViewById(R.id.progress_circular);

        mSkillViewModel = ViewModelProviders.of(getActivity()).get(SkillViewModel.class);
        mSkillViewModel.init();
        getSkillIQ();

        RecyclerView recyclerView = view.findViewById(R.id.list_skill);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter = new SkillRecyclerAdapter(mSkills);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        getThrowableError();
        getErrorCode();

        // Inflate the layout for this fragment
        return view;
    }

    private void getErrorCode() {
        mSkillViewModel.getErrorCode().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                mErrorMessage.setText(s);
                mErrorPage.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.INVISIBLE);
            }
        });

    }

    private void getThrowableError() {
        mSkillViewModel.getThrowableError().observe(getActivity(), new Observer<Throwable>() {
            @Override
            public void onChanged(Throwable throwable) {
                mProgressBar.setVisibility(View.INVISIBLE);
                mErrorMessage.setText(getActivity().getResources().getString(R.string.error_message));
                mErrorPage.setVisibility(View.VISIBLE);
            }
        });
    }

    private void getSkillIQ() {
        mSkillViewModel.getSkill().observe(getActivity(), new Observer<List<Skill>>() {
            @Override
            public void onChanged(List<Skill> skills) {
                mAdapter.setSkills(skills);
                mProgressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

}

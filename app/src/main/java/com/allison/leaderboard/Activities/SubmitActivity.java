package com.allison.leaderboard.Activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.allison.leaderboard.DataSource.GadsApi;
import com.allison.leaderboard.DataSource.RetrofitBase;
import com.allison.leaderboard.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SubmitActivity extends AppCompatActivity {

    private Button mConfirmYes;
    private AlertDialog mAlertDialog;
    private View mDialogView;
    private View mFormLayout;
    private ImageView mCancelButton, mBackButton;
    private TextView mFirstName, mLastName, mEmailAddress, mProjectLink;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);

        mBackButton = findViewById(R.id.back_button);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }

        });

        mFormLayout = findViewById(R.id.form_layout);
        Button submitButton = findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateFields(v);
            }
        });

        mProgressBar = findViewById(R.id.progress_circular);
        mFirstName = findViewById(R.id.first_name);
        mLastName = findViewById(R.id.last_name);
        mEmailAddress = findViewById(R.id.email_address);
        mProjectLink = findViewById(R.id.project_link);
    }

    void confirmationDialog(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(SubmitActivity.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        mDialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.confirmation_dialog, viewGroup, false);
        mConfirmYes = mDialogView.findViewById(R.id.yes_button);
        mConfirmYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialog.dismiss();
              submitProject(v);
            }
        });
        mCancelButton = mDialogView.findViewById(R.id.cancel_button);
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialog.dismiss();
                Toast.makeText(SubmitActivity.this, "Submission cancelled", Toast.LENGTH_LONG).show();
            }
        });

        builder.setView(mDialogView);
        mAlertDialog = builder.create();
        mAlertDialog.setCanceledOnTouchOutside(false);
        mAlertDialog.show();
    }

    void successDialog(View v) {
        mFormLayout.setVisibility(View.INVISIBLE);
        AlertDialog.Builder builder = new AlertDialog.Builder(SubmitActivity.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        mDialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.success_dialog, viewGroup, false);
        builder.setView(mDialogView);
        mAlertDialog = builder.create();
        mAlertDialog.show();
        mAlertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                finish();
            }
        });
    }

    void failedDialog(View v) {
        mFormLayout.setVisibility(View.INVISIBLE);
        AlertDialog.Builder builder = new AlertDialog.Builder(SubmitActivity.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        mDialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.failed_dialog, viewGroup, false);
        builder.setView(mDialogView);
        mAlertDialog = builder.create();
        mAlertDialog.show();
        mAlertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mFormLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    void validateFields(View v) {
        if (mFirstName.getText().toString().isEmpty() || mLastName.getText().toString().isEmpty()|| mEmailAddress.getText().toString().isEmpty()
                || mProjectLink.getText().toString().isEmpty()) {
            Toast.makeText(this, "Missing field! All fields are required!", Toast.LENGTH_SHORT).show();
            return;
        }
        confirmationDialog(v);
    }

    void submitProject(final View v) {
        String firstName = mFirstName.getText().toString();
        String lastName = mLastName.getText().toString();
        String emailAddress = mEmailAddress.getText().toString();
        String projectLink = mProjectLink.getText().toString();

        mProgressBar.setVisibility(View.VISIBLE);
        GadsApi api = RetrofitBase.getInstance().buildRetrofit(RetrofitBase.POST_BASE_URL).create(GadsApi.class);
        api.submitForm(emailAddress, firstName, lastName, projectLink)
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        mProgressBar.setVisibility(View.INVISIBLE);

                        if (!response.isSuccessful()) {
                            Toast.makeText(SubmitActivity.this, "A " + response.code() + " occurred", Toast.LENGTH_SHORT).show();
                            failedDialog(v);
                            return;
                        }
                        successDialog(v);
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        mProgressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(SubmitActivity.this, t.getMessage() + ", try again later.", Toast.LENGTH_SHORT).show();
                        failedDialog(v);
                    }
                });

    }
}

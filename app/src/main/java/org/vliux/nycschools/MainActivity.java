package org.vliux.nycschools;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import org.vliux.nycschools.data.HighSchool;
import org.vliux.nycschools.viewmodel.HighSchoolListViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private HighSchoolListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = new ViewModelProvider(this).get(HighSchoolListViewModel.class);
        viewModel.highSchools.observe(this, listViewModelData -> {
            switch (listViewModelData.getStatus()) {
                case LOADING:
                    onSchoolsLoading();
                    break;
                case SUCCESS:
                    onSchoolsLoaded(listViewModelData.getData());
                    break;
                case ERROR:
                    onSchoolsLoadingFailed();
                    break;
            }
        });
        viewModel.loadHighSchools(this);
    }

    private void onSchoolsLoading() {

    }

    private void onSchoolsLoaded(final @Nullable List<HighSchool> schoolList) {

    }

    private void onSchoolsLoadingFailed() {

    }
}
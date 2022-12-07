package org.vliux.nycschools.listing;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.vliux.nycschools.R;
import org.vliux.nycschools.data.HighSchool;

import java.util.List;

public class HighSchoolListActivity extends AppCompatActivity {

  private HighSchoolListViewModel viewModel;
  private HighSchoolRecyclerViewAdapter schoolsRecyclerViewAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    setupRecyclerView();

    viewModel = new ViewModelProvider(this).get(HighSchoolListViewModel.class);
    viewModel.highSchools.observe(
        this,
        listViewModelData -> {
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

  private void setupRecyclerView() {
    final RecyclerView schoolsRecyclerView = findViewById(R.id.school_list);
    schoolsRecyclerView.setLayoutManager(
        new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    schoolsRecyclerView.setItemAnimator(new DefaultItemAnimator());
    schoolsRecyclerViewAdapter = new HighSchoolRecyclerViewAdapter();
    schoolsRecyclerView.setAdapter(schoolsRecyclerViewAdapter);
  }

  private void onSchoolsLoading() {}

  private void onSchoolsLoaded(final @Nullable List<HighSchool> schoolList) {
    schoolsRecyclerViewAdapter.setHighSchools(schoolList);
  }

  private void onSchoolsLoadingFailed() {}
}

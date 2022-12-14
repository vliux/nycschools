package org.vliux.nycschools.listing;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.vliux.nycschools.Navigation;
import org.vliux.nycschools.R;
import org.vliux.nycschools.data.HighSchool;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HighSchoolListActivity extends AppCompatActivity {

  private HighSchoolRecyclerViewAdapter schoolsRecyclerViewAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    setupRecyclerView();

    final HighSchoolListViewModel viewModel =
        new ViewModelProvider(this).get(HighSchoolListViewModel.class);
    viewModel
        .getHighSchools()
        .observe(
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
    final LinearLayoutManager layoutManager =
        new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
    schoolsRecyclerView.setLayoutManager(layoutManager);
    schoolsRecyclerView.setItemAnimator(new DefaultItemAnimator());
    schoolsRecyclerView.addItemDecoration(
        new DividerItemDecoration(this, layoutManager.getOrientation()));
    schoolsRecyclerViewAdapter =
        new HighSchoolRecyclerViewAdapter(
            (itemView, highSchool) ->
                Navigation.INSTANCE.onSchoolClicked(itemView.getContext(), highSchool));
    schoolsRecyclerView.setAdapter(schoolsRecyclerViewAdapter);
  }

  private void onSchoolsLoading() {
    // Show loading UI when school list is being loaded, progress-bar or shimmer etc.
  }

  private void onSchoolsLoaded(final @Nullable List<HighSchool> schoolList) {
    schoolsRecyclerViewAdapter.setHighSchools(schoolList);
  }

  private void onSchoolsLoadingFailed() {
    // Show error if school list gets failed to be loaded
  }
}

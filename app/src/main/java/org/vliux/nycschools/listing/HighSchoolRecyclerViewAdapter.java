package org.vliux.nycschools.listing;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.vliux.nycschools.R;
import org.vliux.nycschools.data.HighSchool;

import java.util.List;

class HighSchoolRecyclerViewAdapter extends RecyclerView.Adapter<HighSchoolItemViewHolder> {

  private List<HighSchool> highSchools;

  void setHighSchools(final List<HighSchool> newHighSchools) {
    highSchools = newHighSchools;
    notifyDataSetChanged();
  }

  @NonNull
  @Override
  public HighSchoolItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    final View itemView =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_high_school, parent, false);
    return new HighSchoolItemViewHolder(itemView);
  }

  @Override
  public void onBindViewHolder(@NonNull HighSchoolItemViewHolder holder, int position) {
    holder.getSchoolNameTextView().setText(highSchools.get(position).getName());
  }

  @Override
  public int getItemCount() {
    return highSchools != null ? highSchools.size() : 0;
  }
}

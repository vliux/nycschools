package org.vliux.nycschools.listing;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import org.vliux.nycschools.R;
import org.vliux.nycschools.data.HighSchool;

import java.util.List;

class HighSchoolRecyclerViewAdapter extends RecyclerView.Adapter<HighSchoolItemViewHolder> {

  interface OnItemClickedListener {
    void onItemClicked(final View itemView, final @NonNull HighSchool highSchool);
  }

  private @Nullable List<HighSchool> highSchools;
  private @Nullable OnItemClickedListener onItemClickedListener;

  public HighSchoolRecyclerViewAdapter(@Nullable OnItemClickedListener onItemClickedListener) {
    this.onItemClickedListener = onItemClickedListener;
  }

  void setHighSchools(final List<HighSchool> newHighSchools) {
    highSchools = newHighSchools;
    notifyDataSetChanged();
  }

  @NonNull
  @Override
  public HighSchoolItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    final View itemView =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_high_school, parent, false);
    final HighSchoolItemViewHolder viewHolder = new HighSchoolItemViewHolder(itemView);
    itemView.setOnClickListener(
        v -> {
          final HighSchool highSchool = viewHolder.getHighSchool();
          if (highSchool != null && onItemClickedListener != null) {
            onItemClickedListener.onItemClicked(v, highSchool);
          }
        });
    return viewHolder;
  }

  @Override
  public void onBindViewHolder(@NonNull HighSchoolItemViewHolder holder, int position) {
    holder.setHighSchool(highSchools.get(position));
  }

  @Override
  public int getItemCount() {
    return highSchools != null ? highSchools.size() : 0;
  }
}

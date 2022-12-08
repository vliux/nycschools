package org.vliux.nycschools.listing;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import org.vliux.nycschools.R;
import org.vliux.nycschools.data.HighSchool;

public class HighSchoolItemViewHolder extends RecyclerView.ViewHolder {

  private final TextView schoolNameTextView;
  private @Nullable HighSchool highSchool;

  public HighSchoolItemViewHolder(@NonNull View itemView) {
    super(itemView);
    schoolNameTextView = itemView.findViewById(R.id.item_school_name);
  }

  public void setHighSchool(@Nullable HighSchool school) {
    highSchool = school;
    if (school != null) {
      schoolNameTextView.setText(school.getName());
    }
  }

  public @Nullable HighSchool getHighSchool() {
    return highSchool;
  }
}

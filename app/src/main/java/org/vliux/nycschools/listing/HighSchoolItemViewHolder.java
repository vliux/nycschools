package org.vliux.nycschools.listing;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.vliux.nycschools.R;

public class HighSchoolItemViewHolder extends RecyclerView.ViewHolder {

  private final TextView schoolNameTextView;

  public HighSchoolItemViewHolder(@NonNull View itemView) {
    super(itemView);
    schoolNameTextView = itemView.findViewById(R.id.item_school_name);
  }

  public TextView getSchoolNameTextView() {
    return schoolNameTextView;
  }
}

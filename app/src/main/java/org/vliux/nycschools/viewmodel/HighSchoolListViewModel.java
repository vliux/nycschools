package org.vliux.nycschools.viewmodel;

import android.app.Activity;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.vliux.nycschools.data.HighSchool;
import org.vliux.nycschools.data.HighSchoolDataException;
import org.vliux.nycschools.data.HighSchoolRepository;
import org.vliux.nycschools.util.ActivityUtils;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.Executors;

public class HighSchoolListViewModel extends ViewModel {

    private MutableLiveData<ViewModelData<List<HighSchool>>> highSchoolsMutable = new MutableLiveData<>();

    public final LiveData<ViewModelData<List<HighSchool>>> highSchools = highSchoolsMutable;

    public void loadHighSchools(final Activity activity) {
        new LoadSchoolsAsyncTask(activity).executeOnExecutor(Executors.newCachedThreadPool());
    }

    private class LoadSchoolsAsyncTask extends AsyncTask<Void, Integer, List<HighSchool>> {
        private final WeakReference<Activity> activityRef;

        public LoadSchoolsAsyncTask(final Activity activity) {
            this.activityRef = new WeakReference<>(activity);
        }

        @Override
        protected void onPreExecute() {
            if (ActivityUtils.INSTANCE.isAlive(activityRef)) {
                highSchoolsMutable.setValue(ViewModelData.Companion.loading());
            }
        }

        @Override
        protected List<HighSchool> doInBackground(Void... voids) {
            try {
                if (ActivityUtils.INSTANCE.isAlive(activityRef)) {
                    return HighSchoolRepository.INSTANCE.loadHighSchools(activityRef.get());
                }
            } catch (HighSchoolDataException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<HighSchool> highSchools) {
            if (ActivityUtils.INSTANCE.isAlive(activityRef)) {
                highSchoolsMutable.setValue(highSchools != null ?
                        ViewModelData.Companion.success(highSchools)
                        : ViewModelData.Companion.error(null));
            }
        }
    }
}

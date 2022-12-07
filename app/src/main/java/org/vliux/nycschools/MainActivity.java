package org.vliux.nycschools;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import org.vliux.nycschools.data.HighSchool;
import org.vliux.nycschools.data.HighSchoolDataException;
import org.vliux.nycschools.data.HighSchoolRepository;
import org.vliux.nycschools.data.HighSchoolXmlParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new MainAsyncTask().executeOnExecutor(Executors.newCachedThreadPool());
    }

    private class MainAsyncTask extends AsyncTask<Void, Integer, List<HighSchool>> {

        @Override
        protected List<HighSchool> doInBackground(Void... voids) {
            try {
                return HighSchoolRepository.INSTANCE.loadHighSchools(MainActivity.this);
            } catch (HighSchoolDataException e) {
                e.printStackTrace();
            }
            return new ArrayList<>();
        }

        @Override
        protected void onPostExecute(List<HighSchool> highSchools) {
            StringBuilder sb = new StringBuilder();
            for (HighSchool school : highSchools) {
                sb.append(school.getName());
                sb.append("\n");
            }
            ((TextView) findViewById(R.id.tv)).setText(sb.length() > 0 ? sb.toString() : "NONE");
        }
    }
}
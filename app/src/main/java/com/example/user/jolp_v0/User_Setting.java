package com.example.user.jolp_v0;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.StringTokenizer;

public class User_Setting extends PreferenceActivity
{
    static String num;
    static SharedPreferences sharedPref;
    phpdo task;
    phpdo1 task1;

    private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener()
    {
        @Override
        public boolean onPreferenceChange(Preference preference, Object value)
        {
            String stringValue = value.toString();

            if (preference instanceof ListPreference)
            {
                // For list preferences, look up the correct display value in
                // the preference's 'entries' list.
                ListPreference listPreference = (ListPreference) preference;
                int index = listPreference.findIndexOfValue(stringValue);

                // Set the summary to reflect the new value.
                preference.setSummary(index >= 0? listPreference.getEntries()[index]: null);
            }
            else
            {
                // For all other preferences, set the summary to the value's
                // simple string representation.
                preference.setSummary(stringValue);
            }
            //num = sharedPref.getString("partnerphonevalue","");
            //Log.d(num+"*******","kk");

            return true;
        }
    };

    private static void bindPreferenceSummaryToValue(Preference preference)
    {
        // Set the listener to watch for value changes.
        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

        // Trigger the listener immediately with the preference's
        // current value.
        sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        task = new phpdo();
        task.execute(Main2Activity.id);





    }

    @Override
    protected void onPause(){
        super.onPause();
        String id = sharedPref.getString("idvalue", "");
        String phone1 = sharedPref.getString("partnerphonevalue", "");
        String phone = sharedPref.getString("phonevalue", "");
        String addr = sharedPref.getString("homevalue", "");
        String bir = sharedPref.getString("birthvalue", "");
        String pw = sharedPref.getString("pwvalue", "");
        String dn = sharedPref.getString("devicevalue", "");
        String ms = sharedPref.getString("msgvalue", "");
        String query = ms;
        try {
            query = URLEncoder.encode(ms,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Main2Activity.Message = ms;
        Main2Activity.Phone = phone;
        String link = "http://show8258.ipdisk.co.kr:8000/setting_change.php?ID="+id+"&P_NUM="+phone1+"&CALL_NUM="+phone+"&ADDRESS="+addr+"&DATE_OF_BIRTH="+bir+"&PW="+pw+"&DEVICE="+dn+"&MESSAGE="+query;
        task1 = new phpdo1();
        task1.execute(link);

    }

    private class phpdo extends AsyncTask<String,Void,String> {

        protected void onPreExecute(){

        }
        @Override
        protected String doInBackground(String... arg0) {

            try {
                String id = (String)arg0[0];

                String link = "http://show8258.ipdisk.co.kr:8000/setting_userlist.php?ID="+id;
                URL url = new URL(link);
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(link));
                HttpResponse response = client.execute(request);
                BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                    break;
                }
                in.close();
                return sb.toString();
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }

        }

        @Override
        protected void onPostExecute(String result){
            //txtview.setText("Login Successful");
            StringTokenizer st = new StringTokenizer(result);
            sharedPref = PreferenceManager
                    .getDefaultSharedPreferences(getApplicationContext());

            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("pwvalue",st.nextToken(","));
            editor.putString("phonevalue",st.nextToken(","));
            editor.putString("homevalue",st.nextToken(","));
            editor.putString("birthvalue",st.nextToken(","));
            editor.putString("partnerphonevalue",st.nextToken(","));
            editor.putString("devicevalue",st.nextToken(","));
            editor.putString("msgvalue",st.nextToken(","));
            editor.putString("namevalue",st.nextToken(","));

            editor.commit();

            getFragmentManager()
                    .beginTransaction()
                    .replace(android.R.id.content, new GeneralPreferenceFragment())
                    .commit();


        }
    }

    private class phpdo1 extends AsyncTask<String,Void,String> {

        protected void onPreExecute(){

        }
        @Override
        protected String doInBackground(String... arg0) {

            try {
                String ur = (String)arg0[0];


                String link = ur;
                URL url = new URL(link);
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(link));
                HttpResponse response = client.execute(request);
                BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                    break;
                }
                in.close();
                return sb.toString();
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }

        }

        @Override
        protected void onPostExecute(String result){
//            //txtview.setText("Login Successful");
//            StringTokenizer st = new StringTokenizer(result," ");
//            sharedPref = PreferenceManager
//                    .getDefaultSharedPreferences(getApplicationContext());
//
//            SharedPreferences.Editor editor = sharedPref.edit();
//            editor.putString("pwvalue",st.nextToken());
//            editor.putString("phonevalue",st.nextToken());
//            editor.putString("homevalue",st.nextToken());
//            editor.putString("birthvalue",st.nextToken());
//            editor.putString("partnerphonevalue",st.nextToken());
//            editor.putString("devicevalue",st.nextToken());
//            editor.putString("namevalue",st.nextToken());
//            editor.commit();
//
//            getFragmentManager()
//                    .beginTransaction()
//                    .replace(android.R.id.content, new GeneralPreferenceFragment())
//                    .commit();


        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class GeneralPreferenceFragment extends PreferenceFragment
    {
        @Override
        public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.setting_xml);
            setHasOptionsMenu(true);

            // Bind the summaries of EditText/List/Dialog/Ringtone preferences
            // to their values. When their values change, their summaries are
            // updated to reflect the new value, per the Android Design
            // guidelines.

            sharedPref = PreferenceManager
                    .getDefaultSharedPreferences(getActivity());

            SharedPreferences.Editor editor = sharedPref.edit();
            // editor.putInt("mode", 1);
            Intent intent = getActivity().getIntent();
            editor.putString("idvalue", Main2Activity.id); //키값, 저장값
            //editor.putString("namevalue", "name");
            // editor.putString("phonevalue", "010");
            editor.apply();

            bindPreferenceSummaryToValue(findPreference("namevalue"));
            bindPreferenceSummaryToValue(findPreference("idvalue"));
            bindPreferenceSummaryToValue(findPreference("partnerphonevalue"));
            bindPreferenceSummaryToValue(findPreference("homevalue"));
            bindPreferenceSummaryToValue(findPreference("phonevalue"));
            bindPreferenceSummaryToValue(findPreference("birthvalue"));
            bindPreferenceSummaryToValue(findPreference("pwvalue"));
            bindPreferenceSummaryToValue(findPreference("devicevalue"));
            bindPreferenceSummaryToValue(findPreference("msgvalue"));


            //Toast.makeText(getActivity(), sharedPref.getString("phonevalue", ""), Toast.LENGTH_SHORT).show();


        }
    }
}

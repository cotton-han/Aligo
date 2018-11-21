package com.example.ds.aligo;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.RingtonePreference;
import android.preference.SwitchPreference;
import android.text.TextUtils;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class SettingsFragment extends PreferenceFragment {
    RingtonePreference ringtone;
    SwitchPreference location;
    SwitchPreference vibrate;
    SwitchPreference alarm;

    Context context;
    GpsInfo gpsInfo;
    Intent intent2;
    String str="";

    public void setContext(Context context){this.context = context;}


    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);

        intent2 = new Intent(context, MyService.class);

        ringtone = (RingtonePreference) findPreference("ringtone");
        ringtone.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                str = newValue.toString();
                if (preference instanceof RingtonePreference) {
                    if (TextUtils.isEmpty(str))
                        preference.setSummary("무음으로 설정됨");
                    else {
                        Ringtone ringtone = RingtoneManager.getRingtone(preference.getContext(), Uri.parse(str));
                        if (ringtone == null)
                            preference.setSummary(null);
                        else {
                            String name = ringtone.getTitle(preference.getContext());
                            preference.setSummary(name);
                        }
                    }
                }
                return false;
            }
        });

        alarm = (SwitchPreference)findPreference("alarm");
        alarm.setChecked(true);
        alarm.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                if(alarm.isChecked()==false)
                    context.stopService(intent2);
                else if(alarm.isChecked()&&location.isChecked())
                    context.startService(intent2);
                return false;
            }
        });

        vibrate = (SwitchPreference)findPreference("vibrate");
        vibrate.setChecked(false);
        vibrate.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                if(vibrate.isChecked()){
                    Vibrator vibrator = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
                    vibrator.vibrate(500);
                }
                return false;
            }
        });

        gpsInfo = new GpsInfo(context);

        location = (SwitchPreference)findPreference("location");
        location.setChecked(false);
        location.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                if(location.isChecked()) {
                    Intent intent = new Intent(context, GpsInfo.class);
                    context.startService(intent);
                    String address = getAddress(context, gpsInfo.getLatitude(), gpsInfo.getLongitude());
                    //Toast.makeText(context, address, Toast.LENGTH_LONG).show();

                    //이미 그지점에있다고 가정
                    if(alarm.isChecked()){
                            intent2.putExtra("ringtone",str);
                            if(vibrate.isChecked())
                                intent2.putExtra("vibrate",true);
                            else
                                intent2.putExtra("vibrate",false);
                            context.startService(intent2);
                    }
                }
                else{
                    context.stopService(intent2);
                }
                return false;
        }
        });
    }
    public static String getAddress(Context context, double lat,  double lon) {
        String nowAddress ="현재 위치를 확인 할 수 없습니다.";
        Geocoder geocoder = new Geocoder(context, Locale.KOREA);
        List <Address> address;
        try {
            if (geocoder != null) {
                //세번째 파라미터는 좌표에 대해 주소를 리턴 받는 갯수로
                //한좌표에 대해 두개이상의 이름이 존재할수있기에 주소배열을 리턴받기 위해 최대갯수 설정
                address = geocoder.getFromLocation(lat, lon, 1);

                if (address != null && address.size() > 0) {
                    // 주소 받아오기
                    String currentLocationAddress = address.get(0).getAddressLine(0).toString();
                    nowAddress  = currentLocationAddress;
                }
            }

        } catch (IOException e) {
            Toast.makeText(context, "주소를 가져 올 수 없습니다.", Toast.LENGTH_LONG).show();

            e.printStackTrace();
        }
        return nowAddress;
        }

}
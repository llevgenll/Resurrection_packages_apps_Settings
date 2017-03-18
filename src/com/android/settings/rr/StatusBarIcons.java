/*Copyright (C) 2015 The ResurrectionRemix Project
     Licensed under the Apache License, Version 2.0 (the "License");
     you may not use this file except in compliance with the License.
     You may obtain a copy of the License at

          http://www.apache.org/licenses/LICENSE-2.0

     Unless required by applicable law or agreed to in writing, software
     distributed under the License is distributed on an "AS IS" BASIS,
     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
     See the License for the specific language governing permissions and
     limitations under the License.
*/
package com.android.settings.rr;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceScreen;
import android.telephony.TelephonyManager;
import android.support.v14.preference.SwitchPreference;

import com.android.ims.ImsConfig;
import com.android.ims.ImsException;
import com.android.ims.ImsManager;

import com.android.internal.telephony.Phone;
import com.android.internal.telephony.PhoneConstants;
import com.android.internal.telephony.PhoneFactory;

import com.android.internal.logging.MetricsProto.MetricsEvent;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;

public class StatusBarIcons extends SettingsPreferenceFragment implements
        Preference.OnPreferenceChangeListener {
    private static final String TAG = "StatusBarIcons";
    private static final String VOLTE_SWITCH = "volte_icon_enabled";
    private TelephonyManager mTelephonyManager;
    private ImsManager mImsManager = null;
    private Phone phone = null;

    private SwitchPreference mVolteSwitch;

    @Override
    protected int getMetricsCategory() {
        return MetricsEvent.RESURRECTED;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.rr_sb_icons);

        int number = TelephonyManager.getDefault().getPhoneCount();

        mVolteSwitch = (SwitchPreference) findPreference(VOLTE_SWITCH);

        if (mVolteSwitch != null) {
            if (!isImsVolteProvisioned()) {
                getPreferenceScreen().removePreference(mVolteSwitch);
            }
        }
    }

    private boolean isImsVolteProvisioned() {
        phone = PhoneFactory.getDefaultPhone();
        if (phone != null && mImsManager != null) {
            return mImsManager.isVolteEnabledByPlatform(phone.getContext())
                && mImsManager.isVolteProvisionedOnDevice(phone.getContext());
        }
        return false;
    }


    public boolean onPreferenceChange(Preference preference, Object objValue) 		{
        return true;
    }
}

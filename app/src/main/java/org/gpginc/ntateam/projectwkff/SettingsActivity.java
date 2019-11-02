package org.gpginc.ntateam.projectwkff;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.preference.CheckBoxPreference;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.PreferenceScreen;
import androidx.preference.SwitchPreferenceCompat;

import org.gpginc.ntateam.projectwkff.runtime.Main;
import org.gpginc.ntateam.projectwkff.runtime.events.DefeatSupreme;
import org.gpginc.ntateam.projectwkff.runtime.util.enums.Rarity;
import org.gpginc.ntateam.projectwkff.ui.widget.dialogs.MessageDialog;

import java.util.Locale;

public class SettingsActivity extends BaseAppActivity implements
        PreferenceFragmentCompat.OnPreferenceStartFragmentCallback {

    private static final String TITLE_TAG = "settingsActivityTitle";

    private final Preference.OnPreferenceChangeListener updater = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String value = newValue.toString();
            preference.setSummary(value);

            return true;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(darkTheme ? R.style.AppTheme_Dark : R.style.AppTheme);
        setContentView(R.layout.settings_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new HeaderFragment())
                    .commit();
        } else {
            setTitle(savedInstanceState.getCharSequence(TITLE_TAG));
        }
        getSupportFragmentManager().addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener() {
                    @Override
                    public void onBackStackChanged() {
                        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                            setTitle(R.string.title_activity_settings);
                        }
                    }
                });
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.show();
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save current activity title so we can set it again after a configuration change
        outState.putCharSequence(TITLE_TAG, getTitle());
    }

    @Override
    public boolean onSupportNavigateUp() {
        if (getSupportFragmentManager().popBackStackImmediate()) {
            return true;
        }
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onPreferenceStartFragment(PreferenceFragmentCompat caller, Preference pref) {
        // Instantiate the new Fragment
        final Bundle args = pref.getExtras();
        final Fragment fragment = getSupportFragmentManager().getFragmentFactory().instantiate(
                getClassLoader(),
                pref.getFragment());
        fragment.setArguments(args);
        fragment.setTargetFragment(caller, 0);
        // Replace the existing Fragment with the new Fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.settings, fragment)
                .addToBackStack(null)
                .commit();
        setTitle(pref.getTitle());
        return true;
    }

    protected void updateSetting(Preference pref)
    {
        pref.setOnPreferenceChangeListener(updater);
        pref.getOnPreferenceChangeListener().onPreferenceChange(pref,
                PreferenceManager
                        .getDefaultSharedPreferences(pref.getContext())
                        .getString(pref.getKey(), ""));
    }
    public static class HeaderFragment extends PreferenceFragmentCompat {

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.header_preferences, rootKey);
        }
    }

    public static class MessagesFragment extends PreferenceFragmentCompat {

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.messages_preferences, rootKey);
        }
    }

    public static class ClazzPreferFrag extends PreferenceFragmentCompat {

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.clazz_preferences, rootKey);
        }
    }
    public static class LocalPlayersPreferFrag extends PreferenceFragmentCompat
    {
        @Override
        public void onCreatePreferences(final Bundle savedInstanceState, final String rootKey) {
            Context context = getPreferenceManager().getContext();
            final PreferenceScreen screen = getPreferenceManager().createPreferenceScreen(context);

            final SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
            for(int i = 0; i < pref.getInt("players_size", 4); ++i)
            {

                final String namep = (pref.getString("PLAYER " + i, "Player Name"));
                if(namep!="Player Name") {
                    final String key = "PLAYER " + i;

                    EditTextPreference name = new EditTextPreference(context);
                    name.setText(namep);
                    name.setTitle(R.string.set_edit_player_name);
                    name.setDialogTitle(R.string.set_dialog_change_player_name);
                    name.setKey("PLAYER " + i);
                    name.setIconSpaceReserved(false);

                    Preference del = new Preference(context);
                    del.setTitle(R.string.set_delete_player);
                    del.setIcon(R.drawable.ic_delete);
                    del.setOnPreferenceClickListener(preference -> {
                        pref.edit().remove(key).apply();
                        new MessageDialog.Display().directPrompt(getFragmentManager(), R.string.set_player_deleted);
                        return true;
                    });

                    CheckBoxPreference isDev = new CheckBoxPreference(context);
                    isDev.setKey(key + "is_dev");
                    isDev.setTitle("dev -> {}");

                    PreferenceCategory category = new PreferenceCategory(context);
                    category.setKey("CATEGORY PLAYER " + i);
                    category.setTitle(namep);
                    screen.addPreference(category);
                    category.addPreference(name);
                    category.addPreference(isDev);
                    category.addPreference(del);
                    category.setIconSpaceReserved(false);
                }
            }
            setPreferenceScreen(screen);
        }
    }
    public static class EventPreferences extends PreferenceFragmentCompat
    {
        private static boolean changeRarityTitle(Preference preference, Object newValue) {
            String lastTitle = (String) preference.getTitle();
            if(lastTitle.length() > preference.getContext().getResources().getString(R.string.set_label_evtrarity).length())
            {
                lastTitle = preference.getContext().getResources().getString(R.string.set_label_evtrarity);
            }
            String nTitle = (String) newValue;
            preference.setTitle(lastTitle + " "+ nTitle);
            return true;
        }

        @Override
        public void onCreatePreferences(final Bundle savedInstanceState, final String rootKey) {
            Context context = getPreferenceManager().getContext();
            final PreferenceScreen screen = getPreferenceManager().createPreferenceScreen(context);
            setPreferenceScreen(screen);
            final SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
            SwitchPreferenceCompat defEvt = new SwitchPreferenceCompat(context);
            defEvt.setTitle(R.string.set_def_evts);
            defEvt.setKey("default_events");
            defEvt.setChecked(pref.getBoolean("default_events", false));
            defEvt.setIcon(R.drawable.ic_config);
            screen.addPreference(defEvt);

            Main.EVENT_LIST.stream().filter(event -> !(event instanceof DefeatSupreme)).forEach(event -> {
                String evtKey = "EVTLOADER_" + event.getNameLikeStr(getResources());
                PreferenceCategory cCategory = new PreferenceCategory(context);
                cCategory.setTitle(event.getNameLikeStr(getResources()));
                cCategory.setIconSpaceReserved(false);
                screen.addPreference(cCategory);

                SwitchPreferenceCompat evt = new SwitchPreferenceCompat(context);
                evt.setTitle(R.string.label_enabled);
                evt.setKey(evtKey);
                evt.setChecked(pref.getBoolean(evtKey, true));
                cCategory.addPreference(evt);
                evt.setDependency("default_events");
                evt.setIconSpaceReserved(false);

                ListPreference rarity = new ListPreference(context);
                rarity.setTitle(R.string.set_label_evtrarity);
                rarity.setIconSpaceReserved(false);

                CharSequence[] rnames = new CharSequence[Rarity.RARITIES.length];
                for (int i = 0; i < Rarity.RARITIES.length; i++) {
                    rnames[i] = Rarity.RARITIES[i].name();
                }
                rarity.setEntries(rnames);
                rarity.setEntryValues(rnames);
                rarity.setKey(evtKey+"_Rarity=");
                rarity.setValue(pref.getString(evtKey+"_Rarity=", event.getDefaultRarity()));
                changeRarityTitle(rarity, rarity.getValue());
                rarity.setOnPreferenceChangeListener(EventPreferences::changeRarityTitle);
                cCategory.addPreference(rarity);
                rarity.setDependency("default_events");
            });

        }
    }
}

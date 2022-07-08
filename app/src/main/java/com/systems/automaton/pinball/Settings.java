package com.systems.automaton.pinball;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.systems.automaton.pinball.BuildConfig;
import com.systems.automaton.pinball.databinding.ActivityMainBinding;
import com.systems.automaton.pinball.databinding.ActivitySettingsBinding;

import org.w3c.dom.Text;

public class Settings extends AppCompatActivity {

    private ActivitySettingsBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivitySettingsBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);

        int score = getSharedPreferences("com.systems.automaton.pinball", Context.MODE_PRIVATE).getInt("highscore", 0);
        String txt = score + "";
        mBinding.highscoretxtv.setText(txt);

        txt = "Version " + BuildConfig.VERSION_NAME + " (" + BuildConfig.VERSION_CODE + ")";
        mBinding.verstxtv.setText(txt);

        boolean tiltenabled = getSharedPreferences("com.systems.automaton.pinball", Context.MODE_PRIVATE).getBoolean("tiltbuttons", true);
        mBinding.tiltbtns.setChecked(tiltenabled);
        mBinding.tiltbtns.setOnCheckedChangeListener((compoundButton, b) -> {
            getSharedPreferences("com.systems.automaton.pinball", Context.MODE_PRIVATE).edit().putBoolean("tiltbuttons", b).apply();
        });

        boolean customfonts = getSharedPreferences("com.systems.automaton.pinball", Context.MODE_PRIVATE).getBoolean("customfonts", true);
        mBinding.cstmfnts.setChecked(customfonts);
        mBinding.cstmfnts.setOnCheckedChangeListener((compoundButton, b) -> {
            getSharedPreferences("com.systems.automaton.pinball", Context.MODE_PRIVATE).edit().putBoolean("customfonts", b).apply();
        });

        mBinding.inpttxtusername.setText(getSharedPreferences("com.systems.automaton.pinball", Context.MODE_PRIVATE).getString("username", "Player 1"));
        mBinding.inpttxtusername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                getSharedPreferences("com.systems.automaton.pinball", Context.MODE_PRIVATE).edit().putString("username", charSequence.toString()).apply();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mBinding.source.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/automaton82/classic-pinball-space-pilot"));
            startActivity(browserIntent);
        });

        mBinding.volumebar.setProgress(getSharedPreferences("com.systems.automaton.pinball", Context.MODE_PRIVATE).getInt("volume", 100));
        mBinding.volumebar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int percentage, boolean b) {
                getSharedPreferences("com.systems.automaton.pinball", Context.MODE_PRIVATE).edit().putInt("volume", percentage).apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }
}
package io.appsfly.appsflyintegrationsamples;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.json.JSONObject;

import java.util.ArrayList;

import io.appsfly.core.models.AppsFlyClientConfig;
import io.appsfly.core.providers.AppsFlyProvider;
import io.appsfly.core.services.Callback;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnSync = (Button) findViewById(R.id._btn_sync);
        Button btnLaunch = (Button) findViewById(R.id._btn_launch);

        btnSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                syncApp();
            }
        });

        btnLaunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTestMicroApp();
            }
        });
    }

    private void syncApp() {
        ArrayList<AppsFlyClientConfig> appsFlyClientConfigs = new ArrayList<AppsFlyClientConfig>() {{
            this.add(new AppsFlyClientConfig(getString(R.string.microapp_id), getString(R.string.app_key), getString(R.string.execution_url)));
        }};
        AppsFlyProvider.getInstance().syncModules(appsFlyClientConfigs, new Callback<Void>() {
            @Override
            public void send(Void finalClient) {

            }
        });

    }

    private void openTestMicroApp() {
        AppsFlyProvider.getInstance().pushApp(getString(R.string.microapp_id), getString(R.string.app_key), getString(R.string.intent_string), new JSONObject(), this);
    }


}

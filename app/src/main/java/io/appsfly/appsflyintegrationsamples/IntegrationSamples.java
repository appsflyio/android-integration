package io.appsfly.appsflyintegrationsamples;

import android.app.Application;

import java.util.ArrayList;

import io.appsfly.core.models.AppsFlyClientConfig;
import io.appsfly.core.providers.AppsFlyProvider;

/**
 * Created by prateek on 19/10/17.
 */

public class IntegrationSamples extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ArrayList<AppsFlyClientConfig> appsFlyClientConfigs = new ArrayList<AppsFlyClientConfig>() {{
            //Find this out
            this.add(new AppsFlyClientConfig(getString(R.string.microapp_id), getString(R.string.app_key), getString(R.string.execution_url)));
        }};

        AppsFlyProvider.getInstance().initialize(appsFlyClientConfigs, this);
    }
}

# How to integrate the Appsfly Android Utils

### Step 1
#### Add artifactory maven repository:
Add the following to the root gradle file.

```
allprojects {
	repositories {
		...
		maven { url "https://repos.appsfly.io/artifactory/libs-release-local" }
	}
}
```
### Step 2
#### Add the gradle dependency
```
implementation ('io.appsfly.android.utils:micro-app:1.2.15'){
	transitive = true
}
```
> Note: If you are using a lower version of Android Studio, use 'compile' instead of 'implementation'

### Step 3
#### Generated Secret-key

Obtain a unique secret key from Appsfly.io (xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx). To keep the secret key secure, it is recommended to follow the below steps.

1. Place this key in the gradle.properties file of the Android Project.

    `appsfly_app_key=xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx`

2. Add the following config to the manifest placeholders.

```
defaultConfig {
    ...
    manifestPlaceholders.appsfly_app_key = "${appsfly_app_key}"
}
```

3. Add the following to the application module Manifest file.

```
<application
    android:name=".MyApplication"
    ...
    android:label="My Application">

    <meta-data
        android:name="appsfly_app_key"
        android:value="${appsfly_app_key}" />
    ...
/>
```

### Step 3

#### Initialize runtime with MicroApp configurations

Override your Application/Activity Instance onCreate() method 

```
@Override
public void onCreate() {
	super.onCreate();

	ArrayList<AppsFlyClientConfig> appsFlyClientConfigs = new ArrayList<AppsFlyClientConfig>();
	AppsFlyClientConfig appsflyConfig = new AppsFlyClientConfig(context, "MICRO_MODULE_HANDLE", "EXECUTION_URL");
	appsFlyClientConfigs.add(appsflyConfig);
	AppsFlyProvider.getInstance().initialize(appsFlyClientConfigs, this);
}
```
This will start the process of syncing Metadata required to run MicroApp in your application.

### Step 4

#### Fly in MicroApp into context of user

To launch the MicroApp, run the following snippet where there is a call to action.

```
MicroAppLauncher.pushApp(*MICRO_MODULE_HANDLE*, *INTENT*, *new JSONObject(){INTENT_DATA}*, *ACTIVITY*);
```

___

# Data into and out of MicroApp

### To put data into the MicroApp:
```
//Put context data inside a JSONobject and pass it as intent data.
JSONObject contextData = new JSONObject();
data.put(*key* , *value*);
MicroAppLauncher.pushApp(*MICRO_MODULE_HANDLE*, *INTENT*, *new JSONObject(){INTENT_DATA}*, *ACTIVITY*);
```

> Note: Values and format accepted by the Microapp are specific to each Microapp and can be found in the respective Microapp documentation.

### To retrieve data from the MicroApp:
```
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	super.onActivityResult(requestCode, resultCode, data);
	if (resultCode == RESULT_OK && requestCode == AppsFlyConstants.NAVIGATION_CODE) {
		    String dataStr = data.getStringExtra("result");
		    JSONObject resultData;
		    try {
			resultData = new JSONObject(dataStr);
		    } catch (JSONException e) {
			e.printStackTrace();
		    }
		    //Get values from resultData with keys specified by the Microapp Documentation.
		    Object value1 = result.get(*key1*);
		    String value2 = result.getString(*key2*);
	}
}
```

> Note: Values returned by the Microapp are specific to each Microapp and can be found in the respective Microapp documentation.

___


Note: Contact the integrations@appsfly.io for any issues with integration.

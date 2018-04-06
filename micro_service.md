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
implementation ('io.appsfly.android.utils:core:1.2.22'){
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

AppsFlyClientConfig appsFlyClientConfig = new AppsFlyClientConfig(this, "MICRO_HANDLE", getString(R.string.repo_url));
  
}
```
This will start the process of syncing Metadata required to run MicroApp in your application.

### Step 4

#### Fly in MicroApp into context of user

To get the Data, run the following snippet.

```
   afMicroService = new AFMicroService(appsFlyClientConfig, this);
   afMicroService.execIntent("*INTENT_NAME*", *new JSONObject()[INTENT_DATA]*, new Callback<JSONObject>() {
            @Override
            public void send(JSONObject finalClient) {
                -----
            }
        });
```



Note: Contact the integrations@appsfly.io for any issues with integration.

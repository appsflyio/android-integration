# Here's how to integrate the Android SDK

### Step 1
#### Add jitpack.io maven repository:
Add the following to the root gradle file.

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

### Step 2
#### Add Core Library as a gradle dependency
    compile 'com.github.appsflyio.appsfly-runtime-android:core:0.0.21'

Core library has all the libraries to run Appsfly Plugins.

#### Add Microapp Library as a gradle dependency
    compile 'com.github.appsflyio.appsfly-runtime-android:micro-app:0.0.21'

This library has the dependency on Core Library. This will enable MicroApps to fly into the user's context in the publisher application.

> Note: If MicroApps are not used in the publisher application, skip Step 2.

### Step 3

#### Initialize runtime with MicroApp configurations

Override your Application/Activity3 Instance onCreate() method 

	@Override
	public void onCreate() {
		super.onCreate();

		ArrayList<AppsFlyClientConfig> appsFlyClientConfigs = new ArrayList<AppsFlyClientConfig>();
		AppsFlyClientConfig appsflyConfig = new AppsFlyClientConfig("MICRO_MODULE_HANDLE",  "APPLICATION_KEY", "EXECUTION_URL");
		appsFlyClientConfigs.add(appsflyConfig);
		AppsFlyProvider.getInstance().initialize(appsFlyClientConfigs, this);
	}

This will start the process of syncing Metadata required to run MicroApp in your application. 

> Note: **If you are using application class, do not forget to reference the class name in your manifest file.**
> Note: MICRO_MODULE_HANDLE & APPLICATION_KEY can be obtained from MicroApp Service Provider.


### Step 4

#### Fly in MicroApp into context of user

To launch the MicroApp, run the following snippet where there is a call to action.

	AppsFlyProvider.getInstance().pushApp("MICRO_MODULE_HANDLE", "APPLICATION_KEY", "INTENT", new JSONObject()*{}*, context);

> Note: This will create an overlay activity showing the MicroApp.

___

# Data into and out of MicroApp

### To put data into the MicroApp:
    
    //Put user context data inside a JSONobject and pass it as intent data.
    JSONObject userContextData = new JSONObject();
    data.put(*key* , *value*);
    AppsFlyProvider.getInstance().pushApp("MICRO_MODULE_HANDLE", "APPLICATION_KEY", "INTENT", userContextData, context);

### To retrieve data from the MicroApp:

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == AppsFlyProvider.REQUEST_CODE) {
            String dataStr = data.getStringExtra("result");
            JSONObject resultData;
            try {
                resultData = new JSONObject(dataStr);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Get values from resultData with keys specified by the MicroApp Service Provider .
            Object value1 = result.get(*key1*);
            String value2 = result.getString(*key2*);
        }
    }
___

# Production release for Core/MicroApp SDK:

The SDK available for download in this integration documentation is in a sandbox environment. Contact the integrations@appsfly.io to get a production build.

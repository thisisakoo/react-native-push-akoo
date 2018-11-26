
# react-native-push-akoo

## Getting started

`$ npm install react-native-push-akoo --save`

### Mostly automatic installation

`$ react-native link react-native-push-akoo`

### Manual installation



#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.reactlibrary.RNPushAkooPackage;` to the imports at the top of the file
  - Add `new RNPushAkooPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-push-akoo'
  	project(':react-native-push-akoo').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-push-akoo/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      implementation project(":react-native-push-akoo")
  	```
4. public class MainActivity extends ReactActivity {

    /**
     * Returns the name of the main component registered from JavaScript.
     * This is used to schedule rendering of the component.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		````
        String brand = Build.BRAND.toLowerCase();
        switch (brand) {
            case "huawei":
                RNPushAkooModule.registerPush(this);
                break;
            default:
                break;
        }
        `````            
    }
5. androidmenifest.xml
````
       <meta-data
            android:name="com.huawei.hms.client.appid"
        android:value="100499511">
`````


## Usage
```javascript

import { NativeAppEventEmitter,Alert  } from 'react-native';

NativeAppEventEmitter.addListener(
  'receiveRemoteNotification',
  (notification) => {
      console.log(notification);
          Alert.alert('消息通知',notification);
  }
);
NativeAppEventEmitter.addListener(
  'receiveClientId',
  (notification) => {
      console.log(notification);
          Alert.alert('receiveClientId',notification);
  }
);
```
  
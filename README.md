# AndroidAppForceUpdate
This project shows a dialog to user whenever there is an update available on the play store.

Add JSOUP Library

```implementation 'org.jsoup:jsoup:1.10.2'```

Initiate **ForceUpate** class in your **MainActivity**

```
ForceUpdate forceUpdate = new ForceUpdate(MainActivity.this);
        forceUpdate.setCanceledOnTouchOutside(false);
        forceUpdate.setTitle("New Update Available");
        forceUpdate.setMessage("Download this Update for New Features");
        forceUpdate.build();
```


Available Methods in this ForceUpdate Class.

```setCanceledOnTouchOutside(boolean canceledOnTouchOutside)```
Diaog dismiss whenever user touches the ui other than diaog.

```setCanceled(boolean canceled)```
Dialog will dismiss only if user taps any one of the button.

```setTitle(String title)```
Set custom title for the Update Dialog.

```setMessage(String message)```
Set Custom Message to show to the user.


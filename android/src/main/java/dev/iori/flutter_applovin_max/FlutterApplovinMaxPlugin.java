package dev.iori.flutter_applovin_max;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;

import com.applovin.sdk.AppLovinMediationProvider;
import com.applovin.sdk.AppLovinSdk;
import com.applovin.sdk.AppLovinSdkConfiguration;

import io.flutter.Log;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry;
import io.flutter.plugin.common.StandardMethodCodec;
import io.flutter.plugin.platform.PlatformViewRegistry;


public class FlutterApplovinMaxPlugin implements FlutterPlugin, MethodCallHandler, ActivityAware {
    private static FlutterApplovinMaxPlugin instance;
    private static RewardedVideo instanceReward;
    private static InterstitialVideo instanceInter;
    private static Context context;
    private static MethodChannel channel;
    public static Activity activity;

    public static FlutterApplovinMaxPlugin getInstance() {
        return instance;
    }

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        this.RegistrarBanner(flutterPluginBinding.getPlatformViewRegistry());
        this.onAttachedToEngine(flutterPluginBinding.getApplicationContext(), flutterPluginBinding.getBinaryMessenger());
    }

    public static void registerWith(PluginRegistry.Registrar registrar) {
        if (instance == null) {
            instance = new FlutterApplovinMaxPlugin();
        }
        instance.onAttachedToEngine(registrar.context(), registrar.messenger());
    }

    private void onAttachedToEngine(Context applicationContext, BinaryMessenger messenger) {
        if (channel != null) {
            return;
        }
        instance = new FlutterApplovinMaxPlugin();
        Log.i("AppLovin Plugin", "onAttachedToEngine");
        context = applicationContext;
        channel = new MethodChannel(messenger, "flutter_applovin_max", StandardMethodCodec.INSTANCE);
        channel.setMethodCallHandler(this);
    }

    public FlutterApplovinMaxPlugin() {
    }

    private void RegistrarBanner(PlatformViewRegistry registry) {
        registry.registerViewFactory("/Banner", new BannerFactory());
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
        try {
            switch (call.method) {
                /* Reward */
                case "InitSdk":
                    AppLovinSdk.getInstance(activity).setMediationProvider(AppLovinMediationProvider.MAX);
                    AppLovinSdk.initializeSdk(activity, new AppLovinSdk.SdkInitializationListener() {
                        @Override
                        public void onSdkInitialized(final AppLovinSdkConfiguration configuration) {
                        }
                    });
                    break;
                case "InitRewardAd":
                    String unitId = call.argument("UnitId").toString();
                    instanceReward.Init(unitId);
                    result.success(Boolean.TRUE);
                    break;
                case "ShowRewardVideo":
                    instanceReward.Show();
                    result.success(Boolean.TRUE);
                    break;
                case "IsRewardLoaded":
                    Boolean isLoaded = instanceReward.IsLoaded();
                    result.success(isLoaded);
                    break;
                /* Inter */
                case "InitInterAd":
                    instanceInter.Init(call.argument("UnitId").toString());
                    result.success(Boolean.TRUE);
                    break;
                case "ShowInterVideo":
                    instanceInter.Show();
                    result.success(Boolean.TRUE);
                    break;
                case "IsInterLoaded":
                    result.success(instanceInter.IsLoaded());
                    break;
                default:
                    result.notImplemented();
            }
        } catch (Exception err) {
            Log.e("Method error", err.toString());
            result.notImplemented();
        }
    }

    static public void Callback(final String method) {
        if (context != null && channel != null && activity != null) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    channel.invokeMethod(method, null);
                }
            });
        } else {
            Log.e("AppLovin", "instance method channel not created");
        }
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        context = null;
        if (channel != null) {
            channel.setMethodCallHandler(null);
            channel = null;
        }
    }

    @Override
    public void onAttachedToActivity(ActivityPluginBinding binding) {
        activity = binding.getActivity();
        instanceReward = new RewardedVideo();
        instanceInter = new InterstitialVideo();
        Log.i("AppLovin Plugin", "Instances created");
    }

    @Override
    public void onDetachedFromActivityForConfigChanges() {
    }

    @Override
    public void onReattachedToActivityForConfigChanges(ActivityPluginBinding binding) {
        activity = binding.getActivity();
    }

    @Override
    public void onDetachedFromActivity() {
    }
}

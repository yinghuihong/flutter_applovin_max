import 'package:flutter/material.dart';
import 'package:flutter_applovin_max/banner.dart';
import 'package:flutter_applovin_max/flutter_applovin_max.dart';

class HomeScreen extends StatefulWidget {
  @override
  _HomeScreenState createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {
  @override
  void initState() {
    // FlutterApplovinMax.initRewardAd('a4b532666ee0c541');
    // FlutterApplovinMax.initInterstitialAd('b71f63292bf4b3bc');
    super.initState();
  }

  /// adLoaded => adDisplayed => adHidden
  void listener(AppLovinAdListener event) {
    print('xxx event $event');
    // if (event == AppLovinAdListener.onUserRewarded) {
    //   print('xxx 👍get reward');
    // }

    switch (event) {
      case AppLovinAdListener.adLoaded:
        break;
      case AppLovinAdListener.adLoadFailed:
        break;
      case AppLovinAdListener.adDisplayed:
        break;
      case AppLovinAdListener.adHidden:
        break;
      case AppLovinAdListener.adClicked:
        break;
      case AppLovinAdListener.onAdDisplayFailed:
        break;
      case AppLovinAdListener.onRewardedVideoStarted:
        break;
      case AppLovinAdListener.onRewardedVideoCompleted:
        break;
      case AppLovinAdListener.onUserRewarded:
        break;
    }
  }

  bool isRewardedVideoAvailable = false;
  bool isInterstitialVideoAvailable = false;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Plugin example app'),
      ),
      body: Center(
          child: Column(
        children: [
          const Spacer(),
          // TextButton(
          //   onPressed: () async {
          //     isRewardedVideoAvailable =
          //         await FlutterApplovinMax.isRewardLoaded(listener);
          //     if (isRewardedVideoAvailable) {
          //       FlutterApplovinMax.showRewardVideo(
          //           (AppLovinAdListener event) => listener(event));
          //     }
          //   },
          //   child: const Text('Show Reward Video'),
          // ),
          TextButton(
            onPressed: () async {
              isInterstitialVideoAvailable =
                  await FlutterApplovinMax.isInterstitialLoaded(listener);
              if (isInterstitialVideoAvailable) {
                FlutterApplovinMax.showInterstitialVideo(
                    (AppLovinAdListener event) => listener(event));
              }
            },
            child: const Text('Show Interstitial Video'),
          ),
          const Spacer(),
        ],
      )),
      // bottomNavigationBar: BannerMaxView(
      //   (AppLovinAdListener event) => print(event),
      //   BannerAdSize.banner,
      //   "b48ba98c1e25a926",
      // ),
    );
  }
}

import 'package:flutter/material.dart';
import 'package:flutter_applovin_max/flutter_applovin_max.dart';
import 'package:flutter_applovin_max_example/splash.dart';

void main() {
  FlutterApplovinMax.initSDK();
  runApp(MyApp());
}


class MyApp extends StatefulWidget{
  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {

  @override
  void initState() {
    FlutterApplovinMax.initInterstitialAd('b71f63292bf4b3bc'); // maxInterstitialStartup
    FlutterApplovinMax.initInterstitialAd('d78dc653ca733d24'); // maxInterstitialWelcomeStart
    super.initState();
  }
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: SplashScreen(),
    );
  }
}

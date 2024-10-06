plugins {
    id("com.android.application")
    id("androidx.navigation.safeargs")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.foodplannerapplication"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.foodplannerapplication"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")


    //navigation
    implementation("androidx.navigation:navigation-fragment:2.5.3")
    implementation("androidx.navigation:navigation-ui:2.5.3")

    //room
    implementation("androidx.room:room-runtime:2.5.2")
    annotationProcessor("androidx.room:room-compiler:2.5.2")

    //retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    //glide
    implementation("com.github.bumptech.glide:glide:4.12.0")

    //circle imageview
    implementation("de.hdodenhof:circleimageview:3.1.0")

    //lottie
    implementation("com.airbnb.android:lottie:6.1.0")

    //rxJava
    implementation ("io.reactivex.rxjava3:rxandroid:3.0.2")
    implementation ("io.reactivex.rxjava3:rxjava:3.1.6")
    implementation("com.github.akarnokd:rxjava3-retrofit-adapter:3.0.0")
    implementation("androidx.room:room-rxjava3:2.5.2")

    //youtube player
    implementation ("com.pierfrancescosoffritti.androidyoutubeplayer:core:12.0.0")

    // TFLite library for Android
    implementation ("org.tensorflow:tensorflow-lite:2.9.0")

// TFLite support for GPU acceleration (optional but recommended for faster inference)
    implementation ("org.tensorflow:tensorflow-lite-gpu:2.9.0")

// OpenCV for image pre-processing (optional)
    //implementation ("org.opencv:opencv-android:4.5.3")

// CameraX for capturing frames from the camera (optional)
//    implementation ("androidx.camera:camera-core:1.0.2")
//    implementation ("androidx.camera:camera-camera2:1.0.2")
//    implementation ("androidx.camera:camera-lifecycle:1.0.2")
//    implementation ("androidx.camera:camera-view:1.0.2")

// For managing threading and background tasks
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
}
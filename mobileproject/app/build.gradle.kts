plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    // Add the Google services Gradle plugin
    id("com.google.gms.google-services")
    //import for parcel in class @Parcelize
    id("kotlin-parcelize")
}

android {
    namespace = "com.example.mobileproject"
    compileSdk = 34
    buildFeatures {
        viewBinding = true
    }
    defaultConfig {
        applicationId = "com.example.mobileproject"
        minSdk = 24
        targetSdk = 34
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.4.0")
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-database:20.3.0")
    testImplementation("junit:junit:4.13.2")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    // For control over item selection of both touch and mouse driven selection
    implementation("androidx.recyclerview:recyclerview-selection:1.1.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // ------------------------------------------------------------------------
    // initialize the firebase in android
    // Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:32.7.0"))
    // When using the BoM, don't specify versions in Firebase dependencies
    implementation("com.google.firebase:firebase-analytics")
    // Add the dependencies for any other desired Firebase products
    // https://firebase.google.com/docs/android/setup#available-libraries
    // ------------------------------------------------------------------------
    //email and password for kotlin
    //https://firebase.google.com/docs/auth/android/password-auth?hl=zh-cn
    // Import the BoM for the Firebase platform
    implementation(platform("com.google.firebase:firebase-bom:32.3.1"))

    // Add the dependency for the Firebase Authentication library
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation("com.google.firebase:firebase-auth-ktx")

    // ------------------------------------------------------------------------
    // initialize the FireStore
    // Import the BoM for the Firebase platform
    implementation(platform("com.google.firebase:firebase-bom:32.7.0"))

    // Declare the dependency for the Cloud Firestore library
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation("com.google.firebase:firebase-firestore")

    // ------------------------------------------------------------------------
    // initialize the reaktime database firestore
    // Import the BoM for the Firebase platform
    implementation(platform("com.google.firebase:firebase-bom:32.3.1"))

    // Add the dependency for the Realtime Database library
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation("com.google.firebase:firebase-database-ktx")

//
    implementation ("com.razorpay:checkout:1.6.33")

    //Variant: debugAndroidTest
    //Config: debug
    //Store: C:\Users\HP\.android\debug.keystore
    //Alias: AndroidDebugKey
    //MD5: FA:96:C9:80:9D:81:96:16:5D:3B:B0:82:84:4C:C8:C2
    //SHA1: 03:70:71:B0:60:27:58:3F:E9:C6:E8:89:DB:E1:1D:28:35:B8:75:69
    //SHA-256: 4E:82:27:06:97:3D:D1:54:6A:4A:6D:56:FE:63:CB:F9:70:10:C8:50:50:1B:C2:12:97:77:50:B1:BC:23:06:31
    //Valid until: Wednesday, October 15, 2053
}
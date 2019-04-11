# NYTimes-master
A simple android app with implementation NY times API

<img src="https://lh3.googleusercontent.com/-EJsInu_Opg4/XK7xEHaaPbI/AAAAAAAAVZc/SEA690MfbtkGjEfXScbY4jDbmEUirMXrQCK8BGAs/s0/2019-04-11.jpg" width="200">


API : http://api.nytimes.com/svc/mostpopular/v2/mostviewed/all-sections/7.json?apikey=sample-key

Create you key in https://developer.nytimes.com



Dependencies

    implementation 'com.android.support:appcompat-v7:28.0.0'
    implementation 'com.android.support.constraint:constraint-layout:1.1.3'
    implementation 'android.arch.lifecycle:extensions:1.1.1'

    implementation 'com.android.support:design:28.0.0'

    //Glide for Image
    implementation 'com.github.bumptech.glide:glide:4.7.1'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.7.1'
    
    //Circular imageview
    implementation 'de.hdodenhof:circleimageview:2.2.0'

    // retrofit, gson, scalars,logger
    implementation 'com.google.code.gson:gson:2.6.2'
    implementation 'com.squareup.retrofit2:retrofit:2.0.2'
    implementation 'com.squareup.retrofit2:converter-gson:2.0.2'
    implementation 'com.squareup.retrofit2:converter-scalars:2.1.0'
    implementation 'com.squareup.okhttp3:logging-interceptor:3.8.0'

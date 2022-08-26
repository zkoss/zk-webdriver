The ZK Webdriver utility library.
-----


### How to specify ContextPath in System Property
For example in Gradle,
```gradle
test {
    systemProperty "zkWebdriverContextPath", "/zephyr-test"
}
```

### How to specify TestURLPackage in System Property
For example in Gradle,
```gradle
test {
    systemProperty "zkWebdriverTestURLPackage", "org.zkoss.zephyr.webdriver"
}
```

### How to specify a fix Jetty Port in System Property
For example in Gradle,
```gradle
test {
    systemProperty "jetty.port", "8888"
}
```

### How to release
1. Update the project `version` in `gradle.properties`
2. Run `./gradlew clean build bundleJar`
3. Upload zk-webdriver-VERSION-bundle.jar from `build/libs`
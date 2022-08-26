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
1. Check the project `version` in `gradle.properties`
2. Run `./gradlew clean build bundleJar`
3. Upload zk-webdriver-VERSION-bundle.jar from `build/libs`


### How to upgrade chrome version
1. Search the text "Chromium" from https://github.com/puppeteer/puppeteer/releases
2. Update the `version` and `chromeVersion` to Chromium version in `gradle.properties`
3. Update the `chromeBinaryRevision` to the revision `r10.....` in `gradle.properties`
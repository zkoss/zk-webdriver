The ZK Webdriver utility library.
-----

### Support Version
* JDK 11+
* Servlet 3 (with Jetty v10+)

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

### How to specify Webapp Base Resource in System Property
For example in Gradle,
```gradle
test {
    systemProperty "zkWebdriverBaseResource", "./test/webapp/"
}
```
Note: the default value is `"./src/main/webapp/"`

### How to specify a fix Jetty Port in System Property
For example in Gradle,
```gradle
test {
    systemProperty "jetty.port", "8888"
}
```

### How to use Docker compose v1 in System Property
For example in Gradle,
```gradle
test {
    systemProperty "useDockerComposeV2", "false"
}
```

### How to specify Remote Web Driver Url in System Property
For example in Gradle,
```gradle
test {
    systemProperty "RemoteWebDriverUrl", "http://sechrome.test:4444/wd/hub"
}
```

### How to specify Host in System Property
For example in Gradle,
```gradle
test {
    systemProperty "Host", "127.0.0.1"
}
```

### How to specify MobileEmulationDeviceName in System Property
For example in Gradle,
```gradle
test {
    systemProperty "MobileEmulationDeviceName", "iPad"
}
```
**Note:** The device name is `Nexus 7` by default, because `iPad` doesn't work since ChromeDriver 115+


**Note:** By default, it's Docker compose v2 since 1.0.10.2. 
### How to release
1. Update the project `version` in `gradle.properties`
2. Run `./gradlew clean build bundleJar`
3. Upload zk-webdriver-VERSION-bundle.jar from `build/libs`
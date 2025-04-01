/* DockerFirefoxDriverTestCase.java

	Purpose:

	Description:

	History:
		10:45 AM 2024/11/29, Created by jumperchen

Copyright (C) 2024 Potix Corporation. All Rights Reserved.
*/
package org.zkoss.test.webdriver;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.UUID;

import com.palantir.docker.compose.DockerComposeExtension;
import com.palantir.docker.compose.configuration.ShutdownStrategy;
import com.palantir.docker.compose.connection.waiting.HealthChecks;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.parallel.ResourceLock;

/**
 * A test case with a Firefox WebDriver running in a Docker container.
 * @author jumperchen
 */
@ResourceLock("dockerResource")
public abstract class DockerFirefoxDriverTestCase extends FirefoxWebDriverTestCase {
	// enable to use docker env.
	protected final boolean isUseDocker() {
		return true;
	}


	protected String getRemoteWebDriverUrl() {
		final int externalPort = firefoxDocker.containers().container(containerName).port(4444).getExternalPort();
		return "http://localhost:" + externalPort + "/wd/hub";
	}

	protected FileLock globalLock;

	private final String containerName = "hub_" + UUID.randomUUID();

	private static final String tempDir = new SimpleDateFormat("yyyyMMdd").format(new java.util.Date());

	// create a temp file for docker compose.yml
	protected String exportResource(String file) {
		InputStream resourceAsStream = ClassLoader.getSystemResourceAsStream(
				"docker/docker-compose.yml");
		try {
			Path path = Paths.get(System.getProperty("java.io.tmpdir"), tempDir, "zkWebdriver").resolve(file);
			if (!Files.isDirectory(path.getParent())) {
				Files.createDirectories(path.getParent());
			}
			if (path.toFile().exists()) {
				path.toFile().delete();
			}

			// always copy the docker-compose.yml to the temp file
			Files.copy(resourceAsStream, path, StandardCopyOption.REPLACE_EXISTING);

			// Replace the placeholder with the container name.
			String content = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
			content = content.replace("${CONTAINER_NAME}", containerName);
			Files.write(path, content.getBytes(StandardCharsets.UTF_8));

			// try to acquire a global lock for each DockerWebDriver
			RandomAccessFile files = new RandomAccessFile(path.toFile(), "rw");
			FileChannel channel = files.getChannel();
			while (true) {
				try {
					globalLock = channel.tryLock();
					if (globalLock.isValid()) {
						break;
					}
				} catch (Throwable e) {
					// File is already locked in this thread or virtual machine
				}
				try {
					Thread.sleep(200);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
			}
			return path.toAbsolutePath().toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@AfterAll
	public void unlockGlobalLock() {
		if (globalLock != null) {
			try {
				globalLock.release();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	@RegisterExtension
	public final DockerComposeExtension firefoxDocker = DockerComposeExtension.builder()
			.file(exportResource("docker/docker-compose.yml"))
			.useDockerComposeV2(Boolean.parseBoolean(System.getProperty("useDockerComposeV2", "true")))
			.waitingForService(containerName, HealthChecks.toRespondOverHttp(4444,
					(port) -> port.inFormat("http://$HOST:$EXTERNAL_PORT/ui/index.html")))
			.waitingForService("firefox", HealthChecks.toHaveAllPortsOpen())
			.shutdownStrategy(ShutdownStrategy.KILL_DOWN)
			.build();
}

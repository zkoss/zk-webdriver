/* DockerWebDriverTestCase.java

	Purpose:
		
	Description:
		
	History:
		1:29 PM 2021/12/30, Created by jumperchen

Copyright (C) 2021 Potix Corporation. All Rights Reserved.
*/
package org.zkoss.test.webdriver;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import com.palantir.docker.compose.DockerComposeExtension;
import com.palantir.docker.compose.connection.waiting.HealthChecks;
import org.junit.jupiter.api.extension.RegisterExtension;

/**
 * A local Docker container to support a linux based remote WebDriver for
 * Chrome. (Optional Edge and Firefox)
 * @author jumperchen
 */
public abstract class DockerWebDriverTestCase extends WebDriverTestCase {

	// enable to use docker env.
	protected final boolean isUseDocker() {
		return true;
	}

	protected String getRemoteWebDriverUrl() {
		final int externalPort = docker.containers().container("hub").port(4444).getExternalPort();
		return "http://localhost:" + externalPort + "/wd/hub";
	}

	// create a temp file for docker compose.yml
	private static String exportResource(String file) {
		InputStream resourceAsStream = ClassLoader.getSystemResourceAsStream(
				"docker/docker-compose.yml");
		try {
			String dest = Files.createTempDirectory("zkWebdriver")
					.resolve(file).toFile().getAbsolutePath();
			Path path = Paths.get(dest);
			if (!Files.isDirectory(path.getParent())) {
				Files.createDirectories(path.getParent());
			}
			Files.copy(resourceAsStream, path, StandardCopyOption.REPLACE_EXISTING);
			return dest;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	// remove the docker env. if the bug has fixed - https://tracker.zkoss.org/browse/ZK-5092
	@RegisterExtension
	public final static DockerComposeExtension docker = DockerComposeExtension.builder()
			.file(exportResource("docker/docker-compose.yml"))
			.useDockerComposeV2(false)
			.waitingForService("hub", HealthChecks.toRespondOverHttp(4444,
					(port) -> port.inFormat("http://$HOST:$EXTERNAL_PORT/ui/index.html")))
			.waitingForService("chrome", HealthChecks.toHaveAllPortsOpen())
			.build();
}

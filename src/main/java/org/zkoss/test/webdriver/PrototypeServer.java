/* PrototypeServer.java

	Purpose:
		
	Description:
		
	History:
		11:44 AM 2022/9/26, Created by jumperchen

Copyright (C) 2022 Potix Corporation. All Rights Reserved.
*/
package org.zkoss.test.webdriver;

import java.net.InetSocketAddress;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;
import org.junit.jupiter.api.extension.TestInstancePreDestroyCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A server instance per a test case instance.
 * @author jumperchen
 * @since 1.0.4
 */
public class PrototypeServer implements TestInstancePostProcessor, TestInstancePreDestroyCallback {
	private static final Logger log = LoggerFactory.getLogger(
			PrototypeServer.class);
	private Server server;
	public void postProcessTestInstance(Object testInstance,
			ExtensionContext eContext) throws Exception {
		server = new Server(
				new InetSocketAddress(BaseTestCase.getHost(), Integer.parseInt(BaseTestCase.getServerPort())));

		final WebAppContext context = new WebAppContext();
		context.setContextPath(BaseTestCase.getContextPath());
		context.setBaseResource(Resource.newResource(BaseTestCase.getBaseResource()));
		context.getSessionHandler().setSessionIdPathParameterName(null);
		server.setHandler(new HandlerList(context, new DefaultHandler()));
		BaseTestCase base = (BaseTestCase) testInstance;
		server.start();
		base.initServer(server);
		log.info("Server runs at: " + base.getAddress());
	}

	public Server getServer() {
		return server;
	}

	public void preDestroyTestInstance(ExtensionContext context)
			throws Exception {
		BaseTestCase base = (BaseTestCase) context.getTestInstance().get();
		base.destroyServer(server);
		if (server != null) {
			server.stop();
			log.info("Stop server.");
		}
	}
}

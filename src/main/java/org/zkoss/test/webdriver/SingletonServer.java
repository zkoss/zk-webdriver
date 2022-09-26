/* SingletonServer.java

	Purpose:
		
	Description:
		
	History:
		11:33 AM 2022/9/26, Created by jumperchen

Copyright (C) 2022 Potix Corporation. All Rights Reserved.
*/
package org.zkoss.test.webdriver;

import java.net.InetSocketAddress;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.NetworkConnector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A singleton server instance for all test cases.
 *
 * @author jumperchen
 * @since 1.0.4
 */
public class SingletonServer
		implements BeforeAllCallback, ExtensionContext.Store.CloseableResource {
	private static final Logger log = LoggerFactory.getLogger(
			SingletonServer.class);
	private static volatile Server server;
	private static final Lock LOCK = new ReentrantLock();
	public void beforeAll(ExtensionContext eContext) throws Exception {
		LOCK.lock();
		try {
			if (server == null) {
				server = new Server(new InetSocketAddress(BaseTestCase.getHost(),
						Integer.parseInt(BaseTestCase.getServerPort())));

				final WebAppContext context = new WebAppContext();
				context.setContextPath(BaseTestCase.getContextPath());
				context.setBaseResource(Resource.newResource(BaseTestCase.getBaseResource()));
				context.getSessionHandler().setSessionIdPathParameterName(null);
				server.setHandler(new HandlerList(context, new DefaultHandler()));
				server.start();
				for (Connector c : server.getConnectors()) {
					if (c instanceof NetworkConnector) {
						if (((NetworkConnector) c).getLocalPort() > 0) {
							BaseTestCase.static_port = ((NetworkConnector) c).getLocalPort();
							break;
						}
					}
				}
				eContext.getRoot().getStore(ExtensionContext.Namespace.GLOBAL).put(this.getClass().getName(), this);
				log.info("Server runs at: " + BaseTestCase.getAddress());
			}
		} finally {
			LOCK.unlock();
		}
	}

	public Server getServer() {
		return server;
	}

	public void close() throws Throwable {
		if (server != null) {
			server.stop();
			log.info("Stop server.");
		}
	}
}

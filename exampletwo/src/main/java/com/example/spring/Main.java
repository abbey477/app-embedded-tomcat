package com.example.spring;

import java.util.HashSet;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleEvent;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.LifecycleState;
import org.apache.catalina.startup.Tomcat;
import org.springframework.web.SpringServletContainerInitializer;

public class Main {
    Tomcat tomcat;

    {
        tomcat = new Tomcat();
//      tomcat.setAddDefaultWebXmlToWebapp(false);
//      tomcat.noDefaultWebXmlPath();
    }

    public void run() throws Throwable {
        tomcat.setBaseDir("F:\\Game\\tomcat");
        tomcat.setHostname("localhost");
        tomcat.setPort(80);
//      tomcat.enableNaming();

//      tomcat.getHost().setAutoDeploy(false);
//      tomcat.getEngine().setBackgroundProcessorDelay(-1);

        Context ctx = tomcat.addContext("", "ROOT");

        ctx.addLifecycleListener(new LifecycleListener() {
            public void lifecycleEvent(LifecycleEvent event) {
//              System.out.println(event.getLifecycle().getState().name());
                if (event.getLifecycle().getState() == LifecycleState.STARTING_PREP) {
                    try {
                        new SpringServletContainerInitializer().onStartup(new HashSet<Class<?>>() {
                            private static final long serialVersionUID = 1L;
                            {
                                add(WebAppInitializer.class);
                            }
                        }, ctx.getServletContext());
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                }
            }
        });

//      tomcat.init();
        tomcat.getConnector();
        tomcat.start();
        tomcat.getServer().await();
    }

    public static void main(String[] args) throws Throwable {
        new Main().run();
    }

    public void slskd() {
        Context ctx = tomcat.addContext("", "ROOT");

        ctx.addLifecycleListener(new LifecycleListener() {
            public void lifecycleEvent(LifecycleEvent event) {
                if (event.getLifecycle().getState() == LifecycleState.STARTING_PREP) {
                    try {
                        new WebAppInitializer().onStartup(ctx.getServletContext());
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}



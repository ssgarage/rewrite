package com.example.proxyserver;

import javax.servlet.ServletContext;

import org.ocpsoft.rewrite.config.Configuration;
import org.ocpsoft.rewrite.config.ConfigurationBuilder;
import org.ocpsoft.rewrite.config.proxy.Proxy;
import org.ocpsoft.rewrite.servlet.config.HttpConfigurationProvider;
import org.ocpsoft.rewrite.servlet.config.Path;

public class ProxyRewriteConfiguration extends HttpConfigurationProvider
{

   @Override
   public Configuration getConfiguration(ServletContext context)
   {
      return ConfigurationBuilder.begin()
               .addRule()
               .when(Path.matches("/proxy"))
               .perform(Proxy.to("http://google.com:80/"));
   }

   @Override
   public int priority()
   {
      return 0;
   }

}

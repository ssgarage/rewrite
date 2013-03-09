package org.ocpsoft.rewrite.config.proxy;

import java.util.Set;

import org.ocpsoft.rewrite.context.EvaluationContext;
import org.ocpsoft.rewrite.param.ParameterStore;
import org.ocpsoft.rewrite.param.Parameterized;
import org.ocpsoft.rewrite.param.ParameterizedPatternBuilder;
import org.ocpsoft.rewrite.param.RegexParameterizedPatternBuilder;
import org.ocpsoft.rewrite.servlet.config.HttpOperation;
import org.ocpsoft.rewrite.servlet.http.event.HttpServletRewrite;
import org.ocpsoft.rewrite.util.ParseTools.CaptureType;

public class Proxy extends HttpOperation implements Parameterized
{
   private static ParameterizedPatternBuilder location;

   public Proxy(String url)
   {
      location = new RegexParameterizedPatternBuilder(CaptureType.BRACE, "[^/]+", url);
   }

   public static Proxy to(String url)
   {
      return new Proxy(url);
   }

   @Override
   public void performHttp(HttpServletRewrite event, EvaluationContext context)
   {

   }

   @Override
   public Set<String> getRequiredParameterNames()
   {
      return location.getRequiredParameterNames();
   }

   @Override
   public void setParameterStore(ParameterStore store)
   {
      location.setParameterStore(store);
   }
}

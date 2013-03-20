/*
 * Copyright 2011 <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.ocpsoft.rewrite.transform;

import org.ocpsoft.rewrite.context.EvaluationContext;
import org.ocpsoft.rewrite.servlet.RewriteWrappedResponse;
import org.ocpsoft.rewrite.servlet.config.HttpOperation;
import org.ocpsoft.rewrite.servlet.http.event.HttpInboundServletRewrite;
import org.ocpsoft.rewrite.servlet.http.event.HttpServletRewrite;

/**
 * HTTP operation that applies one or more {@link Transformer} implementations to the response.
 * 
 * @author Christian Kaltepoth
 */
public class Transform extends HttpOperation
{

   private PipelineContentInterceptor pipeline = new PipelineContentInterceptor();

   private Transform()
   {
      // hide default constructor
   }

   /**
    * Creates a transform instance that applies the given {@link Transformer}. Please note that the {@link Transformer}
    * implementation has to provide a public default constructor.
    */
   public static Transform with(Class<? extends Transformer> transformer)
   {
      return new Transform().and(transformer);
   }

   /**
    * Creates a transform instance that applies the given {@link Transformer}.
    */
   public static Transform with(Transformer transformer)
   {
      return new Transform().and(transformer);
   }

   /**
    * Adds another {@link Transformer} implementation to the transformer pipeline. The supplied transformer will be
    * executed AFTER all other transformer that have been added before. Please note that the {@link Transformer}
    * implementation has to provide a public default constructor.
    */
   public Transform and(Class<? extends Transformer> transformerType)
   {
      try {
         return and(transformerType.newInstance());
      }
      catch (InstantiationException e) {
         throw new IllegalArgumentException(e);
      }
      catch (IllegalAccessException e) {
         throw new IllegalArgumentException("The default constructor of " + transformerType.getName()
                  + " is not accessible", e);
      }
   }

   /**
    * Adds another {@link Transformer} implementation to the transformer pipeline. The supplied transformer will be
    * executed AFTER all other transformer that have been added before.
    */
   public Transform and(Transformer transformer)
   {
      pipeline.add(transformer);
      return this;
   }

   @Override
   public void performHttp(HttpServletRewrite event, EvaluationContext context)
   {
      if (event instanceof HttpInboundServletRewrite) {
         RewriteWrappedResponse.getCurrentInstance(event.getRequest()).addContentInterceptor(pipeline);
      }
   }

}

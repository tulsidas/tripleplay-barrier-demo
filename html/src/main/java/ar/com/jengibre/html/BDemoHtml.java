package ar.com.jengibre.html;

import playn.core.PlayN;
import playn.html.HtmlGame;
import playn.html.HtmlPlatform;
import ar.com.jengibre.core.BDemo;

public class BDemoHtml extends HtmlGame {

   @Override
   public void start() {
      HtmlPlatform.Config config = new HtmlPlatform.Config();

      HtmlPlatform.register(config);
      
//      HtmlPlatform platform = 
//      platform.assets().setPathPrefix("bdemo/");

      PlayN.run(new BDemo());
   }
}

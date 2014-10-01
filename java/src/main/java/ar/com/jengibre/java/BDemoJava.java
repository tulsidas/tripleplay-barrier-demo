package ar.com.jengibre.java;

import playn.core.PlayN;
import playn.java.JavaPlatform;

import ar.com.jengibre.core.BDemo;

public class BDemoJava {

   public static void main(String[] args) {
      JavaPlatform.Config config = new JavaPlatform.Config();

      config.width = 600;
      config.height = 454;

      JavaPlatform.register(config);
      PlayN.run(new BDemo());
   }
}
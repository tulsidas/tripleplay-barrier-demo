package ar.com.jengibre.core;

import static playn.core.PlayN.graphics;
import playn.core.Canvas;
import playn.core.CanvasImage;
import playn.core.ImageLayer;
import tripleplay.game.ScreenStack;
import tripleplay.util.Colors;

public class Escena1 extends BaseScreen {

   public Escena1(ScreenStack _stack) {
      super(_stack);
   }

   @Override
   public void wasShown() {
      super.wasShown();

      CanvasImage ci = graphics().createImage(width(), height());
      Canvas c = ci.canvas();

      c.setFillColor(Colors.RED);
      c.fillRect(0, 0, width(), height());
      layer.add(graphics().createImageLayer(ci));

      CanvasImage sqimg = graphics().createImage(50, 50);
      sqimg.canvas().setFillColor(0xFF99CCFF).fillRect(0, 0, 50, 50);
      ImageLayer square = graphics().createImageLayer(sqimg);
      layer.addAt(square, 50, 200);

      anim.repeat(square).tweenX(square).to(width() - 100).in(1000).then().tweenX(square)
            .to(50).in(1000);

   }

   protected void onClick() {
      foto();
   }
}
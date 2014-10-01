package ar.com.jengibre.core;

import static playn.core.PlayN.graphics;
import static playn.core.PlayN.pointer;
import playn.core.Canvas;
import playn.core.CanvasImage;
import playn.core.GroupLayer;
import playn.core.ImageLayer;
import playn.core.ImmediateLayer;
import playn.core.Layer;
import playn.core.Pointer;
import playn.core.Pointer.Event;
import playn.core.canvas.CanvasSurface;
import playn.core.util.Clock;
import pythagoras.f.AffineTransform;
import pythagoras.f.FloatMath;
import pythagoras.f.Transform;
import tripleplay.anim.Animator;
import tripleplay.game.ScreenStack;
import tripleplay.game.UIAnimScreen;
import tripleplay.util.Colors;

public abstract class BaseScreen extends UIAnimScreen {
   protected final ScreenStack _stack;

   public BaseScreen(ScreenStack _stack) {
      this._stack = _stack;

      pointer().setListener(new Pointer.Adapter() {
         @Override
         public void onPointerEnd(Event event) {
            onClick();
         }
      });
   }

   private Animator fanim = new Animator();

   protected abstract void onClick();

   protected void foto() {
      pointer().setEnabled(false);

      ImageLayer foto = graphics().createImageLayer(polaroid());
      layer.add(foto);

      float DUR = 600;
      // 600x454 -> 150x113

      fanim.tweenScale(foto).to(0.25F).in(DUR).easeInOut();
      fanim.tweenXY(foto).to(-10, 370).in(DUR).easeInOut();
      fanim.tweenRotation(foto).to(-FloatMath.PI / 8).in(DUR);

      fanim.addBarrier(3333); // espero a que todo termine + 333

      fanim.tweenXY(foto).to(-300, 270).in(DUR).easeOutBack();
      fanim.tweenRotation(foto).to(-3 * FloatMath.PI).in(DUR);

      fanim.addBarrier();

      fanim.action(new Runnable() {
         public void run() {
            pointer().setEnabled(true);
         }
      });
   }

   private CanvasImage polaroid() {
      CanvasImage screenshot = graphics().createImage(width(), height());
      capture(layer, screenshot.canvas());

      final int BORDE = 2;

      // marco polaroid
      CanvasImage polaroid = graphics().createImage(width() + 20 + BORDE + BORDE,
            height() + 60 + BORDE + BORDE);
      polaroid.canvas().clear();

      // fondo negro
      polaroid.canvas().setFillColor(Colors.BLACK);
      polaroid.canvas().fillRect(0, 0, polaroid.width(), polaroid.height());

      // fondo blanco
      polaroid.canvas().setFillColor(Colors.WHITE);
      polaroid.canvas().fillRect(BORDE, BORDE, polaroid.width() - BORDE, polaroid.height() - BORDE);

      polaroid.canvas().drawImage(screenshot, 10, 10);

      return polaroid;
   }

   public static void capture(Layer layer, Canvas canvas) {
      if (!layer.visible())
         return;
      canvas.save();

      concatTransform(canvas, layer.transform());
      canvas.translate(-layer.originX(), -layer.originY());

      if (layer instanceof GroupLayer) {
         GroupLayer gl = (GroupLayer) layer;
         for (int ii = 0, ll = gl.size(); ii < ll; ii++) {
            capture(gl.get(ii), canvas);
         }

      }
      else if (layer instanceof ImageLayer) {
         ImageLayer il = (ImageLayer) layer;
         canvas.setAlpha(il.alpha());
         canvas.drawImage(il.image(), 0, 0);
      }
      else if (layer instanceof ImmediateLayer) {
         ImmediateLayer il = (ImmediateLayer) layer;
         il.renderer().render(new CanvasSurface(canvas).setAlpha(il.alpha()));
      }

      canvas.restore();
   }

   /** Utility method for capture. */
   private static AffineTransform toAffine(Transform t) {
      if (t instanceof AffineTransform)
         return (AffineTransform) t;
      else
         return new AffineTransform(t.scaleX(), t.scaleY(), t.rotation(), t.tx(), t.ty());
   }

   /** Utility method for capture. */
   private static void concatTransform(Canvas canvas, AffineTransform at) {
      canvas.transform(at.m00, at.m01, at.m10, at.m11, at.tx, at.ty);
   }

   /** Utility method for capture. */
   protected static void concatTransform(Canvas canvas, Transform t) {
      concatTransform(canvas, toAffine(t));
   }

   @Override
   public void paint(Clock clock) {
      super.paint(clock);
      fanim.paint(clock);
   }

}
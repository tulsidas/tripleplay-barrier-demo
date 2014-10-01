package ar.com.jengibre.core;

import static playn.core.PlayN.log;
import playn.core.Game;
import playn.core.util.Clock;
import tripleplay.game.ScreenStack;

public class BDemo extends Game.Default {

   private static final int UPDATE_RATE = 33;

   private final ScreenStack _stack = new ScreenStack() {
      @Override
      protected void handleError(RuntimeException error) {
         log().error(error.toString());
      }
   };

   protected final Clock.Source _clock = new Clock.Source(UPDATE_RATE);

   public BDemo() {
      super(UPDATE_RATE); // call update every 33ms (30 times per second)
   }

   @Override
   public void init() {
      _stack.push(new Escena1(_stack));
   }

   @Override
   public void update(int delta) {
      _clock.update(delta);
      _stack.update(delta);
   }

   @Override
   public void paint(float alpha) {
      _clock.paint(alpha);
      _stack.paint(_clock);
   }
}
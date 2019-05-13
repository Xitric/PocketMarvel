package dk.sdu.pocketmarvel;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MarvelExecutors {

    private static MarvelExecutors instance;
    private Executor main;
    private Executor background;

    private MarvelExecutors() {
        main = new MainExecutor();
        background = Executors.newFixedThreadPool(3);
    }

    public static MarvelExecutors getInstance() {
        if (instance == null) {
            instance = new MarvelExecutors();
        }

        return instance;
    }

    public Executor getMain() {
        return main;
    }

    public Executor getBackground() {
        return background;
    }

    private static class MainExecutor implements Executor {

        private Handler main = new Handler(Looper.getMainLooper());

        @Override
        public void execute(Runnable command) {
            main.post(command);
        }
    }
}

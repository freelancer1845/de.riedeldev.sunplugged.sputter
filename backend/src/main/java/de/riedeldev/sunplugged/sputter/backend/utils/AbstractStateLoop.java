package de.riedeldev.sunplugged.sputter.backend.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Setter
@Getter(value = AccessLevel.PROTECTED)
public abstract class AbstractStateLoop<T extends Enum<T>> {

	public static final long DEFAULT_TICK_RATE = 100;

	private boolean running;

	private String threadName = "Unnamed Loop";

	private Thread watcherThread;

	private ExecutorService executor;

	private T state;

	private Map<T, Callable<T>> states = new HashMap<>();

	private Callable<T> errorHandler = null;

	private Thread currentWorkThread;

	private volatile boolean immediateChangeRequest = false;

	private long tickRate = DEFAULT_TICK_RATE;

	public AbstractStateLoop() {

		executor = Executors.newSingleThreadExecutor(new ThreadFactory() {

			@Override
			public Thread newThread(Runnable r) {
				Thread thread = new Thread(r);
				thread.setDaemon(true);
				thread.setName(threadName);
				currentWorkThread = thread;
				return thread;
			}
		});

	}

	public void start() {
		if (state == null) {
			throw new IllegalStateException("No initial state definied. Do via setState())");
		}
		startWatcherThread();
	}

	public void finish() {
		running = false;
		executor.shutdown();
	}

	public void requestImmediateStateSwitch(T newState) {
		log.debug("Requesting ImmediateStateSwitch to state: " + newState.toString());

		immediateChangeRequest = true;
		executor.submit(() -> {
			state = newState;

			log.debug("ImmediateStateSwitch executed. New state: " + newState.toString());
			return state;
		});
		currentWorkThread.interrupt();
	}

	private void startWatcherThread() {
		running = true;
		watcherThread = new Thread(new Runnable() {

			@Override
			public void run() {
				log.debug("Starting Watcher Thread  for: " + threadName);

				Thread.currentThread().setName(threadName + " - Watcher");

				while (running) {
					long lastTime = System.nanoTime();
					long minTickTime = (long) (1e9 / tickRate);
					try {

						long now = System.nanoTime();

						long delta = lastTime - now;

						long sleepTime = minTickTime - delta;
						if (sleepTime > 0) {
							long sleepTimeMs = TimeUnit.NANOSECONDS.toMillis(sleepTime);
							if (sleepTimeMs < 1) {
								Thread.sleep(0, (int) sleepTime);
							} else {
								Thread.sleep(sleepTimeMs);
							}

						}

						if (states.containsKey(state) == false) {
							log.error(String.format(
									"No Handler for state %s. This is regarded as a critical error causing the watcher thread top stop and the service becoming unusable.",
									state.toString()));
							running = false;
						} else {

							T newState = executor.submit(() -> {
								try {
									return states.get(state).call();
								} catch (InterruptedException e) {
									if (immediateChangeRequest == true) {
										log.debug("Executing of state: " + state + " was interrupted as expeceted.");
										immediateChangeRequest = false;
										return state;
									} else {
										throw e;
									}
								}
							}).get();

							if (newState != state) {
								log.debug(threadName + ": New State - " + newState.toString());
							}
							state = newState;
							lastTime = System.nanoTime();
						}
					} catch (Exception e) {
						log.error("Unhandled Exception in Watcher Thread.", e);
						log.error("Executing error handler.");
						try {
							if (errorHandler != null) {
								state = executor.submit(errorHandler).get();
							} else {
								log.error(
										"No errorhandler provided. This is regarded as a critical error causing the watcher thread to stop and the service becoming unusable.",
										e);
								running = false;
							}

						} catch (Exception e1) {
							log.error(
									"Error in error handler. This is regarded as a critical error causing the watcher thread to stop and the service becoming unusable.",
									e1);
							running = false;
						}

					}
				}
			}
		});
		watcherThread.start();
	}

	public T getState() {
		return state;
	}

	public boolean isRunning() {
		return running;
	}

}

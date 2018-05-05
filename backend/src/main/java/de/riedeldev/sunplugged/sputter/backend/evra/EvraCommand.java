package de.riedeldev.sunplugged.sputter.backend.evra;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

public class EvraCommand implements Future<List<EvraPackage>> {

	private final String message;

	private List<EvraPackage> packageAnswers = new ArrayList<>();

	private Throwable exception;

	private boolean done = false;

	private boolean cancle = false;

	private final Consumer<EvraCommand> callBack;

	public EvraCommand(String message) {
		this.message = message;
		this.callBack = list -> {
		};
	}

	public EvraCommand(String message, Consumer<EvraCommand> callback) {
		this.message = message;
		this.callBack = callback;
	}

	public String getMessage() {
		return message;
	}

	public Consumer<EvraCommand> getCallBack() {
		return callBack;
	}

	@Override
	public synchronized boolean cancel(boolean mayInterruptIfRunning) {
		cancle = true;
		setDone(true);
		return true;
	}

	@Override
	public synchronized List<EvraPackage> get() throws InterruptedException, ExecutionException {
		while (done == false) {
			wait();
		}

		return packageAnswers;
	}

	@Override
	public synchronized List<EvraPackage> get(long timeout, TimeUnit unit)
			throws InterruptedException, ExecutionException, TimeoutException {
		while (done == false) {
			unit.timedWait(this, timeout);
		}

		return packageAnswers;
	}

	@Override
	public boolean isCancelled() {
		return cancle;
	}

	public Throwable getException() {
		return exception;
	}

	public void setException(Throwable exception) {
		this.exception = exception;
	}

	public synchronized void setDone(boolean done) {
		this.done = done;
		notifyAll();
	}

	public List<EvraPackage> getPackageAnswers() {
		return packageAnswers;
	}

	@Override
	public boolean isDone() {
		return done;
	}
}

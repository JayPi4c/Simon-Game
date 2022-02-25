package com.jaypi4c.simon.util;

import java.util.ArrayList;

public abstract class Observable {

	private ArrayList<Observer> observers;

	private boolean changed;

	private boolean notify = true;

	protected Observable() {
		observers = new ArrayList<>();
		changed = false;
	}

	public void setChanged() {
		changed = true;
	}

	public void notifyAllObservers() {
		if (notify) {
			if (changed) {
				for (Observer s : observers)
					s.update(this);
			}
			changed = true;
		}
	}

	public void addObserver(Observer obs) {
		observers.add(obs);

	}

	public boolean removeObserver(Observer obs) {
		return observers.remove(obs);
	}

	public void activateNotification() {
		notify = true;
		notifyAllObservers();
	}

	public void deactivateNotification() {
		notify = false;
	}
}

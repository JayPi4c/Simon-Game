package com.jaypi4c.simon.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum Field {
	BLUE, RED, GREEN, YELLOW;

	// https://stackoverflow.com/a/1972399
	private static final List<Field> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
	private static final int SIZE = VALUES.size();
	private static final Random RANDOM = new Random();

	public static Field randomField() {
		return VALUES.get(RANDOM.nextInt(SIZE));
	}
}

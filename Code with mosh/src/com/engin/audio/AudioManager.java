/**
 *
 */
package com.engin.audio;

import java.util.*;

public abstract class AudioManager implements Runnable {

	private final static AudioManager INSTANCE = new AudioManager() {
	};

	private Deque<AudioPlayer> players;
	private Thread audioThread;

	private AudioManager() {
		players = new ArrayDeque<>();
		audioThread = new Thread(this);

		audioThread.start();
	}

	public static void submit(AudioPlayer player) {
		INSTANCE.players.add(player);

		// Start the thread if not started
	}

	public static void submit(String audioFilepath) {
		submit(AudioPlayer.create(audioFilepath));
	}

	public void run() {

		AudioPlayer player = null;
		while (true) {

			// Yield the thread if no
			if (INSTANCE.players.size() <= 0) {
				Thread.yield();
			}

			if (player == null) {
				player = players.poll();

				if (player == null)
					continue;
			}

			if (!player.isPlaying())
				player.play();

			if (player.isFinished()) {
				player = null;
			}
		}
	}
}

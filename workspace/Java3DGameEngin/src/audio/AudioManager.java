package audio;

/**
*
*/
import java.util.ArrayDeque;
import java.util.Queue;

import util.Debug;
import util.Util;

public abstract class AudioManager implements Runnable {

	private final static AudioManager INSTANCE = new AudioManager() {
	};

	private Queue<AudioPlayer> players;
	private Thread audioThread;
	private boolean isPlaying;

	private AudioManager() {
		players = new ArrayDeque<>();
		audioThread = new Thread(this);
		isPlaying = false;

		audioThread.start();
	}

	/**
	 * Adds a audio track to the play queue and returns its id
	 * 
	 * @param player The audio track to add
	 * @return Returns the ID of the added track
	 */
	public static long submit(AudioPlayer player) {
		INSTANCE.players.add(player);

		return player.getId();
	}

	/**
	 * Adds a audio track to the play queue and returns its id
	 * 
	 * @param audioFilepath The path of the audio track
	 * @return Returns the ID of the added track
	 */
	public static long submit(String audioFilepath) {
		var temp = AudioPlayer.create(audioFilepath);
		AudioManager.submit(temp);

		return temp.getId();
	}

	/**
	 * Adds a audio track to the play queue after a certain delay and returns its id
	 * 
	 * @param player       The path of the audio track
	 * @param delaySeconds The delay in Seconds
	 * @return Returns the ID of the added track
	 */
	public static long submit(AudioPlayer player, float delaySeconds) {
		Util.setTimeout(() -> {
			AudioManager.submit(player);
		}, delaySeconds);

		return player.getId();
	}

	/**
	 * Adds a audio track to the play queue after a certain delay and returns its id
	 * 
	 * @param audioFilepath The path of the audio track
	 * @param delaySeconds  The delay in Seconds
	 * @return Returns the ID of the added track
	 */
	public static long submit(String audioFilepath, float delaySeconds) {
		var temp = AudioPlayer.create(audioFilepath);

		Util.setTimeout(() -> {
			AudioManager.submit(temp);
		}, delaySeconds);

		return temp.getId();
	}

	/**
	 * @return Returns the number of tracks waiting
	 */
	public static int tracksInQueue() {
		return INSTANCE.players.size();
	}

	/**
	 * Checks if a audio track is playing when the method is called
	 * 
	 * @return Returns true if a track is being played, false otherwise
	 */
	public static boolean isPlaying() {
		return INSTANCE.isPlaying;
	}

	/**
	 * Removes an audio track from the queue (Works half of the time for some
	 * reason)
	 * 
	 * @param id The id of the track to remove
	 * @return Returns the removed track
	 */
	@Deprecated
	public static AudioPlayer remove(long id) {

		AudioPlayer player = null;

		for (var p : INSTANCE.players)
			if (p.getId() == id) {
				player = p;
				INSTANCE.players.remove(p);
			}

		return player;
	}

	public void run() {

		AudioPlayer player = null;
		while (true) {

			// Yield the thread if no
			if (INSTANCE.players.size() <= 0) {
				Thread.yield();
			}

			// If no task is playing then play the next one in the queue
			if (player == null) {
				player = players.poll();

				// Check again if the queue is empty
				if (player == null) {
					isPlaying = false;
					continue;
				}
			}

			if (!player.isPlaying()) {
				player.play();
				isPlaying = true;
			}

			if (player.isFinished()) {
				player = null;
				isPlaying = false;
			}

			// Don't starve the garbage collector
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				Debug.error(e);
			}
		}
	}
}

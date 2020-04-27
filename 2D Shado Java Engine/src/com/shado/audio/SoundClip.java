/**
 *
 */

package com.shado.audio;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class SoundClip implements AutoCloseable {

	private Clip clip = null;
	private FloatControl gainControl;

	public SoundClip(String path) {

		try {

			InputStream bufferedIn = new BufferedInputStream(new FileInputStream(path));
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn);

			AudioFormat baseFormat = audioStream.getFormat();
			AudioFormat decode_format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
					baseFormat.getSampleRate(),
					16,
					baseFormat.getChannels(),
					baseFormat.getChannels() * 2,
					baseFormat.getSampleRate(),
					false
			);

			AudioInputStream decoded_audio = AudioSystem.getAudioInputStream(decode_format, audioStream);

			clip = AudioSystem.getClip();
			clip.open(decoded_audio);

			gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

		} catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	public void play() {
		if (clip == null)
			return;

		stop();
		clip.setFramePosition(0);

		while (!clip.isRunning())
			clip.start();
	}

	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
		play();
	}

	/**
	 * Stops the play of the clip
	 */
	public void stop() {
		if (clip.isRunning())
			clip.stop();
	}

	public void resume() {
		if (clip == null)
			return;

		while (!clip.isRunning())
			clip.start();
	}

	public void pause() {
		clip.stop();
	}

	/**
	 * Closes the SoundClip and frees resources
	 */
	public void close() {
		stop();
		clip.drain();
		clip.close();
	}

	/**
	 * Changes the Gain of the clip
	 * @param value The amount in dB
	 */
	public void setVolume(float value) {
		gainControl.setValue(value);
	}

	/**
	 * @return Returns if the clip is running
	 */
	public boolean isRunning() {
		return clip.isRunning();
	}
}

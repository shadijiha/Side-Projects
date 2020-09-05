package com.ecosystem

import com.engin.Renderer
import com.engin.Scene
import java.awt.Font
import java.awt.Graphics

final class UI() : Scene("UI Scene", 1000) {

    /**
     * Draws the scene content to the screen
     *
     * @param g The graphics that will handle the drawing
     */
    override fun draw(g: Graphics) {
        //To change body of created functions use File | Settings | File Templates.

        g.font = Font("Arial", Font.PLAIN, 20);
        g.drawString(Rabbit.getRabbitCount().toString(), 30, 30);
    }

    /**
     * Initializes the scene and its variables
     *
     * @param renderer The renderer that will handle the scene
     */
    override fun init(renderer: Renderer?) {
        //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * Updates the state of the scene
     *
     * @param dt The time between 2 frames in ms
     */
    override fun update(dt: Float) {
        //To change body of created functions use File | Settings | File Templates.
    }
}
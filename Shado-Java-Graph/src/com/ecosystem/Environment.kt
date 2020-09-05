package com.ecosystem

import com.engin.Renderer
import com.engin.Scene
import com.engin.shapes.Rectangle
import java.awt.Color
import java.awt.Graphics

final class Environment() : Scene("Environment", -2) {

    lateinit var grass: Rectangle;

    /**
     * Draws the scene content to the screen
     *
     * @param g The graphics that will handle the drawing
     */
    override fun draw(g: Graphics) {
        //To change body of created functions use File | Settings | File Templates.
        grass.draw(g);
    }

    /**
     * Initializes the scene and its variables
     *
     * @param renderer The renderer that will handle the scene
     */
    override fun init(renderer: Renderer) {
        //To change body of created functions use File | Settings | File Templates.
        grass = Rectangle(0, 0, renderer.window.width, renderer.window.height);
        grass.fill = Color(34, 100, 76);
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
function initHitBoxDrawer() {
    var win = new ShadoWindow(100, 100, 600, 800, "Test window");
    win.CENTER_X();
    win.CENTER_Y();
    win.open();
    win.setContent("\n        <h1>Unregular shap hitBox setter:</h1>        \n    ");
    EnginGlobal.winCanvas = new Canvas(0, 0, win.getWidth() * 0.9, win.getWidth() * 0.9, "relative", $("#" + win.getID() + "_body"));
    EnginGlobal.winCanvas.setBackground("rgb(150, 150, 150)");
    EnginGlobal.winCanvas.render();
    EnginGlobal.circleTest = new Circle(50, 50, 50);
}

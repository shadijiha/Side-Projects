#include "MainScene.h"

const float MainScene::skyheight = -1.25f;

MainScene::MainScene() :
	Scene("Main Scene"), camera() {
}

MainScene::~MainScene() {
}

void MainScene::onInit() {
	sun = std::make_shared<Shado::Texture2D>("gameassets/sun.png");
}

void MainScene::onUpdate(Shado::TimeStep dt) {
	std::string  s = "FPS " + std::to_string(dt.toFPS());
	Shado::Application::get().getWindow().setTitle(s);

	// Update player
	player.update();

	camera.onUpdate(dt);	 //<-------------------------------------
}

void MainScene::onDraw() {

	using namespace Shado;
	
	Renderer2D::BeginScene(camera.getCamera());

	// Draw Sky and grass
	Renderer2D::DrawQuad({ 0, 0, -10 }, { 100, 100 }, Color::fromRGB(135, 189, 235));
	Renderer2D::DrawQuad({ 0, skyheight, -5 }, { 100, 1.75f }, Color::fromRGB(99, 201, 0));

	// Draw sun
	Renderer2D::DrawQuad({ -1, 0.75, -5 }, { 0.5, 0.5 }, sun);

	// Draw player
	player.draw();

	Renderer2D::EndScene();
	
}

void MainScene::onDestroy() {
}

void MainScene::onEvent(Shado::Event& e) {
	camera.onEvent(e);
}

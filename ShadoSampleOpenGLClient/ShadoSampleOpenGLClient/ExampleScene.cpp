#include "ExampleScene.h"

ExampleScene::ExampleScene() :
	Scene("Test scene"), camera(Shado::Application::getAspectRatio()) {
}

ExampleScene::~ExampleScene() {
}

void ExampleScene::onInit() {
}

void ExampleScene::onUpdate(Shado::TimeStep dt) {
	std::string  s = "FPS " + std::to_string(dt.toFPS());
	Shado::Application::get().setWindowTitle(s);

	camera.onUpdate(dt);
}

void ExampleScene::onDraw() {

	using namespace Shado;
	
	Renderer2D::BeginScene(camera.getCamera());

	for (float y = -5.0f; y < 5.0f; y += 0.5f)
	{
		for (float x = -5.0f; x < 5.0f; x += 0.5f)
		{
			glm::vec4 color = { (x + 5.0f) / 10.0f, 0.4f, (y + 5.0f) / 10.0f, 0.7f };
			Renderer2D::DrawQuad({ x, y , -10 }, { 0.45f, 0.45f }, color);
		}
	}

	Renderer2D::EndScene();
	
}

void ExampleScene::onDestroy() {
}

void ExampleScene::onEvent(Shado::Event& e) {
	camera.onEvent(e);
}

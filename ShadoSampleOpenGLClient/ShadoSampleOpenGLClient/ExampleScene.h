#pragma once
#include "src/Shado.h"

class ExampleScene : public Shado::Scene {
public:
	ExampleScene();
	virtual ~ExampleScene();

	void onInit() override;

	void onUpdate(Shado::TimeStep dt) override;

	void onDraw() override;

	void onDestroy() override;

	void onEvent(Shado::Event& e) override;

private:
	Shado::OrbitCameraController camera;
};

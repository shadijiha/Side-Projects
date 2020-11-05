#pragma once
#include "GameUtility.h"
#include "src/Shado.h"

class MainScene : public Shado::Scene {
public:
	MainScene();
	virtual ~MainScene();

	void onInit() override;

	void onUpdate(Shado::TimeStep dt) override;

	void onDraw() override;

	void onDestroy() override;

	void onEvent(Shado::Event& e) override;

public:
	static const float skyheight;	
private:
	std::shared_ptr<Shado::Texture2D> sun;
	Player player;
	
	CustomCameraController camera;
};

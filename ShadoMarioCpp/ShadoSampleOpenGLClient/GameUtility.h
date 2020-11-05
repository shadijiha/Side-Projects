#pragma once

#include "src/Shado.h"

class Player {
public:
	Player();

	void update();
	void draw();

private:
	void force();
	void jump();
	
private:
	std::shared_ptr<Shado::Texture2D> texture;
	float x, y;
	float w = 0.10, h = 0.30;
	
	float velocity = 0;	
	float gravity = 0.1;
	float lift = -5;
	uint32_t gold;	
};

class CustomCameraController : public Shado::OrthoCameraController {

public:
	CustomCameraController();
	void onUpdate(Shado::TimeStep ts) override;
	
};

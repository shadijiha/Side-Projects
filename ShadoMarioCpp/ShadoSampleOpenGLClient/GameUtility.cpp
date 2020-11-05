#include "GameUtility.h"

#include "MainScene.h"

Player::Player() {
}

void Player::update() {
	force();
}

void Player::draw() {
	Shado::Renderer2D::DrawQuad({x, y, -5}, {w, h}, Shado::Color::BLACK);
}

void Player::force() {
	velocity += gravity;
	y += velocity;

	if (y > MainScene::skyheight - h) {
		y = MainScene::skyheight - h;
		velocity = 0;
	}

	if (y < 0) {
		y = 0;
		velocity = 0;
	}
}

void Player::jump() {
	velocity += lift;
	//energy -= this.jumpCost;
}

CustomCameraController::CustomCameraController()
	: OrthoCameraController(Shado::Application::get().getWindow().getAspectRatio())
{
}

void CustomCameraController::onUpdate(Shado::TimeStep dt) {

	// Movement
	using Shado::Input;
	
	if (Input::isKeyPressed(SHADO_KEY_A))
		m_CameraPosition.x -= m_CameraTranslationSpeed * dt;
	else if (Input::isKeyPressed(SHADO_KEY_D))
		m_CameraPosition.x += m_CameraTranslationSpeed * dt;

	/*
	if (Input::isKeyPressed(SHADO_KEY_W))
		m_CameraPosition.y += m_CameraTranslationSpeed * dt;
	else if (Input::isKeyPressed(SHADO_KEY_S))
		m_CameraPosition.y -= m_CameraTranslationSpeed * dt;	*/

	m_Camera->setPosition(m_CameraPosition);
	m_CameraTranslationSpeed = m_ZoomLevel;
}

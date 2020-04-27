#include "includes/Util/Vec2D.h"

#include <cmath>

Vec2D Vec2D::add(const Vec2D& other) const {
	return Vec2D(x + other.x, y + other.y);
}

Vec2D Vec2D::sub(const Vec2D& other) const {
	return add(other.inverse());
}

Vec2D Vec2D::mult(float scale) const {
	return Vec2D(x * scale, y * scale);
}

Vec2D Vec2D::div(float scale) const {
	return mult(1 / scale);
}

Vec2D Vec2D::inverse() const {
	return Vec2D(x * -1, y * -1);
}

double Vec2D::mag() {
	return sqrt(x * x + y * y);
}

double Vec2D::dotProduct(const Vec2D& other) {
	return x * other.x + y * other.y;
}

Vec3D Vec2D::crossProduct(const Vec2D& other) {
	return NULL;
}

Vec2D Vec2D::operator+(const Vec2D& right) const {
	return this->add(right);
}

Vec2D Vec2D::operator-(const Vec2D& right) const {
	return this->sub(right);
}

Vec2D Vec2D::operator*(double right) const {
	return this->mult(right);
}

Vec2D Vec2D::operator/(double right) const {
	return this->div(right);
}
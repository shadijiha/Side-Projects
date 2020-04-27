#pragma once

typedef int Vec3D;// TODO:: remove this

class Vec2D
{
public:
	float x;
	float y;

	Vec2D(float _x, float _y)
		: x(_x), y(_y)
	{}

	Vec2D(const Vec2D& other)
		: Vec2D(other.x, other.y)	{}

	Vec2D()
		: Vec2D(0.0f, 0.0f) {}

	Vec2D add(const Vec2D& other) const;

	Vec2D sub(const Vec2D& other) const;

	Vec2D mult(float scale) const;

	Vec2D div(float scale) const;

	Vec2D inverse() const;

	double mag();
	double dotProduct(const Vec2D& other);
	Vec3D crossProduct(const Vec2D& other);

	Vec2D operator+(const Vec2D& right) const;
	Vec2D operator-(const Vec2D& right) const;
	Vec2D operator*(double right) const;
	Vec2D operator/(double right) const;
};




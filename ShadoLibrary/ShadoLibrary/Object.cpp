#include "includes/Object.h"
#include <typeinfo>

Object::Object() {}

std::string Object::getClass() {
	return typeid(*this).name();
}

bool Object::equals(Object obj) {
	return this == &obj;
}

std::string Object::toString() {
	return getClass() + "@" + std::to_string(hashCode());
}

int Object::hashCode() {
	return (int)this;
}

Object Object::clone() {
	return Object(*this);
}

std::ostream& operator<<(std::ostream& os, Object o) {
	os << o.toString();
	return os;
}

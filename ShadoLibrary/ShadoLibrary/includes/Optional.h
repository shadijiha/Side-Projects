#pragma once
#include "Object.h"
#include "includes/Exception.h"

template <class T>
class Optional : public Object {
public:
	enum Status { null, ok };	// Status for the optional object
	
	Optional(T e);
	Optional(Status s);
	Optional(const Optional& o);
	~Optional() = default;

	void setDefault(T default_value);
	void toNull();

	T get();

	bool isNull();
	bool hasValue();
	bool equals(Optional<T> o);

	T orElse(T default_val);

	static Optional<T> nullOptional();


	// Opetarors
	bool operator == (Optional<T> o) { return equals(o); }

private:
	T value;
	T default_value;
	Status status;
};

template <class T>
Optional<T>::Optional(T e) {
	value = e;
	default_value = e;
	status = ok;
}

template <class T>
Optional<T>::Optional(Status s) {
	status = s;
}

template <class T>
Optional<T>::Optional(const Optional& o) {
	value = o.value;
	default_value = o.default_value;
	status = o.status;
}

template <class T>
void Optional<T>::setDefault(T default_value) {
	this->default_value = default_value;
}

template <class T>
void Optional<T>::toNull() {
	status = null;
}

template <class T>
T Optional<T>::get() {
	if (status == null)
		throw NullPointerException();

	return value;
}

template <class T>
bool Optional<T>::isNull() {
	return status == null;
}

template <class T>
bool Optional<T>::hasValue() {
	return !isNull();
}

template <class T>
bool Optional<T>::equals(Optional<T> o) {
	return value == o.value && status == o.status;
}

template <class T>
T Optional<T>::orElse(T default_val) {
	default_value = default_val;

	if (status == null)
		return default_value;
	else
		return value;
}

template <class T>
Optional<T> Optional<T>::nullOptional() {
	return Optional<T>(Optional::null);
}

#pragma once

#include <functional>
#include <vector>
#include "AbstractList.h"

// Macros
template <typename T>				using Predicate = std::function<bool(T)>;
template <typename T>				using Consumer = std::function<void(T)>;
template <typename T, typename R>	using Function = std::function<T(R)>;

template <class T>
class Stream {
public:
	Stream(AbstractList<T>& list);

	~Stream();

	// Intermediate operations
	Stream<T>& filter(Predicate<T> condition);
	Stream<T>& map(Function<T, T> map_function);
	
	Stream<T>& limit(uint32_t number);
	Stream<T>& skip(uint32_t number);

	Stream<T>& takeWhile(Predicate<T> condition);
	Stream<T>& takeIf(Predicate<T> condition);

	// Terminal operations
	void forEach(Consumer<T> operation);
	std::vector<T> toVector();
	
private:
	Stream(std::vector<T> v);
	
	std::vector<T> list;
};

template <class T>
Stream<T>::Stream(AbstractList<T>& abstract_list) {
	std::vector<T> vector;
	vector.reserve(abstract_list.size());

	for (int i = 0; i < abstract_list.size(); i++) {
		vector.push_back(abstract_list.get(i));
	}
	
	this->list = vector;
}

template <class T>
Stream<T>::~Stream() = default;

template <class T>
Stream<T>::Stream(std::vector<T> v) {
	list = v;
}

template <class T>
Stream<T>& Stream<T>::filter(Predicate<T> condition) {
	std::vector<T> result_vector;
	result_vector.reserve(list->size());
	
	for (int i = 0; i < list->size(); i++) {	
		if (condition(list[i])) {
			result_vector.push_back(list[i]);
		}
	}
	
	list = result_vector;
	
	return *this;
}

template <class T>
Stream<T>& Stream<T>::map(Function<T, T> map_function) {

	for (T& temp : list) {
		temp = map_function(temp);
	}
	
	return *this;
}

template <class T>
Stream<T>& Stream<T>::limit(uint32_t number) {
	std::vector<T> result;
	result.reserve(number);	// Reserve the correct size to avoid multiple memalloc
	
	for (int i = 0; i < number; i++)
		result.push_back(list[i]);

	list = result;

	return *this;
}

template <class T>
Stream<T>& Stream<T>::skip(uint32_t number) {
	std::vector<T> result;
	result.reserve(list.size() - number);	// Reserve the correct size to avoid multiple memalloc
	
	for (int i = number; i < list.size(); i++)
		result.push_back(list[i]);

	list = result;

	return *this;
}

template <class T>
Stream<T>& Stream<T>::takeWhile(Predicate<T> condition) {
	std::vector<T> result;

	for (const T temp : list) {
		if (condition(temp)) {
			result.push_back(temp);
		} else
		{
			break;
		}
	}

	list = result;
	
	return *this;
}

template <class T>
Stream<T>& Stream<T>::takeIf(Predicate<T> condition) {
	std::vector<T> result;

	for (const T temp : list) {
		if (condition(temp)) {
			result.push_back(temp);
		}
	}

	list = result;

	return *this;
}

template <class T>
void Stream<T>::forEach(Consumer<T> operation) {
	for (T& temp : list)
		operation(temp);
}

template <class T>
std::vector<T> Stream<T>::toVector() {
	return list;
}

#pragma once

#include "List.h"

template <class T>
class ArrayList : public AbstractList<T> {
public:
	//ArrayList(T... elements);
	ArrayList();
	ArrayList(const ArrayList& list);
	virtual ~ArrayList();

	bool add(T element) override;
	void clear() override;
	bool contains(const T& element) override;
	bool contains(const T& element, const std::function<bool(T, T)>& comperator) override;
	bool remove(const T& object) override;
	bool removeIf(const std::function<bool(T)>& condition) override;
	unsigned size() const override;
	bool isEmpty() const override;
	T* toArray() const override;
	bool add(unsigned index, T element) override;
	T& get(unsigned index) override;
	T set(unsigned index, T element) override;
	std::shared_ptr<AbstractList<T>> subList(unsigned start, unsigned end) const override;
	bool remove(unsigned index) override;
	unsigned indexOf(const T& element) const override;
	std::string toString() const override;

	std::shared_ptr<T>* begin();
	std::shared_ptr<T>* end();
	
private:
	std::shared_ptr<T>* array;
	unsigned int capacity;
	int pointer;

	void shift_elements(int from);
	void resize();

	static const unsigned int initial_capacity = 10;
};

template <class T>
ArrayList<T>::ArrayList() {
	array = new std::shared_ptr<T>[initial_capacity];
	pointer = -1;
	capacity = initial_capacity;
}

template <class T>
ArrayList<T>::ArrayList(const ArrayList& list) {
	// TODO
	array = new std::shared_ptr<T>[list.capacity];
	pointer = list.pointer;
	capacity = list.capacity;

	// Copy array
	for (int i = 0; i < list.size(); i++)
		array[i] = list.array[i];
}

template <class T>
ArrayList<T>::~ArrayList() {
	delete[] array;
	pointer = -1;
}

template <class T>
bool ArrayList<T>::add(T element) {

	if (pointer + 1 >= size())
		resize();
	
	pointer++;
	array[pointer] = std::make_shared<T>(element);
	return true;
}

template <class T>
void ArrayList<T>::clear() {
	//TODO: make this work
	//for (int i = 0; i < size(); i++)
		//array[i] = nullptr;

	pointer = -1;
}

template <class T>
bool ArrayList<T>::contains(const T& element) {
	for (int i = 0; i < size(); i++)
		if (element == *array[i])
			return true;
	return false;
}

template <class T>
bool ArrayList<T>::contains(const T& element, const std::function<bool(T, T)>& comperator) {
	for (int i = 0; i < size(); i++)
		if (comperator(*array[i], element))
			return true;
	return false;
}

template <class T>
bool ArrayList<T>::remove(const T& object) {
	for (int i = 0; i < size(); i++) {
		if (*array[i] == object) {
			array[i] = nullptr;
			shift_elements(i - 1);
			pointer--;
			return true;
		}			
	}
	return false;
}

template <class T>
bool ArrayList<T>::removeIf(const std::function<bool(T)>& condition) {
	__debugbreak();	// This function is not working
	for (int i = 0; i < size(); i++) {
		if (condition(*array[i])) {
			array[i] = nullptr;
			shift_elements(i);
			pointer--;
			return true;
		}
	}
	return false;
}

template <class T>
unsigned ArrayList<T>::size() const {
	return pointer + 1;
}

template <class T>
bool ArrayList<T>::isEmpty() const {
	return size() == 0;
}

template <class T>
T* ArrayList<T>::toArray() const {
	T* result = new T[size()];

	for (int i = 0; i < size(); i++)
		result[i] = *array[i];
	
	return result;
}

template <class T>
bool ArrayList<T>::add(unsigned index, T element) {

	// Verify index
	if (index < 0 || index >= size()) {
		__debugbreak();
		return false;
	}		

	// Verify size
	if (pointer + 2 >= size())
		resize();

	// Shift elements
	for (int i = size() - 1; i >= index; i--) {
		array[i + 1] = array[i];
	}

	// Add the element
	array[index] = std::make_shared<T>(element);
	pointer++;
	return true;
}

template <class T>
T& ArrayList<T>::get(unsigned index) {
	if (index < 0 || index >= size())
		__debugbreak();

	return *array[index];
}

template <class T>
T ArrayList<T>::set(unsigned index, T element) {
	if (index < 0 || index >= size())
		__debugbreak();

	auto old_element = array[index];
	array[index] = std::make_shared<T>(element);

	return *old_element;
}

template <class T>
std::shared_ptr<AbstractList<T>> ArrayList<T>::subList(unsigned start, unsigned end) const {

	auto list = std::make_shared<ArrayList<T>>();
	for (int i = start; i <= end; i++)
		list->add(*array[i]);

	return list;
}

template <class T>
bool ArrayList<T>::remove(unsigned index) {
	if (index >= size())
		return false;
	
	array[index] = nullptr;
	shift_elements(index - 1);
	pointer--;
	return true;
}

template <class T>
void ArrayList<T>::shift_elements(int from) {
	
	if (from <= 0)
		return;
	
	for (int i = from; i < size(); i++) {
		
		// Shift only if the element before is nullptr	
		if (array[i - 1] == nullptr) {
			auto temp = array[i - 1];
			array[i - 1] = array[i];
			array[i] = temp;
		}
	}	
}

template <class T>
void ArrayList<T>::resize() {

	// Create new array and copy elements
	std::shared_ptr<T>* new_array = new std::shared_ptr<T>[capacity * 2];

	for (int i = 0; i < size(); i++)
		new_array[i] = array[i];

	delete[] array;

	array = new_array;

	capacity *= 2;
}

template <class T>
unsigned ArrayList<T>::indexOf(const T& element) const {
	for (int i = 0; i < size(); i++)
		if (element == *array[i])
			return i;
	return 0;
}

template <class T>
std::string ArrayList<T>::toString() const {

	std::string str = "[";

	for (int i = 0; i < size(); i++) {
		str += std::to_string(*array[i]);

		if (i != size() - 1)
			str += ", ";
	}
		

	str += "]";
	
	return str; 
}

template <class T>
std::shared_ptr<T>* ArrayList<T>::begin() {
	return array;
}

template <class T>
std::shared_ptr<T>* ArrayList<T>::end() {
	return array + size();
}

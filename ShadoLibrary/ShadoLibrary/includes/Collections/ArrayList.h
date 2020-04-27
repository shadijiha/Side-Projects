#pragma once
#include <iostream>
#include "AbstractList.h"
#include "includes/Exception.h"
#include "Stream.h"

template<class T>
class ArrayList : public AbstractList<T> {
public:
	ArrayList(int initial_capacity);

	ArrayList();

	ArrayList(const ArrayList& other);

	~ArrayList();

	void add(T element) override;
	void insert(T element, int index) override;
	void remove() override;
	void clear() override;
	void remove(int index) override;
	T& get(int index) override;

	bool contains(T element) const override;
	bool isEmpty() const override { return count == 0; }
	uint32_t size() const override { return count; }

	Stream<T> stream();

	T* begin();
	T* end();

	T& operator[] (int index) { return get(index); }

private:
	T* array;
	unsigned int count;
	unsigned int arraySize;	

	// Doubles the size of the array
	void resize();
};

template<class T>
ArrayList<T>::ArrayList(int initial_capacity) {
	count = 0;
	array = new T[initial_capacity];
	arraySize = initial_capacity;
}

template<class T>
ArrayList<T>::ArrayList() : ArrayList(5) {}

template<class T>
ArrayList<T>::ArrayList(const ArrayList& other) {
	count = other.count;
	array = new T[count];
	arraySize = other.arraySize;

	// COpy the content of the array
	for (int i = 0; i < count; i++) {
		array[i] = other.array[i];
	}
}

template<class T>
ArrayList<T>::~ArrayList() {
	delete[] array;
}

template<class T>
void ArrayList<T>::add(T element) {
	if (count == arraySize) {
		resize();
		add(element);
	} else
	{
		array[count++] = element;
	}
}

template<class T>
void ArrayList<T>::insert(T element, int index) {
	if (index < 0 || index > count)
		throw ArrayListOutOfBoundException("Cannot add element at index " + std::to_string(index) + " for an ArrayList of size " + std::to_string(count));

	// If add to the end of the arrayList
	if (index == count)
		add(element);

	if (count == arraySize) {
		resize();
		insert(element, index);
	} else
	{
		// Shift all elements from that index
		for (int i = count; i >= index; i--) {
			array[i + 1] = array[i];
		}

		// Add element to array
		array[index] = element;
		
		count++;
	}
}

template<class T>
void ArrayList<T>::remove() {
	array[--count] = NULL;
}

template <class T>
void ArrayList<T>::clear() {
	count = 0;
}

template<class T>
void ArrayList<T>::remove(int index) {
	if (index < 0 || index >= count)
		throw ArrayListOutOfBoundException("Cannot remove element at index " + std::to_string(index) + " for an ArrayList of size " + std::to_string(count));

	array[index] = NULL;

	// Shift all elements
	for (int i = index; i < count - 1; i++) {
		array[i] = array[i + 1];
	}

	count--;
}

template<class T>
T& ArrayList<T>::get(int index)  {
	if (index < 0 || index >= count)
		throw ArrayListOutOfBoundException("Cannot get element at index " + std::to_string(index) + " for an ArrayList of size " + std::to_string(count));

	return array[index];
}

template<class T>
bool ArrayList<T>::contains(T element) const {
	for (int i = 0; i < count; i++) {
		if (array[i] == element) {
			return true;
		}
	}
	return false;
}

template<class T>
void ArrayList<T>::resize() {
	arraySize *= 2;
	T* newArray = new T[arraySize];

	for (int i = 0; i < count; i++) {
		newArray[i] = array[i];
	}

	T* oldLocation = array;
	array = newArray;

	delete[] oldLocation;
}

template<class T>
Stream<T> ArrayList<T>::stream() {
	return Stream<T>(*this);
}

template<class T>
T* ArrayList<T>::begin() {
	return array;
}

template<class T>
T* ArrayList<T>::end() {
	return array + count;
}

#pragma once

#include <functional>
#include <string>

#define interface class

template <class T>
interface Collection	{

public:
	
	virtual bool add(T element) = 0;
	virtual void clear() = 0;
	virtual bool contains(const T& element) = 0;
	virtual bool contains(const T& element, const std::function<bool(T, T)>& comperator) = 0;
	virtual bool remove(const T& object) = 0;
	virtual bool removeIf(const std::function<bool(T)>& condition) = 0;

	// Iterator
	//virtual T* begin() = 0;
	//virtual T* end() = 0;
	
	// Getters
	virtual unsigned int size() const = 0;
	virtual bool isEmpty() const = 0;
	virtual T* toArray() const = 0;
	virtual std::string toString() const = 0;
};

#pragma once

#include "Collection.h"
#include <memory>

template <class T>
interface AbstractList : public Collection<T>	{

public:
	virtual bool add(unsigned int index, T element) = 0;
	virtual T& get(unsigned int index) = 0;
	virtual T set(unsigned int index, T element) = 0;
	virtual std::shared_ptr<AbstractList<T>> subList(unsigned int start, unsigned int end) const = 0;
	virtual bool remove(unsigned int index) = 0;

	// Getters
	virtual unsigned indexOf(const T& element) const = 0;
};

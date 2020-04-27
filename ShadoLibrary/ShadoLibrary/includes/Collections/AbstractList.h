#pragma once

#include "AbstractCollection.h"

template <class T>
interface AbstractList : public AbstractCollection<T> {
public:
	virtual ~AbstractList() {}
	
	virtual void insert(T element, int index) = 0;
	virtual void remove(int index) = 0;

	virtual T& get(int index) = 0;
};

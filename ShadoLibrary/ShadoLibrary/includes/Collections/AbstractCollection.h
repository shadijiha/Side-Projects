#pragma once

#define interface class
#define implements : public

template <class T>
interface AbstractCollection	{
public:
	virtual ~AbstractCollection() {}

	virtual void add(T element) = 0;
	virtual void remove() = 0;
	virtual void clear() = 0;

	// Getters
	virtual bool contains(T element) const = 0;
	virtual bool isEmpty() const = 0;	
	virtual uint32_t size() const = 0;
};

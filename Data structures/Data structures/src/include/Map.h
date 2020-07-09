#pragma once

#include "Collection.h"
#include "List.h"

template <class K, class V>
interface Map {

public:

	virtual void put(K key, V value) = 0;
	virtual V& get(K key) = 0;
	virtual V remove(K key) = 0;

	virtual bool containsKey(const K& key) = 0;
	virtual bool containsValue(const V& value) = 0;

	virtual AbstractList<K> keySet() = 0;	
};

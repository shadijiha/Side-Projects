#pragma once

#include "Map.h"

template <class K, class V>
class HashTable	: public Map<K, V> {
public:
	
	HashTable(unsigned int initial_capacity, bool resizeable);
	HashTable();	
	HashTable(const HashTable& other);
	~HashTable();
	
	void put(K key, V value) override;
	V& get(K key) override;
	V remove(K key) override;
	bool containsKey(const K& key) override;
	bool containsValue(const V& value) override;
	AbstractList<K> keySet() override;

private:
	unsigned int hash(K* key);
	
private:
	bool m_resizeable;
	unsigned int m_capacity;
	

	std::shared_ptr<V>* m_array;
	unsigned int m_size;
};

template <class K, class V>
HashTable<K, V>::HashTable(unsigned initial_capacity, bool resizeable) {
	m_capacity = initial_capacity;
	m_resizeable = resizeable;
	m_size = 0;
	m_array = new std::shared_ptr<V>[initial_capacity];
}

template <class K, class V>
HashTable<K, V>::HashTable()
	: HashTable(10, true)
{
}

template <class K, class V>
HashTable<K, V>::HashTable(const HashTable& other) {
}

template <class K, class V>
HashTable<K, V>::~HashTable() {
}

template <class K, class V>
void HashTable<K, V>::put(K key, V value) {

	auto index = hash(&key);
	if (m_array[index] == nullptr)
		m_arra[index] = std::make_shared<V>(value);
	
}

template <class K, class V>
V& HashTable<K, V>::get(K key) {
}

template <class K, class V>
V HashTable<K, V>::remove(K key) {
}

template <class K, class V>
bool HashTable<K, V>::containsKey(const K& key) {
}

template <class K, class V>
bool HashTable<K, V>::containsValue(const V& value) {
}

template <class K, class V>
AbstractList<K> HashTable<K, V>::keySet() {
}

template <class K, class V>
unsigned HashTable<K, V>::hash(K* key) {
	return key % m_capacity;
}

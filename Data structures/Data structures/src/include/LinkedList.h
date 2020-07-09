#pragma once

#include "List.h"
#include <exception>

template <class T>
class LinkedList : public AbstractList<T> {

public:

	LinkedList();
	LinkedList(const LinkedList& o);
	virtual ~LinkedList();
		
	bool add(T element) override;
	void clear() override;
	bool contains(const T& element) override;
	bool contains(const T& element, const std::function<bool(T, T)>& comperator) override;
	bool remove(const T& object) override;
	bool removeIf(const std::function<bool(T)>& condition) override;
	unsigned size() const override;
	bool isEmpty() const override;
	T* toArray() const override;
	std::string toString() const override;
	bool add(unsigned index, T element) override;
	T& get(unsigned index) override;
	T set(unsigned index, T element) override;
	std::shared_ptr<AbstractList<T>> subList(unsigned start, unsigned end) const override;
	bool remove(unsigned index) override;
	unsigned indexOf(const T& element) const override;

private:

	class Node {
		T m_element;
		Node* m_next;
		Node* m_previous;

	public:
		Node(T e, Node* p, Node n) : m_element(e), m_previous(p), m_next(n)	{}
		Node(const Node& o) : Node(o.m_element, o.m_previous, o.m_next)	{}

		bool hasNext() { if (m_next != nullptr) return true; return false; }
		bool hasPrevious() { if (m_previous != nullptr) return true; return false; }
		
		Node* next() const { return m_next; }
		Node* previous() const { return m_previous; }		
		T& element() { return m_element; }

		void setElement(T element) { m_element = element; }
		void setNext(Node* n) { m_next = n; }
		void setPrevious(Node* p) { m_previous = p; }
	};
	
	unsigned int m_size;
	Node* head;
	Node* tail;
};

template <class T>
LinkedList<T>::LinkedList() {
	head = nullptr;
	tail = nullptr;
	m_size = 0;
}

template <class T>
LinkedList<T>::LinkedList(const LinkedList& o) = default;

template <class T>
LinkedList<T>::~LinkedList() {

	Node* position = head;
	while (head != nullptr && head->hasNext()) {
		Node* temp = position->next();
		delete position;
		position = temp;
	}

	delete head;
}

template <class T>
bool LinkedList<T>::add(T element) {

	Node* h = new Node(element, nullptr, head);
	head->setPrevious(h);

	head = h;

	m_size++;

	return true;
}

template <class T>
void LinkedList<T>::clear() {
	Node* position = head;
	while (head != nullptr && head->hasNext()) {
		Node* temp = position->next();
		delete position;
		position = temp;
	}

	head = nullptr;
	size = 0;
}

template <class T>
bool LinkedList<T>::contains(const T& element) {

	Node* position = head;
	while(position->hasNext() && position != nullptr) {
		if (position->element() == element)
			return true;
		position = position->next();
	}

	return false;	
}

template <class T>
bool LinkedList<T>::contains(const T& element, const std::function<bool(T, T)>& comperator) {

	Node* position = head;
	while (position->hasNext() && position != nullptr) {
		if (comperator(position->element(), element))
			return true;
		position = position->next();
	}

	return false;
}

template <class T>
bool LinkedList<T>::remove(const T& object) {

	Node* position = head;
	while (position->hasNext() && position != nullptr) {
		if (object == position->element()) {

			position->previous()->setNext(position->next());
			position->next()->setPrevious(position->previous());
			
			delete position;

			m_size--;
			
			return true;
		}
			
		position = position->next();
	}

	return false;	
}

template <class T>
bool LinkedList<T>::removeIf(const std::function<bool(T)>& condition) {
	__debugbreak();
	return false;
}

template <class T>
unsigned LinkedList<T>::size() const {
	return m_size;
}

template <class T>
bool LinkedList<T>::isEmpty() const {
	return m_size == 0;
}

template <class T>
T* LinkedList<T>::toArray() const {

	T* result = new T[size()];

	Node* position = head;
	int index = 0;
	while (head != nullptr && head->hasNext()) {
		result[index++] = position->element();
		position = position->next();
	}
	
	return result;	
}

template <class T>
std::string LinkedList<T>::toString() const {
	std::string str = "[";

	Node* position = head;
	while (head != nullptr && head->hasNext()) {
		str += std::to_string(position->element());
		
		position = position->next();
		
		if (position->hasNext()) {
			str += ", ";
		}		
	}

	str += "]";

	return str;
}

template <class T>
bool LinkedList<T>::add(unsigned index, T element) {

	if (index > size()) {
		__debugbreak();
		return false;
	}

	if (index == 0) {
		add(element);
		return true;
	}

	Node* position = head;
	int i = 0;

	while(position->hasNext()) {

		if (i == index) {
			Node* toAdd = new Node(element, position, position->next());
			position->setNext(toAdd);

			m_size++;
			
			return true;
		}
		
		i++;
		position = position->next();
	}

	return false;	
}

template <class T>
T& LinkedList<T>::get(unsigned index) {

	if (index >= size() || isEmpty())
		__debugbreak();
	
	Node* position = head;
	int i = 0;

	while (position->hasNext()) {

		if (i == index) {
			return position->element();
		}

		i++;
		position = position->next();
	}

	throw std::exception("Something went terribly wrong"); // SHould never get here
}

template <class T>
T LinkedList<T>::set(unsigned index, T element) {
	
	if (index >= size())
		__debugbreak();

	Node* old_element = nullptr;
	Node* position = head;
	int i = 0;

	while (position->hasNext()) {

		if (i == index) {
			old_element = position;
			position->setElement(element);
			return old_element->element();
		}

		i++;
		position = position->next();
	}

	throw std::exception("Something went terribly wrong");	// Should never get there
}

template <class T>
std::shared_ptr<AbstractList<T>> LinkedList<T>::subList(unsigned start, unsigned end) const {

	if (end >= size() || start > end)
		__debugbreak();

	std::shared_ptr<AbstractList<T>> list = std::make_shared<T>();
	
	Node* position = head;
	int i = 0;

	while (position->hasNext()) {

		if (i >= start && i <= end)
			list->add(position->element());

		i++;
		position = position->next();
	}

	return list;
}

template <class T>
bool LinkedList<T>::remove(unsigned index) {

	Node* position = head;
	int i = 0;

	while (position->hasNext()) {

		if (i == index) {

			position->previous()->setNext(position->next());
			position->next()->setPrevious(position->previous());

			m_size--;

			delete position;
		}

		i++;
		position = position->next();
	}

	return false;
}

template <class T>
unsigned LinkedList<T>::indexOf(const T& element) const {
	Node* position = head;
	int i = 0;

	while (position->hasNext()) {

		if (position->element() == element) {
			return i;
		}

		i++;
		position = position->next();
	}

	throw std::exception("Item doesn't exist");
}

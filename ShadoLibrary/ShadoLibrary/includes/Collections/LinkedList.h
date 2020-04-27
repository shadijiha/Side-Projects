#pragma once

#include "AbstractList.h"
#include "String.h"
#include <optional>
#include <vector>

#include "includes/Optional.h"

template <class T>
class Node {
public:
	Node(T data, Node* next);
	
	Node* next();
	T& getData();

	void setNext(Node* after);

	bool hasNext() const;
	
public:
	Node* after;
	T data;
};

template <class T>
class LinkedList implements AbstractList<T> {
public:
	LinkedList();
	LinkedList(const LinkedList& other);	

	~LinkedList();

	class LinkedListIterator;
	
	// Override AbstractCollection interface
	void add(T element) override;
	void remove() override;
	void clear() override;

	Stream<T> stream();
	
	// Getters
	bool contains(T element) const  override;
	bool isEmpty() const override;
	uint32_t size() const override;
	std::vector<T> toVector();

	// Override AbstractList interface
	void insert(T element, int index) override;
	void remove(int index) override;

	T& get(int index) override;
	Optional<int> indexOf(T element);

	T& operator[](unsigned int index) {
		return get(index);
	}

	// Root of LinkedList wrapped in Iterator type 
	LinkedListIterator iterator()
	{
		return LinkedListIterator(head);
	}

	// Iterator class can be used to 
	// sequentially access nodes of linked list 
	class LinkedListIterator
	{
	public:
		LinkedListIterator(Node<T>* _head) {
			position = _head;
			previous = nullptr;
		}

		void restart() {
			position = head;
			previous - nullptr;
		}

		T& next() {
			if (!hasNext())
				throw LinkedListIndexOutOfBoundException();
			T& toReturn = position->getData();
			previous = position;
			position = position->next();

			return toReturn;
		}

		bool hasNext() {
			return position != nullptr;
		}

		T& peek() {
			if (!hasNext())
				throw LinkedListIndexOutOfBoundException();
			return position->getData();
		}

	private:
		Node<T>* position;
		Node<T>* previous;
	};
	
private:
	Node<T>* head;
	uint32_t length;

	Node<T>* getNode(int index);
	Node<T>* copyOf(Node<T>* otherHead);

};

// ================ NODE CLASS ===================
template <class T>
Node<T>::Node(T data, Node* next) {
	this->after = next;
	this->data = data;
}

template <class T>
Node<T>* Node<T>::next() {
	return after;
}

template <class T>
void Node<T>::setNext(Node* _a) {
	after = _a;
}

template <class T>
T& Node<T>::getData() {
	return data;
}

template <class T>
bool Node<T>::hasNext() const {
	return after != nullptr;
}

// ================ LinkedList CLASS ===================
template <class T>
LinkedList<T>::LinkedList() {
	head = nullptr;
	length = 0;
}

template <class T>
LinkedList<T>::LinkedList(const LinkedList& other) {
	if (other.head == nullptr)
		head = nullptr;
	else
		head = copyOf(other.head);	
}

template <class T>
LinkedList<T>::~LinkedList() {
	Node<T>* position = head;
	while (position != nullptr) {
		auto* ptr = position;
		position = position->next();
		delete ptr;
	}
	head = nullptr;
}

template <class T>
void LinkedList<T>::add(T element) {
	head = new Node<T>(element, head);
	length++;
}

template <class T>
void LinkedList<T>::remove() {
	remove(size() - 1);
}

template <class T>
void LinkedList<T>::clear() {

	Node<T>* position = head;
	while(position != nullptr) {
		auto* ptr = position;
		position = position->next();
		delete ptr;
	}
	head = nullptr;	
}

template <class T>
Stream<T> LinkedList<T>::stream() {
	return Stream<T>(*this);
}

template <class T>
std::vector<T> LinkedList<T>::toVector() {
	std::vector<T> result;
	result.reserve(size());

	Node<T>* position = head;
	while (position != nullptr) {
		result.push_back(position->getData());
		position = position->next();
	}

	return result;
}

template <class T>
bool LinkedList<T>::contains(T element) const {
	//return !indexOf(element).isNull();
	return false;
}

template <class T>
bool LinkedList<T>::isEmpty() const {
	return size() == 0;
}

template <class T>
uint32_t LinkedList<T>::size() const {

	uint32_t count = 0;

	Node<T>* node = head;

	while(node != nullptr) {
		count++;
		node = node->next();
	}
	return count;
}

template <class T>
void LinkedList<T>::insert(T element, int index) {
	if (index < 0 || index > size() - 1) {
		throw new LinkedListIndexOutOfBoundException();
	} else
	{
		Node<T>* previous = getNode(index - 1);
		Node<T>* suivant = getNode(index);
		Node<T>* current = new Node<T>(element, suivant);

		previous->setNext(current);

		length++;
	}
}

template <class T>
void LinkedList<T>::remove(int index) {
	if (index < 0 || index > size() - 1) {
		throw LinkedListIndexOutOfBoundException();
	} else if (index == 0) {
		head = head->next();
	} else
	{
		Node<T>* previous = getNode(index - 1);
		Node<T>* current = getNode(index);

		previous->setNext(current->next());
		delete current;

		length--;
	}
}

template <class T>
T& LinkedList<T>::get(int index) {
	if (index < 0 || index > size() - 1) {
		throw LinkedListIndexOutOfBoundException();
	} else
	{
		Node<T>* position = head;
		uint32_t iterations = 0;
		
		while(position != nullptr) {
			if (iterations == index)
				return position->getData();
			position = position->next();

			iterations++;
		}
		return head->getData();
	}
}

template <class T>
Optional<int> LinkedList<T>::indexOf(T element) {

	Node<T>* position = head;
	T itemAtPosition;

	int i = 0;

	while(position != nullptr) {
		itemAtPosition = position.getData();
		if (itemAtPosition == element)
			return Optional<int>(i);
		position = position->next();
		i++;
	}
	return Optional<int>(Optional<int>::null);
}

template <class T>
Node<T>* LinkedList<T>::getNode(int index) {
	if (index < 0 || index > size() - 1) {
		throw LinkedListIndexOutOfBoundException();
	} else
	{
		Node<T>* position = head;
		uint32_t iterations = 0;

		while (position != nullptr) {
			if (iterations == index)
				return position;
			position = position->next();

			iterations++;
		}
		return nullptr;
	}
}

template <class T>
Node<T>* LinkedList<T>::copyOf(Node<T>* otherHead) {
	Node<T>* position = otherHead;
	Node<T>* newHead;
	Node<T>* end = nullptr;

	// Create first node
	newHead = new Node<T>(position->getData(), nullptr);
	end = newHead;
	position = position->next();

	while (position != nullptr) {
		end->setNext(new Node<T>(position->getData(), nullptr));
		end = end->next();
		position = position->next();
	}

	return newHead;
}


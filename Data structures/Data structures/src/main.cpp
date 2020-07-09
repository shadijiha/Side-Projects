#include <iostream>

#include "include/ArrayList.h"

template <class T>
void print(const Collection<T>& list) {
	std::cout << list.toString() << std::endl;
}

int main() {

	auto list = new ArrayList<int>();

	for (int i = 0; i < 20; i++)
		list->add(i);

	print(*list);

	std::cout << list->size() << std::endl;

	print(*list);

	delete list;
	
	std::cin.get();

	return 0;
}

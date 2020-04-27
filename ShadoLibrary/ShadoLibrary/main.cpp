#include <iostream>


#include "includes/Collections/ArrayList.h"
#include "includes/Collections/LinkedList.h"
#include "includes/Optional.h"
#include "includes/Strings/String.h"
#include "includes/Strings/StringBuilder.h"

int main() {
	
	LinkedList<int> list;
	list.add(1);
	list.add(2);
	list.add(3);
	list.add(4);
	list.add(5);

	list[0] = 123;

	list.insert(7, 1);
	
	std::cout << list.size() << " -->[";

	list.stream()
		.forEach([](auto e) {std::cout << e << " | "; });
	
	std::cout << "]" << std::endl;

	std::cin.get();
	
	return 0;
}
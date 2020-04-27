#pragma once

#include <iostream>
#include <string>

class Object {
public:
	Object();
	Object(const Object& o) = default;
	virtual ~Object() = default;

	virtual std::string getClass();
	
	virtual bool equals(Object obj);
	virtual std::string toString();
	virtual int hashCode();

	bool operator == (Object obj) { return hashCode() == obj.hashCode(); }

	friend std::ostream& operator << (std::ostream& os, Object o);
	
protected:
	virtual Object clone();
	
private:
	
};
